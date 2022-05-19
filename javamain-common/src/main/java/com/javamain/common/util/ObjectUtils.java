package com.javamain.common.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * @author yzhou
 * @date 2022/5/11
 */
public interface ObjectUtils {

    static <T> T NULL() {
        return null;
    }

    static void requireNonNull(Object obj, String name) {
        if (obj == null)
            throw new IllegalArgumentException(name + " is null");
    }

    static void requireNonEmpty(Collection<?> obj, String name) {
        if (CollectionUtils.isEmpty(obj))
            throw new IllegalArgumentException(name + " is empty");
    }

    static <T> void requireNonEmpty(T[] obj, String name) {
        if (CollectionUtils.isEmpty(obj))
            throw new IllegalArgumentException(name + " is empty");
    }

    static void requireNonEmpty(Map<?, ?> obj, String name) {
        if (CollectionUtils.isEmpty(obj))
            throw new IllegalArgumentException(name + " is empty");
    }

    static void requireNonEmpty(String obj, String name) {
        if (StringUtils.isEmpty(obj))
            throw new IllegalArgumentException(name + " is empty");
    }

    static void requireNonBlank(String obj, String name) {
        if (StringUtils.isBlank(obj))
            throw new IllegalArgumentException(name + " is blank");
    }

    static void requireNonNullOrEmpty(Object obj, String name) {
        if (isNullOrEmpty(obj))
            throw new IllegalArgumentException(name + " is null or empty");
    }

    @SuppressWarnings("rawtypes")
    static boolean isNullOrEmpty(Object obj) {
        if (obj == null)
            return true;

        if (obj instanceof String)
            return StringUtils.isBlank((String) obj);

        if (obj instanceof Collection)
            return ((Collection) obj).isEmpty();

        if (obj instanceof Map)
            return ((Map) obj).isEmpty();

        if (obj.getClass().isArray())
            return Array.getLength(obj) == 0;

        return false;
    }
}
