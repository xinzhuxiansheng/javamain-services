package com.javamain.lock.reentrantLock.demo02;

public class TestMain {

    public static void main(String[] args) {
        Queue<Integer> queue = new Queue<>(10);
        Producer<Integer> producer01 = new Producer<Integer>(queue, "producer01");
        Producer<Integer> producer02 = new Producer<Integer>(queue, "producer02");
        Consumer<Integer> consumer01 = new Consumer<Integer>(queue, "consumer01");

        new Thread(producer01).start();
        new Thread(producer02).start();
        new Thread(consumer01).start();
    }
}
