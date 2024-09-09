package com.javamain.java8.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * CompletableFuture 比 基础 Future 强大之处： 异步任务的链式回调，
 * 以及多异步任务阶段之间的组合、汇聚等功能。
 * <p>
 * applyAsync() 无返回值
 * supplyAsync() 有返回值
 */
public class Demo2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CompletableFuture<String> cft = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "，supply task 执行了");
            return "supply-result";
        });

        System.out.println("主线程工作之中……");
        System.out.println("主线程工作之中……");

        System.out.println(cft.get());
        Thread.sleep(Long.MAX_VALUE);
    }
}
