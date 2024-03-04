package com.yzhou.rpc.enhance.jobmanager;

import com.yzhou.rpc.enhance.rpc.RpcGateway;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface JobMasterGateway extends RpcGateway {
    public String registerTaskExecutor(String taskExecutorAddress, String resourceId) throws ExecutionException, InterruptedException;

    public CompletableFuture<String> getMasterId();
}
