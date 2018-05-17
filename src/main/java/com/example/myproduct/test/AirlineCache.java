package com.example.myproduct.test;

import com.example.myproduct.cache.CacheAnnotation;
import com.example.myproduct.cache.CacheBean;
import com.example.myproduct.cache.CacheManager;
import com.example.myproduct.util.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by qydai on 2018/3/22.
 */
@Component
public class AirlineCache implements CacheBean {
  @Autowired
  private CacheManager cacheManager;

  @CacheAnnotation(name = "AirlineCache_getAll")
  public List<Airline> getAll() {
    List<Airline> airlines = new ArrayList<>();
    Airline airline = new Airline();
    airline.setName("中国航空");
    airline.setEnName("CA");
    airline.setShortName("国航");
    airline.setCode("CA");
    airlines.add(airline);
    airline = new Airline();
    airline.setName("南方航空");
    airline.setEnName("CS");
    airline.setShortName("南航");
    airline.setCode("CS");
    airlines.add(airline);
    return airlines;
  }

  public Airline getAirlineByCode(String code) {
    Airline airline = null;
    List<Airline> all = cacheManager.getCache("AirlineCache_getAll");
    if (!CollectionUtil.isNullOrEmpty(all)) {
      airline = CollectionUtil.findFirst(all, x -> Objects.equals(code, x.getCode()));
    }
    return airline;
  }
}
