package com.yzhou.rpc.base.systema;

import com.yzhou.rpc.base.common.RpcRequest;
import com.yzhou.rpc.base.common.RpcResponse;
import org.apache.pekko.actor.ActorRef;
import org.apache.pekko.pattern.Patterns;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class SystemAInvocationHandler implements InvocationHandler {
    ActorRef targetActorRef;

    public SystemAInvocationHandler(ActorRef targetActorRef) {
        this.targetActorRef = targetActorRef;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String interfaceName = method.getDeclaringClass().getName();
        String methodName = method.getName();
        Class<?>[] parameterTypes = method.getParameterTypes();

        RpcRequest rpcRequest = new RpcRequest(interfaceName, methodName, parameterTypes, args);
        Future<Object> ask = Patterns.ask(targetActorRef, rpcRequest, 2000);
        RpcResponse rpcResponse = (RpcResponse) Await.result(ask, Duration.apply("2s"));
        return rpcResponse.getResult();
    }
}
