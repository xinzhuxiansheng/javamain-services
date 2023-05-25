package com.javamain.concurrent.example01.chapter2.base;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SleepTest {
    //1. 创建一个独占锁
    private static final Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {

        //2. 创建线程A
        Thread threadA = new Thread(new Runnable() {
            public void run() {
                // 获取独占锁
                lock.lock();
                try {
                    System.out.println("child threadA is in sleep");
                    Thread.sleep(10000);
                    System.out.println("child threadA is in awaked");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // 释放锁
                    lock.unlock();
                }
            }
        });

        //2. 创建线程B
        Thread threadB = new Thread(new Runnable() {
            public void run() {
                // 获取独占锁
                lock.lock();
                try {
                    System.out.println("child threadB is in sleep");

                    Thread.sleep(10000);
                    System.out.println("child threadB is in awaked");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // 释放锁
                    lock.unlock();
                }
            }
        });

        // 启动线程
        threadA.start();
        threadB.start();

    }
}
