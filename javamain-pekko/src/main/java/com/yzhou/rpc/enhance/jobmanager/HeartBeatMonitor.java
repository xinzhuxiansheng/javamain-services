package com.yzhou.rpc.enhance.jobmanager;

import com.yzhou.rpc.enhance.rpc.RpcEndpoint;
import com.yzhou.rpc.enhance.taskmanager.TaskExecutorGateway;

import java.util.concurrent.CompletableFuture;

public class HeartBeatMonitor {

    private TaskExecutorGateway taskExecutor;
    private String resourceId;
    private JobMaster.HeartBeatListener heartBeatListener;
    private long lastHeartBeatTime;
    private int failureCount;

    RpcEndpoint.MainThreadExecutor mainThreadExecutor;

    public HeartBeatMonitor(TaskExecutorGateway taskExecutor, String resourceId, JobMaster.HeartBeatListener heartBeatListener, RpcEndpoint.MainThreadExecutor mainThreadExecutor) {
        this.taskExecutor = taskExecutor;
        this.resourceId = resourceId;
        this.heartBeatListener = heartBeatListener;
        this.mainThreadExecutor = mainThreadExecutor;

    }


    public void handleHeartBeatFailure(){
        failureCount++;

        System.out.println("monitor内,心跳请求失败: " + failureCount);
        if(failureCount >= 3 ){
            heartBeatListener.notifyHeartBeatFailure(resourceId);
        }
    }



    public void requestHeartBeat(){

        CompletableFuture<String> completableFuture = taskExecutor.heartBeatFromJobManager("你还在吗?");

        completableFuture.whenCompleteAsync((s,ex)->{

            if(ex != null){
                handleHeartBeatFailure();

            }else{
                System.out.println("收到心跳回应: " + s);
                failureCount = 0;
                this.lastHeartBeatTime = System.currentTimeMillis();

            }
        },mainThreadExecutor);

    }
}
