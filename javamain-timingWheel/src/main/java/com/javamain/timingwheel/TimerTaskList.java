package com.javamain.timingwheel;


import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;


/**
 * TimerTaskList 除了包含 TimerTaskEntry root哨兵节点外，还包含其他参数，例如 最小过期时间，任务数，所以它非常重要
 */
@ThreadSafe
class TimerTaskList implements Delayed {


    private AtomicInteger taskCounter;
    /**
     * 哨兵节点
     */
    private TimerTaskEntry root;

    private AtomicLong expiration;

    public TimerTaskList() {
    }

    public TimerTaskList(AtomicInteger taskCounter) {
        // TimerTaskList forms a doubly linked cyclic list using a dummy root entry
        // root.next points to the head
        // root.prev points to the tail
        this.taskCounter = taskCounter;
        this.root = new TimerTaskEntry(null, -1L);
        this.root.next = root;
        this.root.prev = root;
        this.expiration = new AtomicLong(-1L);
    }

    /**
     * 设置过期时间
     */
    public boolean setExpiration(Long expirationMs) {
        return expiration.getAndSet(expirationMs) != expirationMs;
    }

    /**
     * 获取过期时间
     */
    public Long getExpiration() {
        return expiration.get();
    }

    /**
     * 遍历任务
     */
    public void foreach(Function<TimerTask, Void> f) {
        synchronized (this) {
            TimerTaskEntry entry = root.next;
            while (entry != root) {
                TimerTaskEntry nextEntry = entry.next;

                if (!entry.cancelled()) {
                    f.apply(entry.getTimerTask());
                }

                entry = nextEntry;
            }
        }
    }

    /**
     * 插入 entry
     */
    public void add(TimerTaskEntry timerTaskEntry) {
        boolean done = false;
        while (!done) {
            // remove() 的作用是为了清空 timerTaskEntry的链表引用关系，保证它是 single
            timerTaskEntry.remove();
            synchronized (this) {
                synchronized (timerTaskEntry) {
                    // 插入之前 必须确保 当前的 timerTaskEntry 无任何引用关系
                    if (timerTaskEntry.getList() == null) {
                        // put the timer task entry to the end of the list. (root.prev points to the tail entry)
                        TimerTaskEntry tail = root.prev;
                        timerTaskEntry.next = root;
                        timerTaskEntry.prev = tail;
                        timerTaskEntry.setList(this);
                        tail.next = timerTaskEntry;
                        root.prev = timerTaskEntry;
                        taskCounter.incrementAndGet();
                        done = true;
                    }
                }
            }
        }
    }

    /**
     * 移除某个 entry
     */
    public void remove(TimerTaskEntry timerTaskEntry) {
        synchronized (this) {
            synchronized (timerTaskEntry) {
                if (timerTaskEntry.getList() == this) {
                    timerTaskEntry.next.prev = timerTaskEntry.prev;
                    timerTaskEntry.prev.next = timerTaskEntry.next;
                    timerTaskEntry.next = null;
                    timerTaskEntry.prev = null;
                    timerTaskEntry.setList(null);
                    taskCounter.decrementAndGet();
                }
            }
        }
    }

    /**
     * 移除当前双向链表的 所有项，并且使用 f.aaply() 来处理 时间轮降级插入，而降级的判断标准是 entry的过期时间是否 小于 （当前时间 + 当前时间轮的一圈时间）
     */
    public void flush(Function<TimerTaskEntry, Void> f) {
        synchronized (this) {
            TimerTaskEntry head = root.next;
            while (head != root) {
                remove(head);
                f.apply(head);
                head = root.next;
            }
            expiration.set(-1L);
        }
    }

    /*
        DelayQueue是一个支持延迟获取元素的无界阻塞队列。 队列中的元素只有在其延迟到期时才能从队列中获取。
        Delayed 接口中的 getDelay(TimeUint unit) 方法用于返回与次对象相关的剩余延迟时间，给定的时间单位是作为方法参数传入。
        返回的值为正，则延迟尚未过期，返回的值为0或者为负值，则延迟已过期。

        使用poll()方法，取出过期的元素，如果没有元素过期，它会返回null。
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(Long.max(getExpiration() - Time.getHiresClockMs(), 0), TimeUnit.MILLISECONDS);
    }

    /**
     * 处理延迟队列中的排序问题，保证 poll() 获取的是最早过期项
     */
    @Override
    public int compareTo(Delayed d) {
        TimerTaskList other;
        if (d instanceof TimerTaskList) {
            other = (TimerTaskList) d;
        } else {
            throw new ClassCastException("can not cast to TimerTaskList");
        }
        return Long.compare(getExpiration(), other.getExpiration());
    }
}
