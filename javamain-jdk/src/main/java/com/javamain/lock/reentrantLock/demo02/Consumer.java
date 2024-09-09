package com.javamain.lock.reentrantLock.demo02;

public class Consumer<T> implements Runnable {
    private String name;
    private final Queue<T> queue;

    public Consumer(Queue<T> queue, String name) {
        this.queue = queue;
        this.name = name;
    }

    @Override
    public void run() {
        while (true) {
            T val = queue.take();
            System.out.println("threadName: " + name + " , Consumed : " + val);
        }
    }
}
