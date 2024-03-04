package com.yzhou.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Demo4 {

    public static void main(String[] args) {
        AtomicInteger counter =  new AtomicInteger(0);
        ExecutorService executorService = Executors.newFixedThreadPool(27);

        System.out.println(System.currentTimeMillis() + ": 任务开始");
        for (int i = 0; i < 3000000; i++) {
            executorService.submit(new MyTask2(counter));
        }

        while(true){
            if(counter.get() == 3000000){
                System.out.println(System.currentTimeMillis() + ": 任务全部完成");
                break;
            }
        }
    }
}

class MyTask2 implements Runnable{
    AtomicInteger counter;
    public MyTask2(AtomicInteger counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        counter.incrementAndGet();
    }
}
