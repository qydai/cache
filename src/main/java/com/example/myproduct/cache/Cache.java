package com.example.myproduct.cache;

import java.lang.reflect.Method;
import java.util.Map;

public class Cache {
  private CacheBean cacheBean;
  private Method method;
  private Map<String, Object[]> params;
  private Map<String, Object> data;
  private long firstExecuteTime;
  private long lastExecuteTime;
  private boolean isAutoFlash;
  private String cacheName;
  private long timeOut;
  private boolean isContainParam;

  public long getTimeOut() {
    return timeOut;
  }

  public void setTimeOut(long timeOut) {
    this.timeOut = timeOut;
  }

  public CacheBean getCacheBean() {
    return cacheBean;
  }

  public void setCacheBean(CacheBean cacheBean) {
    this.cacheBean = cacheBean;
  }

  public Method getMethod() {
    return method;
  }

  public void setMethod(Method method) {
    this.method = method;
  }

  public Map<String, Object[]> getParams() {
    return params;
  }

  public void setParams(Map<String, Object[]> params) {
    this.params = params;
  }

  public Map<String, Object> getData() {
    return data;
  }

  public void setData(Map<String, Object> data) {
    this.data = data;
  }

  public long getFirstExecuteTime() {
    return firstExecuteTime;
  }

  public void setFirstExecuteTime(long firstExecuteTime) {
    this.firstExecuteTime = firstExecuteTime;
  }

  public long getLastExecuteTime() {
    return lastExecuteTime;
  }

  public void setLastExecuteTime(long lastExecuteTime) {
    this.lastExecuteTime = lastExecuteTime;
  }

  public boolean isAutoFlash() {
    return isAutoFlash;
  }

  public void setAutoFlash(boolean autoFlash) {
    isAutoFlash = autoFlash;
  }

  public String getCacheName() {
    return cacheName;
  }

  public void setCacheName(String cacheName) {
    this.cacheName = cacheName;
  }

  public boolean isContainParam() {
    return isContainParam;
  }

  public void setContainParam(boolean containParam) {
    isContainParam = containParam;
  }
}
