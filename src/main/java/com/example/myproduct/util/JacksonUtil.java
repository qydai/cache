package com.example.myproduct.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * The type Jackson util.
 * Created by @auther: SZOnlineDev@Ctrip.com
 * Created on @date: 2018.03.13
 */
public abstract class JacksonUtil {
  private static final Logger _logger = LoggerFactory.getLogger(JacksonUtil.class);
  private static final Map<Class<?>,Class<?>> CLASSES =  Arrays.asList(
      String.class,Double.class,Long.class,Integer.class,Float.class,Short.class,Byte.class,Character.class,Boolean.class
      ,double.class,long.class,int.class,short.class,byte.class,char.class,boolean.class
  ).stream().collect(Collectors.toMap(key->key,value -> value));
  private static ObjectMapper _reader;
  private static ObjectMapper _writer;

  static {
    _reader = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    _reader.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
    _writer = new ObjectMapper();
    _writer.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
  }

  /**
   * To json string.
   *
   * @param object the object
   * @return the string
   */
  public static String toJson(Object object) {

    try {
      return _writer.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      _logger.error(e.getMessage());
      return null;
    }
  }

  /**
   * To json string.
   *
   * @param object       the object
   * @param ignoredNames the ignored names
   * @return the string
   */
  public static String toJson(Object object, List<String> ignoredNames) {
    if (object!=null && CLASSES.containsKey(object.getClass())){
      return object.toString();
    }
    JsonNode jsonNode = _writer.valueToTree(object);
    for (String ignoredName : ignoredNames) {
      ((ObjectNode) jsonNode).remove(ignoredName);
    }

    return jsonNode.toString();
  }

  /**
   * From json t.
   *
   * @param <T>   the type parameter
   * @param json  the json
   * @param clazz the clazz
   * @return the t
   */
  public static <T> T fromJson(String json, Class<T> clazz) {
    if (!StringUtil.isNullOrEmpty(json) && CLASSES.containsKey(clazz)){
      return parseBasicData(json,clazz);
    }
    return parseJson(json, clazz);
  }

  /**
   * Parse basic data t.
   *
   * @param <T>   the type parameter
   * @param json  the json
   * @param clazz the clazz
   * @return the t
   */
  public static <T> T parseBasicData(String json, Class<T> clazz){
    T t;
    if (clazz.getSimpleName().contains("String")){
      return (T)json;
    }else if (clazz.getSimpleName().contains("Character") || clazz.getSimpleName().contains("char")){
      t = (T)Character.valueOf(json.charAt(0));
    }else if (clazz.getSimpleName().contains("Boolean") || clazz.getSimpleName().contains("boolean")){
      t = (T)new Boolean(json);
    }else {
      t = Converter.parseNumber(json,clazz);
    }
    return t;
  }

  /**
   * From json t.
   *
   * @param <T>           the type parameter
   * @param json          the json
   * @param typeReference the type reference
   * @return the t
   */
  public static <T> T fromJson(String json, TypeReference typeReference) {
    return parseJson(json, typeReference);
  }

  private static <T> T parseJson(String json, Object type) {
    T t = null;
    try {
      if (type instanceof Class) {
        t = _reader.readValue(json, (Class<T>) type);
      } else if (type instanceof TypeReference) {
        t = _reader.readValue(json, (TypeReference) type);
      }
    } catch (Exception e) {
      _logger.error(e.getMessage());
    }
    return t;
  }
  private static class ChannelCampaignConfig{
    @JsonProperty("statrTime")
    private Date startTime;
    private Date endTime;

    /**
     * Gets start time.
     *
     * @return the start time
     */
    public Date getStartTime() {
      return startTime;
    }

    /**
     * Sets start time.
     *
     * @param startTime the start time
     */
    public void setStartTime(Date startTime) {
      this.startTime = startTime;
    }

    /**
     * Gets end time.
     *
     * @return the end time
     */
    public Date getEndTime() {
      return endTime;
    }

    /**
     * Sets end time.
     *
     * @param endTime the end time
     */
    public void setEndTime(Date endTime) {
      this.endTime = endTime;
    }

    @Override
    public String toString() {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.s");
      return "ChannelCampaignConfig{" +
          "startTime=" + (startTime != null ? dateFormat.format(startTime) : null) +
          ", endTime=" + (endTime != null ? dateFormat.format(endTime) : null) +
          '}';
    }
  }
}
