package com.javamain.guava;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class CacheTest03 {

    private static String key = "test";
    private static Object result = null;

    public static void main(String[] args) throws Exception {

        Cache<String, Long> groupIdsOffsetsCache = Caffeine.newBuilder()
                .maximumSize(40000)
                .expireAfterWrite(2, TimeUnit.DAYS)
                .build();

        Long offset = groupIdsOffsetsCache.getIfPresent("key");
//        if(offset==null){
//            System.out.println("为空!");
//        }

        Optional.ofNullable(groupIdsOffsetsCache.getIfPresent("key"))
                .orElseGet(()->{
                    System.out.println("为空");
                    return -1L;
                });
    }
}
