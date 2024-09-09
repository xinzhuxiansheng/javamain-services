package com.javamain.algorithms.ratelimiting;

import java.util.LinkedList;
import java.util.Queue;

public class SlidingWindowLog extends RateLimiter{
    private final Queue<Long> log = new LinkedList<>();

    protected SlidingWindowLog(int maxRequestPerSec) {
        super(maxRequestPerSec);
    }

    @Override
    boolean allow() {
        long curTime = System.currentTimeMillis();
        long boundary = curTime - 1000;
        synchronized (log) {
            while (!log.isEmpty() && log.element() <= boundary) {
                log.poll();
            }
            log.add(curTime);
            return log.size() <= maxRequestPerSec;
        }
    }
}
