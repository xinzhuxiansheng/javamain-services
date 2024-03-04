package com.yzhou.rpc.enhance.rpc;

import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

public interface MainThreadExecutable {
    void runAsync(Runnable runnable);

    <V> CompletableFuture<V> callAsync(Callable<V> callable, Duration callTimeout);

    void scheduleRunAsync(Runnable runnable, long delay);
}
