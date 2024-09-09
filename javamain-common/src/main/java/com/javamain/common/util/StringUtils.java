package com.javamain.common.util;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author yzhou
 * @date 2022/5/11
 */
public interface StringUtils {

    static final String EMPTY = "";
    static final String NULL = String.valueOf((Object) null);

    static boolean isEmpty(String obj) {
        return obj == null || obj.isEmpty();
    }

    static boolean isBlank(String obj) {
        if (isEmpty(obj))
            return true;

        for (int i = 0; i < obj.length(); i++) {
            if (!Character.isWhitespace(obj.charAt(i)))
                return false;
        }

        return true;
    }

    static String nullToEmpty(String obj) {
        return obj == null ? EMPTY : obj;
    }

    static String toString(Object obj) {
        return obj == null ? null : obj.toString();
    }

    static String toLowerCase(String value) {
        if (value == null)
            return null;

        return value.toLowerCase();
    }

    static String toUpperCase(String value) {
        if (value == null)
            return null;

        return value.toUpperCase();
    }

    static String intern(String value) {
        return value == null ? null : value.intern();
    }

    static boolean equalsIgnoreCase(String s1, String s2) {
        if (s1 == s2)
            return true;

        if (s1 == null || s2 == null)
            return false;

        return s1.equalsIgnoreCase(s2);
    }

    static String trim(String s) {
        if (s == null)
            return null;
        return s.trim();
    }

    static String trim(String s, char... chars) {
        s = trimStart(s, chars);
        s = trimEnd(s, chars);
        return s;
    }

    static String trimEnd(String s) {
        if (s == null)
            return s;
        char[] chars = s.toCharArray();
        int st = chars.length - 1;
        while (st >= 0 && chars[st] <= ' ') {
            st--;
        }
        return st < 0 ? EMPTY : s.substring(0, st + 1);
    }

    static String trimEnd(String s, char... chars) {
        if (s == null || chars == null)
            return s;

        Arrays.sort(chars);

        int index;
        for (index = s.length() - 1; index >= 0; index--) {
            char c = s.charAt(index);
            if (Character.isWhitespace(c))
                continue;

            int i = Arrays.binarySearch(chars, c);
            if (i >= 0)
                continue;

            break;
        }

        if (index == s.length() - 1)
            return s;

        return index < 0 ? EMPTY : s.substring(0, index + 1);
    }

    static String trimStart(String s) {
        if (s == null)
            return s;
        char[] chars = s.toCharArray();
        int st = 0;
        while (st < chars.length && chars[st] <= ' ') {
            st++;
        }
        return st == chars.length ? EMPTY : s.substring(st);
    }

    static String trimStart(String s, char... chars) {
        if (s == null || chars == null)
            return s;

        Arrays.sort(chars);

        int index;
        for (index = 0; index < s.length(); index++) {
            char c = s.charAt(index);
            if (Character.isWhitespace(c))
                continue;

            int i = Arrays.binarySearch(chars, c);
            if (i >= 0)
                continue;

            break;
        }

        if (index == 0)
            return s;

        return index == s.length() ? EMPTY : s.substring(index);
    }

    static String trim(String s, String s2) {
        s = trimStart(s, s2);
        s = trimEnd(s, s2);
        return s;
    }

    static String trimStart(String s, String s2) {
        if (s == null || s2 == null)
            return s;

        s2 = trim(s2);
        if (isEmpty(s2))
            return trim(s);

        s = trim(s);
        if (isBlank(s))
            return s;

        if (s2.length() > s.length())
            return s;

        if (!s.startsWith(s2))
            return s;

        if (s.length() == s2.length())
            return EMPTY;

        s = s.substring(s2.length());
        return trimStart(s, s2);
    }

    static String trimEnd(String s, String s2) {
        if (s == null || s2 == null)
            return s;

        s2 = trim(s2);
        if (isEmpty(s2))
            return trim(s);

        s = trim(s);
        if (isEmpty(s))
            return s;

        if (s2.length() > s.length())
            return s;

        if (!s.endsWith(s2))
            return s;

        if (s.length() == s2.length())
            return EMPTY;

        s = s.substring(0, s.length() - s2.length());
        return trimEnd(s, s2);
    }

    static <T> String join(T[] data, char separator) {
        return join(data, String.valueOf(separator));
    }

    static <T> String join(T[] data, String separator) {
        if (CollectionUtils.isEmpty(data))
            return null;

        return join(Arrays.asList(data), separator);
    }

    static String join(Collection<?> data, char separator) {
        return join(data, String.valueOf(separator));
    }

    static String join(Collection<?> data, String separator) {
        if (CollectionUtils.isEmpty(data))
            return null;

        StringBuilder sb = new StringBuilder();
        for (Object item : data) {
            if (item == null)
                continue;

            sb.append(item);
            if (separator != null)
                sb.append(separator);
        }

        if (sb.length() == 0)
            return null;

        if (separator == null)
            return sb.toString();

        return sb.substring(0, sb.length() - separator.length());
    }
}
