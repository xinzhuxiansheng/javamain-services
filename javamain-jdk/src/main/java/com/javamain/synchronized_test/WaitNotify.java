package com.javamain.synchronized_test;

/**
 * @author yzhou
 * @date 2023/2/16
 */

/*



 */



public class WaitNotify {

    public static void main(String[] args) {
        Object lock = new Object();
        // thread1
        new Thread(() -> {
            System.out.println("thread1 is ready");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {

            }
            synchronized (lock) {
                lock.notify();
                System.out.println("thread1 is notify,but not exit synchronized");
                System.out.println("thread1 is exit synchronized");
            }
        }).start();

        // thread2
        new Thread(() -> {
            System.out.println("thread2 is ready");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
            synchronized (lock) {
                try {
                    System.out.println("thread2 is waiting");
                    lock.wait();
                    System.out.println("thread2 is awake");
                } catch (InterruptedException e) {
                }
            }
        }).start();
    }
}

