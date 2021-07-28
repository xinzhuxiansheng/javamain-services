package com.javamain.guava;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CacheTest01 {

    private static String key = "test";
    private static Object result = null;

    public static void main(String[] args) throws Exception{

        /**
         * 手动加载：
         *      在获取的时候如果key存在，则获取，不存在就手动写入
         */
        Cache<String, Object> manualCache = Caffeine.newBuilder()
                .initialCapacity(1000)
                .maximumSize(5000)
                .expireAfterWrite(30, TimeUnit.SECONDS)
                .build();

        result = manualCache.get(key, k -> "manualCache");
        System.out.println("手动加载："+result);

        /**
         * 同步加载：
         *      用同步方式去获取一个缓存和上面的手动方式是一个原理
         *      查询并在缺失的情况下使用同步的方式来构建
         */
        LoadingCache<String, Object> loadingCache = Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(30, TimeUnit.SECONDS)
                .build(key -> "loadingCache");

        result = loadingCache.get(key);
        System.out.println("同步加载："+ result);

        /**
         * 异步加载:
         *      查询并在缺失的情况下使用异步的方式来构建缓存.
         *      使用 isDone()查看异步是否完成。
         */
        AsyncLoadingCache<String, Object> asyncLoadingCache = Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(30, TimeUnit.SECONDS)
                .buildAsync(key -> "asyncLoadingCache");

        CompletableFuture<Object> graph = asyncLoadingCache.get(key);
        if(graph.isDone()){
            System.out.println("异步加载："+ graph.get().toString());
        }

    }
}
