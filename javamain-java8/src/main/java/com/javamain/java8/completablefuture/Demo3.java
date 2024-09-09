package com.javamain.java8.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class Demo3 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        // 提交异步任务stage时，可以指定执行用的线程池，如果不指定，则用默认的ForkJoinPool
        CompletableFuture<String> ctf1 = CompletableFuture.supplyAsync(() -> {

            try {
                System.out.println(Thread.currentThread().getName() + ", task1 开始执行");
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + ", task1 执行完毕");

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "task1-res";
        }/*,Executors.newFixedThreadPool(2)*/);

        // 对this_stage的结果，进行变换
        // apply stage 是沿用  操作stage:ctf1 的线程池来执行
//        CompletableFuture<String> ctf2 = ctf1.thenApply((s1) -> {
//            try {
//                System.out.println(Thread.currentThread().getName() + ", apply stage 开始执行");
//                Thread.sleep(2000);
//                System.out.println(Thread.currentThread().getName() + ", apply stage 执行完毕");
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            return s1.toUpperCase();
//        });
//        System.out.println("get结果: " + ctf2.get());


        // 带Async后缀： apply stage可以放在另行指定的与ctf1不同的线程池运行
        // 如果不传线程池，则会放入 ForkJoinPool中执行
        CompletableFuture<String> applyAsync = ctf1.thenApplyAsync((s1) -> {
            try {
                System.out.println(Thread.currentThread().getName() + ", apply stage 开始执行");
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + ", apply stage 执行完毕");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return s1.toUpperCase();
        });

        // async后缀，提供传入线程池的功能，它会把apply stage中的任物逻辑放在传入的线程池中执行
        // 如果不传入线程池，则apply stage是在操作stage原有的线程池中执行
        CompletableFuture<String> applyAsync2 = ctf1.thenApplyAsync((s1) -> {
            try {
                System.out.println(Thread.currentThread().getName() + ", apply stage 开始执行");
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + ", apply stage 执行完毕");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return s1.toUpperCase();
        }, Executors.newSingleThreadExecutor());

        Thread.sleep(Long.MAX_VALUE);
    }
}

