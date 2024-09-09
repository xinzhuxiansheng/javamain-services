package com.javamain.timingwheel;

import java.util.concurrent.TimeUnit;

public class Time {

    public static Long getHiresClockMs() {
        // System.nanoTime() 返回的值是是从一个任意的起点开始的，
        // 不能依赖其绝对值，而只应该使用它来测量时间间隔。

        // 但不过有一点 可以肯定的是：每次在同一个JVM实例中调用 System.nanoTime()，它返回的值都是基于同一个起点来计数的。这个起点在JVM启动时被确定，并且在JVM的整个生命周期中保持不变。

        // TimeUnit.NANOSECONDS.toMillis() 其作用是将时间单位从纳秒转换为毫秒
        return TimeUnit.NANOSECONDS.toMillis(System.nanoTime());
    }
}
