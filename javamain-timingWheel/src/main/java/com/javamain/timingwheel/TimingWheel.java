package com.javamain.timingwheel;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.atomic.AtomicInteger;

@NotThreadSafe
public class TimingWheel {

    /**
     * 每一格时间
     */
    private Long tickMs;
    /**
     * 格子数
     */
    private Integer wheelSize;

    private Long interval;

    private Long startMs;

    private AtomicInteger taskCounter;

    private DelayQueue<TimerTaskList> queue;  // 时间轮的任务

    private Long currentTime;

    private volatile TimingWheel overflowWheel;

    private TimerTaskList[] buckets;  // 就是时间轮

    public TimingWheel() {
    }

    public TimingWheel(Long tickMs, Integer wheelSize, Long startMs, AtomicInteger taskCounter, DelayQueue<TimerTaskList> queue) {
        this.tickMs = tickMs;
        this.wheelSize = wheelSize;
        this.startMs = startMs;
        this.taskCounter = taskCounter;
        this.queue = queue;
        this.interval = tickMs * wheelSize;
        this.currentTime = startMs - (startMs % tickMs);
        this.buckets = new TimerTaskList[wheelSize];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new TimerTaskList(taskCounter);
        }
    }

    public boolean add(TimerTaskEntry timerTaskEntry) {
        long expiration = timerTaskEntry.getExpirationMs();

        // 判断是否取消
        if (timerTaskEntry.cancelled()) {
            // Cancelled
            return false;
        } else if (expiration < currentTime + tickMs) { // 判断是否过期
            // Already expired
            return false;
        } else if (expiration < currentTime + interval) { // 当前时间轮
            // Put in its own bucket
            long virtualId = expiration / tickMs;  // 确定虚拟槽位
            TimerTaskList bucket = buckets[(int) (virtualId % wheelSize)];
            bucket.add(timerTaskEntry);

            // 设置过期时间
            if (bucket.setExpiration(virtualId * tickMs)) {
                queue.offer(bucket);
            }
            return true;
        } else {
            // 时间轮溢出，这需要将其设置到 父时间轮中去。
            if (overflowWheel == null) {
                addOverflowWheel();
            }
            return overflowWheel.add(timerTaskEntry);
        }
    }

    /**
     * Try to advance the clock
     */
    public void advanceClock(Long timeMs) {
        if (timeMs >= currentTime + tickMs) {
            currentTime = timeMs - (timeMs % tickMs);

            // Try to advance the clock of the overflow wheel if present
            if (overflowWheel != null) {
                overflowWheel.advanceClock(currentTime);
            }
        }
    }


    /**
     * 增加溢出时间轮
     */
    private void addOverflowWheel() {
        synchronized (this) {
            if (overflowWheel == null) {
                overflowWheel = new TimingWheel(interval, wheelSize, currentTime, taskCounter, queue);
            }
        }
    }
}
