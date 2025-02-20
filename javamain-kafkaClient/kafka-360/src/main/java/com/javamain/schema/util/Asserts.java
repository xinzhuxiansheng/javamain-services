package com.javamain.schema.util;

import java.util.Collection;
import java.util.Map;

public class Asserts {

  private Asserts() {
    throw new IllegalStateException("Utility class");
  }

  public static boolean isNotNull(Object object) {
    return object != null;
  }

  public static boolean isNull(Object object) {
    return object == null;
  }

  public static boolean isNullString(String str) {
    return isNull(str) || "".equals(str);
  }

  public static boolean isAllNullString(String... str) {
    boolean isNull = true;
    for (String item : str) {
      if (isNotNullString(item)) {
        isNull = false;
      }
    }
    return isNull;
  }

  public static boolean isNotNullString(String str) {
    return !isNullString(str);
  }

  public static boolean isAllNotNullString(String... str) {
    boolean isNotNull = true;
    for (String item : str) {
      if (isNullString(item)) {
        isNotNull = false;
      }
    }
    return isNotNull;
  }

  public static boolean isEquals(String str1, String str2) {
    if (isNull(str1) && isNull(str2)) {
      return true;
    } else if (isNull(str1) || isNull(str2)) {
      return false;
    } else {
      return str1.equals(str2);
    }
  }

  public static boolean isEqualsIgnoreCase(String str1, String str2) {
    if (isNull(str1) && isNull(str2)) {
      return true;
    } else if (isNull(str1) || isNull(str2)) {
      return false;
    } else {
      return str1.equalsIgnoreCase(str2);
    }
  }

  public static boolean isNullCollection(Collection collection) {
    return isNull(collection) || collection.isEmpty();
  }

  public static boolean isNotNullCollection(Collection collection) {
    return !isNullCollection(collection);
  }

  public static boolean isNullMap(Map map) {
    return isNull(map) || map.size() == 0;
  }

  public static boolean isNotNullMap(Map map) {
    return !isNullMap(map);
  }

  public static void checkNull(Object key, String msg) {
    if (key == null) {
      throw new RuntimeException(msg);
    }
  }

  public static void checkNotNull(Object object, String msg) {
    if (isNull(object)) {
      throw new RuntimeException(msg);
    }
  }

  public static void checkNullString(String key, String msg) {
    if (isNull(key) || isEquals("", key)) {
      throw new RuntimeException(msg);
    }
  }

  public static void checkNullCollection(Collection collection, String msg) {
    if (isNullCollection(collection)) {
      throw new RuntimeException(msg);
    }
  }

  public static void checkNullMap(Map map, String msg) {
    if (isNullMap(map)) {
      throw new RuntimeException(msg);
    }
  }
}
