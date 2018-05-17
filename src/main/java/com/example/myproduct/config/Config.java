package com.example.myproduct.config;

import com.example.myproduct.cache.CacheManager;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;
import java.util.EventListener;

/**
 * Created by @author: qydai
 * Created on @date: 2018/3/27
 */
@Configuration
public class Config extends WebMvcConfigurerAdapter{

  @Bean
  public CacheManager getCacheManager(ApplicationContext applicationContext){
    CacheManager cacheManager = new CacheManager();
    cacheManager.setApplicationContext(applicationContext);
    return cacheManager;
  }
  @Bean
  public DataSource dataSource(){
    PooledDataSource dataSource = new PooledDataSource("com.mysql.jdbc.Driver","","root","root");
    return dataSource;
  }
}
