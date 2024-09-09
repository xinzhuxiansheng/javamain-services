package com.yzhou.rpc.base.systema;

import com.yzhou.rpc.base.common.RpcResponse;
import org.apache.pekko.actor.AbstractActor;
import org.apache.pekko.japi.pf.ReceiveBuilder;

public class SystemA_Actor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(RpcResponse.class,this::handleRpcResponse)
                .build();
    }

    public void handleRpcResponse(RpcResponse rpcResponse){

    }
}
