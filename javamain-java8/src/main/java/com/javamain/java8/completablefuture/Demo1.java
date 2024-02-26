package com.javamain.java8.completablefuture;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Demo1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = new CompletableFuture<>();
        System.out.println(future.isDone());

        future.complete("over");
        System.out.println(future.isDone());

        // get是阻塞的
        String s = future.get();
        System.out.println(s);
    }
}
