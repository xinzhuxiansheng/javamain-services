//package com.javamain.guava;
//
//import com.alibaba.fastjson.JSON;
//import com.github.benmanes.caffeine.cache.*;
//import org.checkerframework.checker.nullness.qual.NonNull;
//import org.checkerframework.checker.nullness.qual.Nullable;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.Set;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.TimeUnit;
//
//public class CacheTest02 {
//
//    private static String key = "test";
//    private static Object result = null;
//    private static Cache<String,Object> dataCache= null;
//
//    public static void main(String[] args) throws Exception{
//
//        /**
//         * 手动加载：
//         *      在获取的时候如果key存在，则获取，不存在就手动写入
//         */
////        dataCache = Caffeine.newBuilder()
////                .initialCapacity(1000)
////                .maximumSize(5000)
////                .removalListener((String key, Object value, RemovalCause cause) ->
////                        System.out.printf("Key %s was removed (%s)%n", key, cause))
////                .expireAfterWrite(2, TimeUnit.SECONDS)
////                .build();
//
//        dataCache = Caffeine.newBuilder()
//                .maximumSize(40000)
//                .expireAfterWrite(1, TimeUnit.SECONDS)
//                .removalListener(new GroupRemovalListener())
//                .build();
//
//        System.out.println(dataCache.estimatedSize());
//        dataCache.put("abc","bcd");
//        System.out.println(dataCache.getIfPresent("abc"));
//
//
//        dataCache.put("abc","efg");
//        System.out.println(dataCache.getIfPresent("abc"));
//
//
//
//        //dataCache.cleanUp();
//
//        Thread.sleep(8000);
//        System.out.println(dataCache.getIfPresent("abc"));
////        System.out.println(dataCache.getIfPresent("abc"));
////        System.out.println(dataCache.estimatedSize());
//
//    }
//
//    private static class GroupRemovalListener implements RemovalListener<String, Object> {
//        private Logger logger = LoggerFactory.getLogger(GroupRemovalListener.class);
//
//        @Override
//        public void onRemoval(@Nullable String key, @Nullable Object value, @NonNull RemovalCause removalCause) {
//            if(removalCause.equals(RemovalCause.EXPIRED)){
//                System.out.println(String.format("GroupRemovalListener Cache key: %s, value: %s", key,
//                        JSON.toJSONString(value)));
//            }
//        }
//    }
//}
