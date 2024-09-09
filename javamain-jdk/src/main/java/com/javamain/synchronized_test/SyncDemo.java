package com.javamain.synchronized_test;

public class SyncDemo {
    private static int counter = 0;
    private static String lock = "";
    public static void increment(){
        synchronized (lock){
            counter ++;
        }
    }
    public static void decrement(){
        synchronized (lock){
            counter--;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
            for(int i =0;i< 5000;i++){
                increment();
            }
        });

        Thread t2 = new Thread(()->{
            for(int i =0;i< 5000;i++){
                decrement();
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(counter);
    }
}
