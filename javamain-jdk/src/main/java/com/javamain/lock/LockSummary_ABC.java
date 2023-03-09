package com.javamain.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yzhou
 * @date 2023/2/16
 */
public class LockSummary_ABC {

    private Lock lock = new ReentrantLock();

    private Condition a = lock.newCondition();
    private Condition b = lock.newCondition();
    private Condition c = lock.newCondition();

    private Map<Integer, Condition> condtionContext = new HashMap<Integer, Condition>();

    public LockSummary_ABC() {
        condtionContext.put(Integer.valueOf(0),a);
        condtionContext.put(Integer.valueOf(1),b);
        condtionContext.put(Integer.valueOf(2),c);
    }

    private int count=0;

    public void print(int id) {
        lock.lock();
        try {
            while (count  < 30) {
                if (id == count%3) {
                    if(id==0) {
                        System.out.println(Thread.currentThread()+": "+"A");
                    } else if(id==1) {
                        System.out.println(Thread.currentThread()+": "+"B");
                    } else {
                        System.out.println(Thread.currentThread()+": "+"C");
                    }
                    count++;
                    Condition nextConditon = condtionContext.get(Integer.valueOf(count%3));
                    nextConditon.signal();
                } else {
                    Condition condition = condtionContext.get(Integer.valueOf(id));
                    condition.await();
                }
            }
            for (Condition c : condtionContext.values())
            {
                c.signal();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        final CountDownLatch latch = new CountDownLatch(1);
        final LockSummary_ABC printer = new LockSummary_ABC();
        for (int i = 0; i < 3; i++) {
            final int id = i;
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    printer.print(id);
                }
            });
        }
        System.out.println("三个任务开始顺序打印数字。。。。。。");
        latch.countDown();
        executor.shutdown();
    }

}
