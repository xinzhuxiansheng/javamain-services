package com.javamain.java8.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Demo4 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

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

        CompletableFuture<String> ctf2 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + ", task2 开始执行");
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + ", task2 执行完毕");

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "task2-res";
        });

        // accept功能：接收上一个stage的结果，然后执行一个没有返回值的新stage
//        CompletableFuture<Void> future = ctf1.thenAccept((s) -> {
//            try {
//                System.out.println(Thread.currentThread().getName() + ", accept stage 开始执行");
//                Thread.sleep(2000);
//                System.out.println(Thread.currentThread().getName() + ", accept stage 执行完毕");
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        });


        // thenRun功能：消费上一个stage的完成“事件”，不会消费上一个stage的计算结果;
//        ctf1.thenRun(()->{
//            try {
//                System.out.println(Thread.currentThread().getName() + ", run stage 开始执行");
//                Thread.sleep(2000);
//                System.out.println(Thread.currentThread().getName() + ", run stage 执行完毕");
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        });

        // thenCombine是对自身stage和传入的other stage进行合并，将它们俩的计算结果，作为入参，传给新的stage加工并返回最终结果
        CompletableFuture<String> future = ctf1.thenCombine(ctf2, (s1, s2) -> {
            System.out.println(Thread.currentThread().getName() + ", combine stage 开始执行");
            return s1 + "----" + s2;
        });
        System.out.println(future.get());
        Thread.sleep(Long.MAX_VALUE);
    }
}

