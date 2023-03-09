package com.javamain.threadpool;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

/**
 * @author yzhou
 * @date 2023/3/9
 */

// newCachedThreadPool
public class TestNewCachedThreadPool {

    public static void main(String[] args) {
        createNewCachedThreadPool();
    }

    // 因为初识线程池没有线程，而线程不足会创建新线程，所以线程名都不一样
    private static void createNewCachedThreadPool() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            executorService.execute(() -> {
                // 获取线程名称,默认格式:pool-1-thread-1
                System.out.println(new Date().getTime() + " " + Thread.currentThread().getName() + " " + index);
                // 等待2秒
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
