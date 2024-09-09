package com.javamain.timingwheel;

/**
 * 用来定义 SystemTimer的行为接口
 */
public interface Timer {

    void add(TimerTask timerTask);

    boolean advanceClock(long timeoutMs);

    int size();

    void shutdown();
}
