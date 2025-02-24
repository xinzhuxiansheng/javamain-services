package com.javamain.netty.protocol.client;

import com.alibaba.fastjson.JSON;
import com.javamain.netty.protocol.coder.TcpMsg;
import com.javamain.netty.protocol.common.ServerSyncFutureManager;
import com.javamain.netty.protocol.common.SyncFuture;
import com.javamain.netty.protocol.common.enums.ServerResponseCode;
import com.javamain.netty.protocol.dto.RegistryResponseDTO;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class ServerRespChannelHandler extends SimpleChannelInboundHandler {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {

        System.out.println("Handler 当前线程名称: " + Thread.currentThread().getName());

        TcpMsg tcpMsg = (TcpMsg) msg;
        System.out.println("resp:" + JSON.toJSONString(tcpMsg));
        if (ServerResponseCode.REGISTRY_SUCCESS.getCode() == tcpMsg.getCode()) {
            RegistryResponseDTO registryResponseDTO = com.alibaba.fastjson2.JSON.parseObject(tcpMsg.getBody(), RegistryResponseDTO.class);
            SyncFuture syncFuture = ServerSyncFutureManager.get(registryResponseDTO.getMsgId());
            if (syncFuture != null) {
                syncFuture.setResponse(tcpMsg);
            }
        }
    }
}
