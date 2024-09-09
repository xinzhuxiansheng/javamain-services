package com.yzhou.rpc.enhance.rpc;

import java.util.concurrent.*;

/**
 * RpcEndpoint 负责 RPC的最外层包装
 */
public class RpcEndpoint implements RpcGateway{

    public RpcService rpcService;
    private String endpointId;
    RpcServer rpcServer;
    private MainThreadExecutor mainThreadExecutor;

    protected RpcEndpoint(RpcService rpcService,String endpointId){
        this.endpointId = endpointId;
        this.rpcService = rpcService;
        // 做各类rpc组件准备工作
        rpcServer = rpcService.startServer(this);
        mainThreadExecutor = new MainThreadExecutor(rpcServer);

    }

    public MainThreadExecutor getMainThreadExecutor() {
        return mainThreadExecutor;
    }

    public String getEndpointId(){
        return this.endpointId;
    }

    // endpoint内，用于提交各类异步任务的工具
    public static class MainThreadExecutor implements Executor {
        MainThreadExecutable rpcServer;
        ScheduledExecutorService mainScheduledExecutor;

        public MainThreadExecutor(MainThreadExecutable rpcServer){
            this.rpcServer = rpcServer;
            this.mainScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        }

        @Override
        public void execute(Runnable runnable) {
            mainScheduledExecutor.execute(()-> rpcServer.runAsync(runnable));
            //rpcServer.runAsync(runnable);
        }

        public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit){
            ScheduledFuture<?> scheduledFuture = mainScheduledExecutor.schedule(() -> {
                rpcServer.runAsync(command);
            }, delay, unit);
            return scheduledFuture;
        }
    }
}
