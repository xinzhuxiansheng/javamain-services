package com.yzhou.test;

import java.util.concurrent.atomic.AtomicInteger;

public class Demo3 {
    public static void main(String[] args) {
        AtomicInteger counter =  new AtomicInteger(0);
        System.out.println(System.currentTimeMillis()/1000 + ": 任务开始");

        for (int i = 0; i < 1000000; i++) {
            new Thread(new MyTask(counter)).start();
        }

        while(true){
            if(counter.get() == 1000000){
                System.out.println(System.currentTimeMillis()/1000 + ": 任务全部完成");
                break;
            }
        }
    }
}

class MyTask implements Runnable{

    AtomicInteger counter;

    public MyTask(AtomicInteger counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        counter.incrementAndGet();
    }
}
