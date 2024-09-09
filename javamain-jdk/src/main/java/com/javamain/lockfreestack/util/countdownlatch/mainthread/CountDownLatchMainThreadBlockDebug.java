package com.javamain.lockfreestack.util.countdownlatch.mainthread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class CountDownLatchMainThreadBlockDebug {
    private static Logger logger = LoggerFactory.getLogger(CountDownLatchMainThreadBlockDebug.class);

    public static void main(String[] args) {
        try {
            CountDownLatch countDownLatch = new CountDownLatch(3);

            for (int i = 0; i < 3; i++) {
                HandleWork handleWork = new HandleWork("threadName-" + i, countDownLatch);
                Thread th =new Thread(handleWork);
                th.start();
            }

            logger.info("等待thread执行完");
            countDownLatch.await();
            logger.info("所有线程执行结束");
        } catch (Exception e) {
            logger.error("", e);
        }
    }
}

class HandleWork implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(HandleWork.class);

    private String threadName;
    private CountDownLatch countDownLatch;

    public HandleWork(String threadName, CountDownLatch countDownLatch) {
        this.threadName = threadName;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            Random random = new Random();
            Thread.sleep(10000);
            logger.info("threadName: {} 任务完成", threadName);
            countDownLatch.countDown();
        } catch (InterruptedException e) {
            logger.error("", e);
        }
    }
}
