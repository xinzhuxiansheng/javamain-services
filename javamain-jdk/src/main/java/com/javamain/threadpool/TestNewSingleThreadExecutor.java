package com.javamain.threadpool;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

/**
 * @author yzhou
 * @date 2023/3/9
 */

// newSingleThreadExecutor
public class TestNewSingleThreadExecutor {

    public static void main(String[] args) {
        createNewSingleThreadPool();
    }


    private static void createNewSingleThreadPool() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
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
