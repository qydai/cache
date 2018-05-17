package com.example.myproduct.util;

import org.springframework.util.StringUtils;

import java.util.Date;


/**
 * The type String util.
 * Created by @auther: SZOnlineDev@Ctrip.com
 * Created on @date: 2018.03.13
 */
public abstract class StringUtil {
  /**
   * Camel case string.
   *
   * @param text the text
   * @return the string
   */
  public static String camelCase(String text) {
    if (!StringUtils.isEmpty(text)) {
      return text.substring(0, 1).toLowerCase() + text.substring(1);
    }

    return null;
  }

  /**
   * Is null or empty boolean.
   *
   * @param text the text
   * @return the boolean
   */
  public static boolean isNullOrEmpty(String text) {
    return isNullOrEmpty(text, false);
  }

  /**
   * Is null or empty boolean.
   *
   * @param text   the text
   * @param isTrim the is trim
   * @return the boolean
   */
  public static boolean isNullOrEmpty(String text, boolean isTrim) {
    boolean result = false;
    if (text == null || (isTrim ? text.trim().equals("") : text.equals(""))) {
      result = true;
    }
    return result;
  }

  /**
   * Gets birthday from id card no.
   *
   * @param idCardNo the id card no
   * @return the birthday from id card no
   */
  public static Date getBirthdayFromIDCardNo(String idCardNo) {
    String birth = "";
    if (idCardNo.length() == 15) {
      birth = "19" + idCardNo.substring(6, 8) + "-" + idCardNo.substring(8, 10) + "-" + idCardNo
          .substring(10, 12);
    } else if (idCardNo.length() > 17) {
      birth = idCardNo.substring(6, 10) + '-' + idCardNo.substring(10, 12) + '-' + idCardNo
          .substring(12, 14);
    }
    return DatetimeUtil.parseDate(birth, "yyyy-MM-dd");
  }

  /**
   * Trim string.
   *
   * @param text the text
   * @param c    the c
   * @return the string
   */
  public static String trim(String text, String c) {
    String result = text;
    if (result.startsWith(c)) {
      result = result.substring(result.indexOf(c) + c.length(), result.length());
    }
    if (result.endsWith(c)) {
      result = result.substring(0, result.lastIndexOf(c));
    }
    return result;
  }

  /**
   * Gets sex from id card no.
   *
   * @param idCardNo the id card no
   * @return the sex from id card no
   */
  public static String getSexFromIDCardNo(String idCardNo) {
    String sex = "M";
    int sexNumber = 0;
    if (idCardNo.length() == 15) {
      sexNumber = Integer.parseInt(idCardNo.substring(14, 15));
    } else if (idCardNo.length() > 17) {
      sexNumber = Integer.parseInt(idCardNo.substring(16, 17));
    }
    if (sexNumber % 2 == 0) {
      sex = "F";
    }
    return sex;
  }

  /**
   * Parse int integer.
   *
   * @param str the str
   * @return the integer
   */
  public static Integer parseInt(String str) {
    Integer result = null;
    if (str != null && str.matches("^\\d+$")) {
      result = Integer.parseInt(str);
    }
    return result;
  }

  /**
   * Trim start string.
   *
   * @param input    the input
   * @param trimChar the trim char
   * @return the string
   */
  public static String trimStart(String input, String trimChar) {
    String output = null;
    if (input.startsWith(trimChar)) {
      output = input.substring(input.indexOf(trimChar) + trimChar.length(), input.length());
    } else {
      return input;
    }
    if (output.startsWith(trimChar)) {
      output = trimStart(output, trimChar);
    }
    return output;
  }

  /**
   * Trim end string.
   *
   * @param input    the input
   * @param trimChar the trim char
   * @return the string
   */
  public static String trimEnd(String input, String trimChar) {
    String output = null;
    if (input.endsWith(trimChar)) {
      output = input.substring(0, input.length() - trimChar.length());
    } else {
      return input;
    }
    if (output.endsWith(trimChar)) {
      output = trimEnd(output, trimChar);
    }
    return output;
  }

  /**
   * Equals boolean.
   *
   * @param string1 the string 1
   * @param string2 the string 2
   * @param isUpper the is upper
   * @return the boolean
   */
  public static boolean equals(String string1, String string2, boolean... isUpper) {
    if (!isNullOrEmpty(string1, true) && !isNullOrEmpty(string2, true)) {
      return isUpper != null && isUpper.length > 0 && isUpper[0] ? string1.toUpperCase().equals
          (string2.toUpperCase()) : string1.equals(string2);
    }
    return false;
  }

  /**
   * Trim string.
   *
   * @param value the value
   * @return the string
   */
  public static String trim(String value) {
    if (!isNullOrEmpty(value, true)) {
      return value.trim();
    }
    return "";
  }

  /**
   * Insert string.
   *
   * @param value       the value
   * @param index       the index
   * @param insertValue the insert value
   * @return the string
   */
  public static String insert(String value, int index, String insertValue) {
    String result = null;
    if (!isNullOrEmpty(value) && !isNullOrEmpty(insertValue)) {
      result = value.substring(0, index) + insertValue + value.substring(index);
    }
    return result;
  }
}
