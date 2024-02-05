package com.javamain.temp;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Test03 {
    public static void main(String[] args) {
        String user = "root";
        String passwd = "123456";
        String authEncoding = Base64.getEncoder().encodeToString(String.format("%s:%s", user, passwd).getBytes(StandardCharsets.UTF_8));

        System.out.println(authEncoding);





//        Properties a = new Properties();
//        a.put("cluster.code","a");
//
//        Properties b = a;
//
//        b.put("cluster.code","b");
//
//        System.out.println(a.get("cluster.code"));
//        System.out.println(b.get("cluster.code"));
    }
}

