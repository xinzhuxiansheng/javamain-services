package com.javamain.timingwheel;


/**
 * 封装定时任务
 */
public class TimerTaskEntry implements Comparable<TimerTaskEntry> {


    private volatile TimerTaskList list;

    public TimerTaskEntry next;

    public TimerTaskEntry prev;

    private TimerTask timerTask;

    private Long expirationMs;

    public TimerTaskEntry() {
    }

    /**
     * 构造器
     *
     * @param timerTask    定时任务
     * @param expirationMs 到期时间
     */
    public TimerTaskEntry(TimerTask timerTask, Long expirationMs) {
        if (timerTask != null) {
            timerTask.setTimerTaskEntry(this);
        }
        this.timerTask = timerTask;
        this.expirationMs = expirationMs;

    }


    public boolean cancelled() {
        return timerTask.getTimerTaskEntry() != this;
    }

    /**
     * 双向链表 清空
     */
    public void remove() {
        TimerTaskList currentList = list;
        while (currentList != null) {
            currentList.remove(this);
            currentList = list;
        }
    }

    @Override
    public int compareTo(TimerTaskEntry that) {
        if (that == null) {
            throw new NullPointerException("TimerTaskEntry is null");
        }
        return Long.compare(this.expirationMs, that.expirationMs);
    }


    public Long getExpirationMs() {
        return expirationMs;
    }

    public TimerTask getTimerTask() {
        return timerTask;
    }

    public TimerTaskList getList() {
        return list;
    }

    public void setList(TimerTaskList list) {
        this.list = list;
    }
}
