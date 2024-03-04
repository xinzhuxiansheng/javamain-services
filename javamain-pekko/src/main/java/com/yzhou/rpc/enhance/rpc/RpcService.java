package com.yzhou.rpc.enhance.rpc;

import org.apache.pekko.actor.*;

import java.lang.reflect.Proxy;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RpcService {
    ActorSystem actorSystem;
    HashMap<String, ActorRef> actors = new HashMap<>();

    public RpcService(ActorSystem actorSystem) {
        this.actorSystem = actorSystem;
    }

    // 获取远程actor的引用，并构造一个远程endpoint的gateway动态代理对象
    public <G extends RpcGateway> G connect(String address, Class<G> gatewayClass) throws ExecutionException, InterruptedException {
        // 要去lookup到远程actor
        ActorSelection actorSelection = actorSystem.actorSelection(address);
        CompletableFuture<ActorRef> completableFuture = actorSelection.resolveOne(Duration.ofSeconds(1)).toCompletableFuture();
        ActorRef remoteTargetActorRef = completableFuture.get();

        PekkoInvocationHandler h = new PekkoInvocationHandler(remoteTargetActorRef);
        @SuppressWarnings("unchecked")
        G gateway = (G) Proxy.newProxyInstance(RpcService.class.getClassLoader(), new Class[]{gatewayClass}, h);

        return gateway;
    }

    /**
     * 根据传入的功能实体 endpoint 对象，创建对应的 actor
     * 并创建出功能实体类 endpoint 的动态代理对象
     */
    public <C extends RpcEndpoint & RpcGateway> RpcServer startServer(C rpcEndpoint) {
        ActorRef endpointSelfActor = actorSystem.actorOf(Props.create(PekkoRpcActor.class, rpcEndpoint),
                rpcEndpoint.getEndpointId());

        /*
            为了后续的管理方便，可以把这个创建好的endpoint对应的actor放入一个集合来持有者
            所谓的管理，是指对 Actor的生命周期 进行管理 。
         */
        actors.put(rpcEndpoint.getEndpointId(), endpointSelfActor);

        /*
            动态代理对象
         */

        // 抽取endpoint对象类上实现的接口
        Class<?>[] interfaces = rpcEndpoint.getClass().getInterfaces();
        // 手动添加一些必要的接口
        HashSet<Class<?>> classes = new HashSet<>(Arrays.asList(interfaces));
        classes.add(RpcServer.class);
        Class<?>[] array = classes.toArray(new Class<?>[0]);

        // 生成一个自身 endpoint 的动态代理对象
        PekkoInvocationHandler h = new PekkoInvocationHandler(endpointSelfActor);
        @SuppressWarnings("")
        RpcServer rpcServer = (RpcServer) Proxy.newProxyInstance(RpcService.class.getClassLoader(), array, h);
        return rpcServer;

    }

    public String getAddress(String endpointId) {
        ActorRef actorRef = actors.get(endpointId);
        Address address = actorSystem.provider().getDefaultAddress();
        return actorRef.path().toStringWithAddress(address);
    }
}
