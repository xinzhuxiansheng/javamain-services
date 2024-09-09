package com.javamain.lockfreestack.util;

import java.util.HashMap;
import java.util.Map;

public class HashMapDebug {

    public static void main(String[] args) {

//        Map<String, String> map = new HashMap<>();
//        for (int i = 0; i < 100; i++) {
//            map.put("name" + i, "yzhou" + i);
//        }
//        map.put("name0", "yzhou002");
//
//        System.out.println("执行结束！");


        Map<String,String> map = new HashMap<>();
        map.put("name","young");
        String name = map.get("name");
        map.remove("name");
        System.out.println("map size: "+map.size());

    }
}
