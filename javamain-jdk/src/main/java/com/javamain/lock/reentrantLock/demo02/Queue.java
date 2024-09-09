package com.javamain.lock.reentrantLock.demo02;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Queue<T> {
    private final int capacity;
    private final LinkedList<T> list;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();
    private final Condition notFull = lock.newCondition();

    public Queue(int capacity) {
        this.capacity = capacity;
        list = new LinkedList<>();
    }

    public void put(T val) {
        lock.lock();
        try {
            if (this.list.size() == capacity) {
                notFull.await();
            }
            this.list.add(val);
            notEmpty.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public T take() {
        lock.lock();
        T val = null;
        try {
            if (this.list.size() == 0) {
                notEmpty.await();
            }
            val = this.list.remove();
            notFull.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return val;
    }
}
