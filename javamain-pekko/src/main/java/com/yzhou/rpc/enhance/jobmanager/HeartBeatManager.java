package com.yzhou.rpc.enhance.jobmanager;

import com.yzhou.rpc.enhance.rpc.RpcEndpoint;
import com.yzhou.rpc.enhance.taskmanager.TaskExecutorGateway;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HeartBeatManager implements Runnable {

    // 心跳目标taskExecutor集合<ResourceId,Gateway>
    Map<String, HeartBeatMonitor> monitorMap = new HashMap<>();
    //ScheduledExecutorService executor;
    RpcEndpoint.MainThreadExecutor mainThreadExecutor;
    JobMaster.HeartBeatListener listener;


    public HeartBeatManager(JobMaster.HeartBeatListener listener, RpcEndpoint.MainThreadExecutor mainThreadExecutor) {

        this.listener = listener;
        // 不要自己fork新线程来执行异步调度任务了
        //this.executor = Executors.newSingleThreadScheduledExecutor();
        this.mainThreadExecutor = mainThreadExecutor;

        mainThreadExecutor.schedule(this, 0, TimeUnit.SECONDS);
    }


    @Override
    public void run() {
        System.out.println("心跳调度..........");

        for (Map.Entry<String, HeartBeatMonitor> entry : monitorMap.entrySet()) {

            HeartBeatMonitor beatMonitor = entry.getValue();
            beatMonitor.requestHeartBeat();

        }

        mainThreadExecutor.schedule(this, 1, TimeUnit.SECONDS);

    }


    // 增加心跳监控目标
    public void monitHeartBeatTarget(String resourceId, TaskExecutorGateway taskExecutor) {

        HeartBeatMonitor heartBeatMonitor = new HeartBeatMonitor(taskExecutor, resourceId, listener,mainThreadExecutor);
        monitorMap.put(resourceId, heartBeatMonitor);

    }


    // 增加心跳监控目标
    public void removeHeartBeatTarget(String resourceId) {

        monitorMap.remove(resourceId);

    }


}
