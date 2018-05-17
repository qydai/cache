package com.example.myproduct.test;

import java.io.Serializable;

/**
 * The type City.
 * Created by @author: SZOnlineDev@Ctrip.com
 * Created on @date: 2018.03.13
 */
public class City implements Serializable {
  /**
   * 城市ID
   */
  private Integer id;
  /**
   * 城市名称
   */
  private String name;
  /**
   * 城市三字码
   */
  private String code;
  /**
   * 城市拼音
   */
  private String enName;
  /**
   * 所在省份ID
   */
  private Integer pid;

  /**
   * Gets id.
   *
   * @return the id
   */
  public Integer getId() {
    return id;
  }

  /**
   * Sets id.
   *
   * @param id the id
   */
  public void setId(Integer id) {
    this.id = id;
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

  /**
   * Gets pid.
   *
   * @return the pid
   */
  public Integer getPid() {
    return pid;
  }

  /**
   * Sets pid.
   *
   * @param pid the pid
   */
  public void setPid(Integer pid) {
    this.pid = pid;
  }
}
