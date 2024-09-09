package com.javamain.common.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author yzhou
 * @date 2022/5/11
 */
public class CollectionUtils {
    static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }

    static <T> List<T> asList(T[] array) {
        if (array == null)
            return null;

        return Arrays.asList(array);
    }
}
