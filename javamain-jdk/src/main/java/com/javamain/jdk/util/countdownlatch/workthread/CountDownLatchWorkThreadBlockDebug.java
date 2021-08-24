package com.javamain.jdk.util.countdownlatch.workthread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class CountDownLatchWorkThreadBlockDebug {
    private static Logger logger = LoggerFactory.getLogger(CountDownLatchWorkThreadBlockDebug.class);

    public static void main(String[] args) {
        try {
            CountDownLatch countDownLatch = new CountDownLatch(1);

            for (int i = 0; i < 3; i++) {
                HandleWork handleWork = new HandleWork("threadName-" + i, countDownLatch);
                Thread th =new Thread(handleWork);
                th.start();
            }

            logger.info("主线程开始处理 sleep 10000");
            Thread.sleep(10000);
            countDownLatch.countDown();
            logger.info("主线程执行结束");
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
            countDownLatch.await();
            logger.info("threadName: {} 任务开始", threadName);
            Random random = new Random();
            Thread.sleep(random.nextInt(10000));
            logger.info("threadName: {} 任务完成", threadName);
        } catch (InterruptedException e) {
            logger.error("", e);
        }
    }
}
