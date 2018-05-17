package com.example.myproduct.cache;

import com.example.myproduct.util.CollectionUtil;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CacheManager {
  private Map<String, Cache> cacheMap = new ConcurrentHashMap<>();
  private ApplicationContext applicationContext;
  private ScheduledThreadPoolExecutor executor;
  private Object lock = new Object();
  private List<Runnable> executeRunnableList = new ArrayList<>();

  public synchronized void initCache() {
    try {
      if (executor == null) {
        executor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(5);
      }
      cacheMap.clear();
      executeRunnableList.clear();
      Map<String, CacheBean> map = applicationContext.getBeansOfType(CacheBean.class);
      Collection<CacheBean> beans = map.values();
      for (CacheBean bean : beans) {
        List<Method> methods = CollectionUtil.findAll(
            Arrays.asList(bean.getClass().getMethods()),
            x -> x.getAnnotation(CacheAnnotation.class) != null);
        for (Method method : methods) {
          Cache cache = new Cache();
          CacheAnnotation cacheAnnotation = method.getAnnotation(CacheAnnotation.class);
          Parameter[] parameters = method.getParameters();
          cache.setContainParam(parameters != null && parameters.length > 0);
          cache.setAutoFlash(cacheAnnotation.autoFlash());
          cache.setCacheBean(bean);
          cache.setCacheName(cacheAnnotation.name());
          cache.setMethod(method);
          cache.setTimeOut(getTimeOut(cacheAnnotation.timeOut(), cacheAnnotation.timeType()));
          cache.setData(new ConcurrentHashMap<>());
          cache.setParams(new ConcurrentHashMap<>());
          cacheMap.put(cacheAnnotation.name(), cache);
        }
      }
      for (String key : cacheMap.keySet()) {
        Cache cache = cacheMap.get(key);
        executeSaveCache(cache);
        if (cache.isAutoFlash()) {
          Runnable runnable = () -> executeSaveCache(cache);
          executor.scheduleAtFixedRate(runnable, cache.getTimeOut(),
              cache.getTimeOut(), TimeUnit.MILLISECONDS);
          executeRunnableList.add(runnable);
        }
      }
    } catch (Throwable e) {
      e.printStackTrace();
    }
  }

  private void executeSaveCache(Cache cache) {
    try {
      long executeBegin = System.currentTimeMillis();
      Method method = cache.getMethod();
      if (!cache.isContainParam()) {
        Object result = method.invoke(cache.getCacheBean());
        if (result != null) {
          cache.getData().put(cache.getCacheName(), result);
          long nowTime = System.currentTimeMillis();
          if (cache.getFirstExecuteTime() <= 0) {
            cache.setFirstExecuteTime(nowTime);
          }
          cache.setLastExecuteTime(nowTime);
        }
      } else {
        if (!cache.getParams().isEmpty()) {
          for (String paramKey : cache.getParams().keySet()) {
            Object result = method.invoke(cache.getCacheBean(), cache.getParams().get(paramKey));
            cache.getData().put(paramKey, result);
            cache.setFirstExecuteTime(System.currentTimeMillis());
            long nowTime = System.currentTimeMillis();
            if (cache.getFirstExecuteTime() <= 0) {
              cache.setFirstExecuteTime(nowTime);
            }
            cache.setLastExecuteTime(nowTime);
          }
        }
      }
    } catch (Throwable e) {
      e.printStackTrace();
    }
  }

  private int getTimeOut(int i, CacheTimeType cacheTimeType) {
    int timeOut;
    switch (cacheTimeType) {
      case Day:
        timeOut = i * 1000 * 60 * 60 * 24;
        break;
      case Hour:
        timeOut = i * 1000 * 60 * 60;
        break;
      case Minute:
        timeOut = i * 1000 * 60;
        break;
      case Second:
        timeOut = i * 1000;
        break;
      default:
        timeOut = i;
    }
    return timeOut;
  }

  public <T> T getCache(String cacheName, Object... params) {
    T t = null;
    try {
      if (cacheMap.containsKey(cacheName)) {
        Cache cache = cacheMap.get(cacheName);
        long nowTime = System.currentTimeMillis();
        if (!cache.isContainParam()) {
          if (cache.isAutoFlash()) {
            t = (T) cache.getData().get(cacheName);
          } else {
            t = getStaticCache(cacheName, cache, nowTime);
          }
        } else {
          if (params != null && params.length > 0) {
            String cacheKey = cacheName;
            for (Object o : params) {
              cacheKey += o.hashCode();
            }
            cache.getParams().put(cacheKey, params);
            t = getStaticCache(cacheKey, cache, nowTime);
          }
        }
      }
    } catch (Throwable e) {
      e.printStackTrace();

    }
    return t;
  }

  private <T> T getStaticCache(String cacheKey, Cache cache, long nowTime) {
    T t = null;
    if (cache.getData().containsKey(cacheKey) && (nowTime - cache.getLastExecuteTime() <= cache.getTimeOut())) {
      t = (T) cache.getData().get(cacheKey);
      cache.setLastExecuteTime(nowTime);
    } else {
      synchronized (lock) {
        if (cache.getData().containsKey(cacheKey) && (nowTime - cache.getLastExecuteTime() <= cache.getTimeOut())) {
          return (T) cache.getData().get(cacheKey);
        }
        Method method = cache.getMethod();
        try {
          if (cache.getParams().isEmpty()) {
            t = (T) method.invoke(cache.getCacheBean());
          } else {
            t = (T) method.invoke(cache.getCacheBean(), cache.getParams().get(cacheKey));
          }
          if (cache.getFirstExecuteTime() <= 0) {
            cache.setFirstExecuteTime(nowTime);
          }
          cache.setLastExecuteTime(nowTime);
          cache.getData().put(cacheKey, t);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    return t;
  }

  public synchronized boolean refresh() {
    try {
      for (Runnable runnable : executeRunnableList) {
        executor.remove(runnable);
      }
      initCache();
    } catch (Throwable e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public synchronized boolean refresh(String key) {
    boolean result = true;
    long nowTime = System.currentTimeMillis();
    if (cacheMap.containsKey(key)) {
      Cache cache = cacheMap.get(key);
      cache.getData().clear();
      if (cache.isContainParam()) {
        for (String cacheKey : cache.getParams().keySet()) {
          try {
            cache.getData().put(cacheKey, cache.getMethod().invoke(cache.getCacheBean(), cache.getParams().get(cacheKey)));
          } catch (Exception e) {
            e.printStackTrace();
            result = false;
          }
        }
      } else {
        try {
          cache.getData().put(key, cache.getMethod().invoke(cache.getCacheBean()));
        } catch (Exception e) {
          e.printStackTrace();
          result = false;
        }
      }
      cache.setFirstExecuteTime(nowTime);
      cache.setLastExecuteTime(nowTime);
    }
    return result;
  }

  public Map<String, Cache> getAllCache() {
    return cacheMap;
  }

  public void setApplicationContext(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }
}
