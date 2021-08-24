package com.javamain.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicReference;

public class SpinLock {
    private static final Logger logger = LoggerFactory.getLogger(SpinLock.class);

    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void myLock(){
        Thread thread = Thread.currentThread();
        logger.info("{} invoke myLock()", Thread.currentThread());
        while(!atomicReference.compareAndSet(null,thread)){

        }
    }

    public void myUnlock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread,null);
        logger.info("{} invoke myUnlock()");
    }

    public static void main(String[] args) {
        SpinLock spinLock = new SpinLock();
        new Thread(()->{
            spinLock.myLock();
            // do somethings

            spinLock.myUnlock();
        });
    }
}
