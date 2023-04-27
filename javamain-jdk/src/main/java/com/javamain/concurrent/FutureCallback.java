package com.javamain.concurrent;

public interface FutureCallback<V> {
    void onSuccess(V result);
    void onFailure(Throwable e);
}
