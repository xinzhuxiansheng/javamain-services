package com.javamain.zookeeperFeatures.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ZKEventThreadPool {
    private static final Logger logger = LoggerFactory.getLogger(ZKEventThreadPool.class);

    private ThreadPoolExecutor pool = null;
    private static AtomicInteger index = new AtomicInteger(0);

    public ZKEventThreadPool(Integer poolSize) {
        pool = new ThreadPoolExecutor(
                poolSize,
                poolSize,
                30,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>(),
                new ZKEventThreadFactory()
                );
    }

    private class ZKEventThreadFactory implements ThreadFactory {
        private AtomicInteger count = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            String threadName = "ZkClient-EventThread-" + count.addAndGet(1);
            t.setName(threadName);
            return t;
        }
    }
}
