package com.yzhou.rpc.enhance.rpc;

import org.apache.pekko.actor.ActorRef;
import org.apache.pekko.pattern.Patterns;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

public class PekkoInvocationHandler implements InvocationHandler, RpcServer {
    ActorRef targetEndpointActorRef;

    public PekkoInvocationHandler(ActorRef targetEndpointActorRef) {
        this.targetEndpointActorRef = targetEndpointActorRef;
    }

    // 核心功能： 拦截动态代理对象上的方法调用，转成向目标 endpoint 的actor发送 rpcInvoke 消息
    // String res = gateway.queryState();
    // CompletableFuture<String> res = jobMaster.getMasterId();
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> declaringClass = method.getDeclaringClass();
        Object result = null;

        // 如果代理的方法，声明类为如下接口，则说明这些方法应该直接在rpcServer 上执行，不需要去请求远程
        if (Objects.equals(declaringClass, MainThreadExecutable.class)
                || Objects.equals(declaringClass, RpcServer.class)
        ) {
            return method.invoke(this, args);
        }
        // 构造rpc请求消息
        RpcInvoke rpcInvoke = new RpcInvoke(method.getName(), method.getParameterTypes(), args);

        // 判断目标方法是否有返回值
        if (Objects.equals(method.getReturnType(), Void.TYPE)) {
            targetEndpointActorRef.tell(rpcInvoke, ActorRef.noSender());
        } else {
            Future<Object> scalaFuture = Patterns.ask(targetEndpointActorRef, rpcInvoke, 2000);
            // 把scala的future，转成java的 CompletableFuture
            CompletableFuture<Object> completableFuture = new CompletableFuture<>();
            scalaFuture.onComplete(scalaTry -> {
                if (scalaTry.isSuccess()) {
                    completableFuture.complete(scalaTry.get());
                } else {
                    completableFuture.completeExceptionally(scalaTry.failed().get());
                }
                return null;
            }, ExecutionContext.global());

            // 判断 目标方法的返回值类型是否是CompletableFuture，
            // 如果是，则直接返回ask得到future；
            // 否则，返回future.get()得到的完成结果
            if (Objects.equals(method.getReturnType(), CompletableFuture.class)) {
                result = completableFuture;
            } else {
                result = completableFuture.get();
            }
        }
        return result;
    }

    @Override
    public String getAddress() {
        return null;
    }

    // MainThreadExecutable中添加的用于执行内部异步任务的方法
    @Override
    public void runAsync(Runnable runnable) {
        scheduleRunAsync(runnable, 0);
    }

    @Override
    public <V> CompletableFuture<V> callAsync(Callable<V> callable, Duration callTimeout) {
        return null;
    }

    @Override
    public void scheduleRunAsync(Runnable runnable, long delay) {
        // 给本动态代理对象所持有的endpoint的actor发送消息
        targetEndpointActorRef.tell(new RunAsync(runnable, delay), ActorRef.noSender());
    }
}
