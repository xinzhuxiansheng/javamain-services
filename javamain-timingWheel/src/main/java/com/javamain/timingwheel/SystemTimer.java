package com.javamain.timingwheel;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;

public class SystemTimer implements Timer, Function<TimerTaskEntry, Void> {

    private final ExecutorService taskExecutor;

    // DelayQueue 是 Java 并发库中的一个特殊类型的无界阻塞队列，它只允许其中的元素在其延迟过期后被取出。
    // 这就意味着只有当元素的延迟为0或者为负数时，才能从队列中获取该元素
    private final DelayQueue<TimerTaskList> delayQueue = new DelayQueue<>();

    private final AtomicInteger taskCounter = new AtomicInteger(0);

    private final TimingWheel timingWheel;

    /**
     * Locks used to protect data structures while ticking
     */
    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private final ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();

    private final ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

    public SystemTimer(String executeName) {
        Long tickMs = 1L;
        Integer wheelSize = 20;
        Long startMs = Time.getHiresClockMs();
        taskExecutor = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(Integer.MAX_VALUE), r -> new Thread(r, "executor" + executeName));
        timingWheel = new TimingWheel(tickMs, wheelSize, startMs, taskCounter, delayQueue);
    }

    /*
        添加任务
     */
    @Override
    public void add(TimerTask timerTask) {
        readLock.lock();
        try {
            // getHiresClockMs() 返回的是当前时间 + getDelayMs() 延迟时长 = 下一次执行的间隔时长
            addTimerTaskEntry(new TimerTaskEntry(timerTask, timerTask.getDelayMs() + Time.getHiresClockMs()));
        } finally {
            readLock.unlock();
        }
    }

    /**
     * 独立线程中 推进时间前进
     */
    @Override
    public boolean advanceClock(long timeoutMs) {
        try {
            // timeoutMs：等待的最大时间，若没有到期的元素，它会等待最多 timeoutMs 毫秒，
            // 有元素到期，他会返回这个元素；否则，当超时后，它会返回 null
            TimerTaskList bucket = delayQueue.poll(timeoutMs, TimeUnit.MILLISECONDS); // 可知道 队列中
            if (bucket != null) {
                writeLock.lock();
                try {
                    // delayQueue调用 poll()方法，每次只能获取一个，当知晓存在 过期的元素，
                    // 那么从需 遍历
                    while (bucket != null) {
                        timingWheel.advanceClock(bucket.getExpiration());
                        // 遍历双向链表
                        bucket.flush(this);
                        bucket = delayQueue.poll();
                    }
                } finally {
                    writeLock.unlock();
                }
                return true;
            } else {
                return false;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int size() {
        return taskCounter.get();
    }

    @Override
    public void shutdown() {
        taskExecutor.shutdown();
    }

    private void addTimerTaskEntry(TimerTaskEntry timerTaskEntry) {
        if (!timingWheel.add(timerTaskEntry)) {
            // Already expired or cancelled
            if (!timerTaskEntry.cancelled()) {
                taskExecutor.submit(timerTaskEntry.getTimerTask());
            }
        }
    }

    /*
       该方法主要处理2个方面
       前提，你必须意识到一点：
       3.注意 到期时间在 [400ms,800ms) 区间的多个任务（比如446ms、455ms以及473ms的定时任务）都会被放入到第三层时间轮的时间格 1 中，时间格 1 对应的TimerTaskList的超时时间为400ms；
       4.随着时间的流逝，当次 TimerTaskList 到期之时，原本定时为 450ms 的任务还剩下 50ms 的时间，还不能执行这个任务的到期操作。这里就有一个 时间轮`降级` 的操作，会将这个剩余时间为 50ms 的定时任务重新提交到层级时间轮中，此时第一层时间轮的总体时间跨度不够，而第二层足够，所以该任务被放到第二层时间轮到期时间为 [40ms,60ms) 的时间格中；
       5.再经历了 40ms 之后，此时这个任务又被“察觉”到，不过还剩余 10ms，还是不能立即执行到期操作。所以还要再有一次时间轮的`降级`，此任务被添加到第一层时间轮到期时间为 [10ms,11ms) 的时间格中，之后再经历 10ms 后，此任务真正到期，最终执行相应的到期操作。

       所以当多层的时间轮某个间隔出现了entry 过期，并不意味着该 "双向链表" 全部都需要执行，这里反而需要对未过期的 entry 进行 降级操作。
     */
    @Override
    public Void apply(TimerTaskEntry timerTaskEntry) {
        addTimerTaskEntry(timerTaskEntry);
        return null;
    }
}
