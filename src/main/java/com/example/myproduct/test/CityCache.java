package com.example.myproduct.test;

import com.example.myproduct.cache.CacheAnnotation;
import com.example.myproduct.cache.CacheBean;
import com.example.myproduct.cache.CacheTimeType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component("cityCache")
public class CityCache implements CacheBean {
  private Random random = new Random();

  @CacheAnnotation(name = "CityCache_getAll", timeType = CacheTimeType.Minute, timeOut = 5)
  public List<City> getAll() {
    List<City> cities = new ArrayList<>();
    City city = new City();
    city.setName("上海");
    city.setId(random.nextInt());
    city.setEnName("ShangHai");
    city.setCode("SHA");
    city.setPid(0);
    cities.add(city);
    city = new City();
    city.setName("北京");
    city.setId(random.nextInt());
    city.setEnName("BeiJin");
    city.setCode("BJS");
    city.setPid(0);
    cities.add(city);
    return cities;
  }

  @CacheAnnotation(name = "CityCache_getCityByCode", timeType = CacheTimeType.Minute, timeOut = 2)
  public City getCityByCode(String code) {
    City city = new City();
    city.setName("上海");
    city.setId(random.nextInt());
    city.setEnName("ShangHai");
    city.setCode(code);
    city.setPid(0);
    return city;
  }
}
