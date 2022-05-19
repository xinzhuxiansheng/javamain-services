package com.javamain.temp;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Test01 {
    public static void main(String[] args) {
//        Properties prop = new Properties();
//        prop.put("a",100);
//        System.out.println(prop.getProperty("a"));
        Map<String, String> map = new HashMap<>();
        map.put("a", "a");
        map.put("b", "b");

        Set<String> keys = map.keySet();
        keys.remove("a");
        System.out.println(map.size());
    }
}
