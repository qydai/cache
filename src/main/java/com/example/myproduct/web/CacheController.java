package com.example.myproduct.web;

import com.example.myproduct.cache.Cache;
import com.example.myproduct.cache.CacheManager;
import com.example.myproduct.test.Airline;
import com.example.myproduct.test.AirlineCache;
import com.example.myproduct.test.City;
import com.example.myproduct.util.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/cache")
public class CacheController {

  private CacheManager cacheManager;
  private AirlineCache airlineCache;
  @Autowired
  public CacheController(CacheManager cacheManager, AirlineCache airlineCache) {
    this.cacheManager = cacheManager;
    this.airlineCache = airlineCache;
  }
  @RequestMapping("getAirlineByCode")
  @ResponseBody
  public String getAirlineByCode(String code){
    Airline airline = airlineCache.getAirlineByCode(code);
    if (airline != null){
      return JacksonUtil.toJson(airline);
    }
    return "null";
  }
  @RequestMapping("getAllCaches")
  @ResponseBody
  public String getAllCaches(){
    return null;
  }

  @RequestMapping("getCache")
  @ResponseBody
  public Object getCache(String key){
    return JacksonUtil.toJson(cacheManager.getCache(key));
  }
  @RequestMapping("getCity")
  @ResponseBody
  public String getCity(String key,String code){
    City city = cacheManager.getCache(key,code);
    return JacksonUtil.toJson(city);
  }
  @RequestMapping("flash")
  @ResponseBody
  public Object flash(String key){
    Object result;
    if (key != null){
      result = cacheManager.refresh(key);
    }else {
      result = cacheManager.refresh();
    }
    return result;
  }
  @RequestMapping("getCacheKeys")
  @ResponseBody
  public String getCacheKeys(){
    Map<String,Cache> cacheMap = cacheManager.getAllCache();
    Map<String,Collection<String>> result = new HashMap<>();
    for (String s : cacheMap.keySet()){
      Cache cache = cacheMap.get(s);
      result.put(s,cache.getData().keySet());
    }
    return JacksonUtil.toJson(result);
  }
}
