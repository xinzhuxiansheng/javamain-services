package com.yzhou.rpc.enhance.taskmanager;

import com.yzhou.rpc.enhance.jobmanager.JobMasterGateway;
import com.yzhou.rpc.enhance.rpc.RpcEndpoint;
import com.yzhou.rpc.enhance.rpc.RpcService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class TaskExecutor extends RpcEndpoint implements TaskExecutorGateway {
    public TaskExecutor(RpcService rpcService, String endpointId) throws ExecutionException, InterruptedException {
        super(rpcService, endpointId);
        // 自己的构造逻辑
        registerTaskExecutor();
    }

    //----   rpc方法  ----------
    @Override
    public String queryState() {
        System.out.println("收到job-master的状态查询请求");
        return "some state from executor";
    }

    @Override
    public String submitTask(String task) {
        return "submit task success from executor";
    }

    @Override
    public CompletableFuture<String> heartBeatFromJobManager(String payload) {
        System.out.println("taskExecutor收到一次心跳请求: " + payload);
        return CompletableFuture.supplyAsync(() -> getEndpointId() + "-ok");
    }

    public void registerTaskExecutor() throws ExecutionException, InterruptedException {
        JobMasterGateway jobMaster = rpcService.connect("pekko://jobmanager@127.0.0.1:17338/user/job_master", JobMasterGateway.class);
        String registerResponse = jobMaster.registerTaskExecutor(rpcService.getAddress(this.getEndpointId()), this.getEndpointId());
        System.out.println("收到注册的响应： " + registerResponse);
    }
}

