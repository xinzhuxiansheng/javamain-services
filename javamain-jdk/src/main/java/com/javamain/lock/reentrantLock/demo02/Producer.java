package com.javamain.lock.reentrantLock.demo02;

import java.util.Random;

public class Producer<T> implements Runnable {
    private String name;
    private final Queue<T> queue;
    private final Random rand = new Random();

    public Producer(Queue<T> queue, String name) {
        this.queue = queue;
        this.name = name;
    }
    @Override
    public void run() {
        while (true) {
            Integer val = rand.nextInt(10);
            queue.put((T) val);
            System.out.println("threadName: " + name + " , Produced  " + val);
        }

    }
}
