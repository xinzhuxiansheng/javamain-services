package com.javamain.lock.reentrantLock.demo01;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AtomicDemo {
    // 共享变量
    private static int count = 0;
    private static Lock lock = new ReentrantLock();

    // 操作共享变量的方法
    public static void incr() {
        // 为了演示效果  休眠一下子
        try {
            lock.lock();
            Thread.sleep(50000);
            count++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 2; i++) {
            new Thread(() -> AtomicDemo.incr(), "yzhou-thd-" + i).start();
            Thread.sleep(1000);
        }
        Thread.sleep(5000);
        System.out.println("result:" + count);
    }
}