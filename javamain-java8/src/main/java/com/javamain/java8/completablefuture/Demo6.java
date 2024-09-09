package com.javamain.java8.completablefuture;

import java.util.concurrent.CompletableFuture;

public class Demo6 {
    public static void main(String[] args) throws Exception {
        CompletableFuture<String> ctf1 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + ", task1 开始执行");
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + ", task1 执行完毕");

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "zhangsan";
        }/*,Executors.newFixedThreadPool(2)*/);

//        CompletableFuture<String> future = ctf1.thenCompose((s) -> CompletableFuture.supplyAsync(s::toUpperCase));
//        System.out.println(future.get());
//
//        CompletableFuture<String> future1 = ctf1.thenApply(String::toUpperCase);
//        System.out.println(future1.get());

        // apply 类似于  map 映射
        CompletableFuture<CompletableFuture<String>> applyFuture = ctf1.thenApply(Demo6::fetchSomeInfo);
        System.out.println(applyFuture.get().get());

        // compose 类似于 flatmap，把嵌套结构压平后返回
        CompletableFuture<String> composeFuture = ctf1.thenCompose(Demo6::fetchSomeInfo);
        System.out.println(composeFuture.get());

        Thread.sleep(Long.MAX_VALUE);
    }

    public static CompletableFuture<String> fetchSomeInfo(String name){
        return CompletableFuture.supplyAsync(()-> name+":some_info");
    }
}
