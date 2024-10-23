package com.javamain.java8.linkedhashmap;

public class LinkedHashMapTest {

    public static void main(String[] args) {
        java.util.Map<Integer, Integer> map1 = new java.util.HashMap<>();
        map1.put(1,1);
        map1.put(100,100);
        map1.put(99,99);
        map1.entrySet().forEach(System.out::println);

        System.out.println("\r\n");

        java.util.Map<Integer, Integer> map2 = new java.util.LinkedHashMap<>();
        map2.put(1,1);
        map2.put(100,100);
        map2.put(99,99);
        map2.entrySet().forEach(System.out::println);
    }
}
