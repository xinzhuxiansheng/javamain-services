package com.javamain.jdk.util;

import java.util.HashMap;
import java.util.Map;

public class HashMapDebug {

    public static void main(String[] args) {

//        Map<String,String>  map = new HashMap<>();
//
//        map.put("field","value");
//        System.out.println("执行结束！");

        String a = "hollischuang";
        System.out.println(a.hashCode());
        System.out.println(a.hashCode());

        char[] strChar = String.valueOf(a.hashCode()).toCharArray();
        String result="";
        for(int i=0;i<strChar.length;i++){
            result +=Integer.toBinaryString(strChar[i])+ " ";
        }
        System.out.println(result);
    }
}
