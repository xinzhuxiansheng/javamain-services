package com.javamain.temp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Test01 {
    public static void main(String[] args) {
//        Properties prop = new Properties();
//        prop.put("a",100);
//        System.out.println(prop.getProperty("a"));
//        Map<String, String> map = new HashMap<>();
//        map.put("a", "a");
//        map.put("b", "b");
//
//        Set<String> keys = map.keySet();
//        keys.remove("a");
//        System.out.println(map.size());
//        LocalDateTime dateTime01 = LocalDateTime.ofInstant(
//                Instant.ofEpochSecond(1700201388), ZoneId.systemDefault());
//        DateTimeFormatter formatter01 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
//        String result01 = dateTime01.format(formatter01) + "000";
//        System.out.println("result01:  " + result01);
//
//
//        LocalDateTime dateTime02 = LocalDateTime.ofInstant(
//                Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.systemDefault());
//        DateTimeFormatter formatter02 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
//        String result02 = dateTime02.format(formatter02) + "000";
//        System.out.println("result02:  " + result02);

        System.out.println("Java版本为: " + System.getProperty("java.version"));
        System.out.println("文件编码为: " + System.getProperty("file.encoding"));
        System.out.println("本地编码为: " + System.getProperty("native.encoding"));
        System.out.println("你好 yzhou");
    }

}
