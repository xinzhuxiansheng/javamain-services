package com.javamain.temp;

import java.util.Properties;

public class Test03 {
    public static void main(String[] args) {
        Properties a = new Properties();
        a.put("cluster.code","a");

        Properties b = a;

        b.put("cluster.code","b");

        System.out.println(a.get("cluster.code"));
        System.out.println(b.get("cluster.code"));
    }
}
