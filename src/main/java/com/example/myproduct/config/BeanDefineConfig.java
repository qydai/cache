package com.example.myproduct.config;

import com.example.myproduct.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component("beanDefineConfig")
public class BeanDefineConfig implements ApplicationListener<ContextRefreshedEvent> {
  @Autowired
  private CacheManager cacheManager;
  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    cacheManager.initCache();
  }
}
