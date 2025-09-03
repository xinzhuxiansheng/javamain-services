package com.javamain.temp;

import org.apache.commons.lang3.StringUtils;
import retrofit2.http.HEAD;
import scala.math.Ordering;

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

//        System.out.println("Java版本为: " + System.getProperty("java.version"));
//        System.out.println("文件编码为: " + System.getProperty("file.encoding"));
//        System.out.println("本地编码为: " + System.getProperty("native.encoding"));
//        System.out.println("你好 yzhou");

//        byte[] a = new byte[] {
//                109, 93, -94, 23, -5, 27, 29, 74, 7, 2, -85, 26, -68, 44, 79, -8, 87, -89, -82, -24, 21,
//                -13, 49, -107, -70, 90, 60, 71, 45, 29, 37, 77, 68, -103, 49, 37, -43, 29, -41, -61,
//                -124, 95, 57, 82, -21, 43, -125, 10, 75, -89, -71, 69, -14, -120, -100, 34, 17, 30, -87,
//                -58, 27, -30, 116, 43, -87, 32, -105, 80, -53, -44, -49, 79, 85, 52, 38, 53, 122, -50,
//                -90, -59, -44, 48, 51, 59, 32, -41, -56, -26, 28, -46, 29, -108, 61, 86, -51, -42, -50,
//                -122, 37, -105
//        };
//
//        System.out.println(new String(a));

        String a = " ";
        System.out.println(StringUtils.isBlank(a));
    }

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
//
//    }
}
