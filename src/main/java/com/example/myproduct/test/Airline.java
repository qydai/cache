package com.example.myproduct.test;

import java.io.Serializable;

/**
 * The type Airline.
 * Created by @author: SZOnlineDev@Ctrip.com
 * Created on @date: 2018.03.13
 */
// 航空公司
public class Airline implements Serializable {

  /**
   * 航司二字码
   */
  private String code;

  /**
   * 航司名称
   */
  private String name;

  /**
   * 航司简称
   */
  private String shortName;

  /**
   * 航司英文名称
   */
  private String enName;

  /**
   * Gets code.
   *
   * @return the code
   */
  public String getCode() {
    return code;
  }

  /**
   * Sets code.
   *
   * @param code the code
   */
  public void setCode(String code) {
    this.code = code;
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets name.
   *
   * @param name the name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets short name.
   *
   * @return the short name
   */
  public String getShortName() {
    return shortName;
  }

  /**
   * Sets short name.
   *
   * @param shortName the short name
   */
  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  /**
   * Gets en name.
   *
   * @return the en name
   */
  public String getEnName() {
    return enName;
  }

  /**
   * Sets en name.
   *
   * @param enName the en name
   */
  public void setEnName(String enName) {
    this.enName = enName;
  }
}
