package com.javamain.timingwheel;


/**
 * 任务线程
 */
public abstract class TimerTask implements Runnable {

    /**
     * timestamp in millisecond
     */
    protected Long delayMs = 30000L;

    protected TimerTaskEntry timerTaskEntry;

    public void cancel() {
        synchronized (this) {
            if (timerTaskEntry != null) {
                timerTaskEntry.remove();
            }
            timerTaskEntry = null;
        }
    }

    public void setTimerTaskEntry(TimerTaskEntry entry) {
        synchronized (this) {
            if (timerTaskEntry != null && timerTaskEntry != entry) {
                timerTaskEntry.remove();
            }
            timerTaskEntry = entry;
        }
    }

    public TimerTaskEntry getTimerTaskEntry() {
        return timerTaskEntry;
    }

    public Long getDelayMs() {
        return delayMs;
    }
}
