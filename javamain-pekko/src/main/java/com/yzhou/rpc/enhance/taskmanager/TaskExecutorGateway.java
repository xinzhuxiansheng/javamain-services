package com.yzhou.rpc.enhance.taskmanager;

import com.yzhou.rpc.enhance.rpc.RpcGateway;

import java.util.concurrent.CompletableFuture;

public interface TaskExecutorGateway extends RpcGateway {
    public String queryState();
    public String submitTask(String task);
    public CompletableFuture<String> heartBeatFromJobManager(String payload);
}
