package com.example.myproduct.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheAnnotation {
  String name();

  CacheTimeType timeType() default CacheTimeType.Minute;

  int timeOut() default 30;

  boolean autoFlash() default true;
}
