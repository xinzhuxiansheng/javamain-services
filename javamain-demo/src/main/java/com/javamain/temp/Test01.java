package com.javamain.temp;

import java.util.BitSet;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class Test01 {
    public static void main(String[] args) {
        Properties prop = new Properties();
        prop.put("a",100);
        System.out.println(prop.getProperty("a"));

    }
}
