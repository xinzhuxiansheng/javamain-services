package com.yzhou.rpc.base.systemb;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.yzhou.rpc.base.common.RpcRequest;
import com.yzhou.rpc.base.common.RpcResponse;
import com.yzhou.rpc.base.common.UserFinder;
import org.apache.pekko.actor.AbstractActor;
import org.apache.pekko.actor.ActorSystem;
import org.apache.pekko.actor.Props;
import org.apache.pekko.japi.pf.ReceiveBuilder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class SystemBEntry {
    public static void main(String[] args) {
        Config config = ConfigFactory.load();
        ActorSystem actorSystem = ActorSystem.create("system-b", config);
        HashMap<String,Object> services = new HashMap<>();
        services.put(UserFinder.class.getName(),new UserFinderImpl());
        actorSystem.actorOf(Props.create(RpcInvokeActor.class,services),"system-b-actor");
    }
}

class RpcInvokeActor extends AbstractActor {
    HashMap<String,Object> services ;

    public RpcInvokeActor(HashMap<String, Object> services) {
        this.services = services;
    }

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(RpcRequest.class,this::handleRpcRequest)
                .build();
    }

    public void handleRpcRequest(RpcRequest rpcRequest) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        String modelInterfaceName = rpcRequest.getModelInterfaceName();
        String methodName = rpcRequest.getMethodName();
        Class<?>[] parameterTypes = rpcRequest.getParameterTypes();
        Object[] parameters = rpcRequest.getParameters();
        Object service = services.get(modelInterfaceName);
        Method method = service.getClass().getMethod(methodName, parameterTypes);
        Object result = method.invoke(service, parameters);
        getSender().tell(new RpcResponse(result,null,0),getSelf());
    }
}

