package com.yzhou.rpc.enhance.taskmanager;

import com.yzhou.rpc.enhance.rpc.Configuration;
import com.yzhou.rpc.enhance.rpc.RpcService;
import com.yzhou.rpc.enhance.rpc.RpcUtils;

import java.util.concurrent.ExecutionException;

public class TaskManagerRunner {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Configuration configuration = new Configuration();
        configuration.setProperty("actor.system.name","taskmanager");
        RpcService rpcService = RpcUtils.createRpcService(configuration);
        TaskExecutor taskExecutor = new TaskExecutor(rpcService,"task-executor");
    }
}
