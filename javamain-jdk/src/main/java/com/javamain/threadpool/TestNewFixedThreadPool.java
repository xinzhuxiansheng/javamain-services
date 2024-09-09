package com.javamain.threadpool;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

/**
 * @author yzhou
 * @date 2023/3/9
 */

// newFixedThreadPool
public class TestNewFixedThreadPool {

    public static void main(String[] args) {
        createNewFixedThreadPool();
    }

    // 因为线程池大小固定，这里设置线程池总数为3，若线程不足时会进入队列等待直到线程空闲
    // 它不提供等待超时时间的设置
    // 使用submit提交任务时，如果任务抛出异常，可以通过调用 Future.get()获取异常信息
    private static void createNewFixedThreadPool() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
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
