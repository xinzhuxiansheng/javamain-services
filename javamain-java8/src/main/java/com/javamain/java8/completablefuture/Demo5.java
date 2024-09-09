package com.javamain.java8.completablefuture;

import java.util.concurrent.CompletableFuture;

public class Demo5 {

    public static void main(String[] args) throws Exception {

        CompletableFuture<String> ctf1 = CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("haha");
//            return "a";
        });

        // whenComplete可以接收上一个stage的正常结果和异常
        // 然后 whenComplete 本身返回的future，装的是上一个stage的正常结果
//        CompletableFuture<String> future = ctf1.whenComplete((r, ex) -> {
//            if (ex != null) {
//                System.out.println("ctf1发生了异常");
//            } else {
//                System.out.println("ctf1正常完成了");
//            }
//        });
//
//        if (!future.isCompletedExceptionally()) {
//            System.out.println(future.get());
//        }

        CompletableFuture<String> future = ctf1.handle((r, ex) -> {
            if (ex == null) {
                return r.toUpperCase();
            } else {
                System.out.println("出了异常");
                return "异常后的默认值";
            }
        });
        System.out.println(future.get());
        Thread.sleep(Long.MAX_VALUE);
    }
}

