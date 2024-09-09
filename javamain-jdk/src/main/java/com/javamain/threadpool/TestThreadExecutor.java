package com.javamain.threadpool;

import java.util.Date;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;

/**
 * @author yzhou
 * @date 2023/3/9
 */

// newCachedThreadPool
public class TestThreadExecutor {

    public static void main(String[] args) {
        createThreadExecutor();
    }


    /*
        一共有7个参数
        1. corePoolSize: 核心线程数，线程池中始终存活的线程数
        2. maximumPoolSize: 最大线程数,线程池中允许的最大线程数
        3. keepAliveTime: 存活时间,线程没有任务执行时最多保持多久时间会终止
        4. unit: 单位，参数keepAliveTime的时间单位，7种可选
            TimeUnit.DAYS、HOURS、MINUTES、SECONDS、MILLISECONDS、MICROSECOND、NANOSECONDS
        5. workQueue: 一个阻塞队列，用来存储等待执行的任务，均为线程安全，7种可选
            ArrayBlockingQueue,一个由数组结构组成的有界阻塞队列
            LinkedBlockingQueue,一个由链表结构组成的有界阻塞队列
            SynchronousQueue,一个不存储元素的阻塞队列，即直接提交给线程不保存它们
            PriorityBlockingQueue,一个支持优先级排序的无界阻塞队列
            DelayQueue,一个使用优先级队列实现的无界阻塞队列，只有在延迟期满时才能从中提取元素
            LinkedTransferQueue,一个由链表结构组成的无界阻塞队列，与SynchronousQueue类似，还含有非阻塞方法
            LinkedBlockingDeque,一个由链表结构组成的双向阻塞队列

        6. threadFactory: 线程工厂，主要用来创建线程，默认正常优先级，非守护线程
        7. handler: 拒绝策略，拒绝处理任务时的策略，4种可选，默认为AbortPolicy
            AbortPolicy,拒绝并抛出异常
            CallerRunsPolicy,重试提交当前的任务，即再次调用运行该任务的execute()方法
            DiscardOldestPolicy,抛弃队列头部（最旧）的一个任务，并执行当前任务
            DiscardPolicy,抛弃当前任务



        线程池的执行规则如下：
        1. 当线程数小于核心线程数时，创建线程
        2. 当线程数大于等于核心线程数，且任务队列未满时，将任务放入任务队列
        3. 当线程数大于等于核心线程数，且任务队列已满：
           若线程数小于最大线程数，创建线程
           若线程数等于最大线程数，抛出异常，拒绝任务
     */

    private static void createThreadExecutor() {
        ExecutorService executorService = new ThreadPoolExecutor(2, 10,
                1, TimeUnit.MINUTES, new ArrayBlockingQueue<>(5, true),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
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
