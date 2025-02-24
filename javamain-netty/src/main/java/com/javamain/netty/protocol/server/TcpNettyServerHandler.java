package com.javamain.netty.protocol.server;

import com.alibaba.fastjson.JSON;
import com.javamain.netty.protocol.coder.TcpMsg;
import com.javamain.netty.protocol.common.enums.ServerEventCode;
import com.javamain.netty.protocol.common.enums.ServerResponseCode;
import com.javamain.netty.protocol.dto.RegistryReqDTO;
import com.javamain.netty.protocol.dto.RegistryResponseDTO;
import com.javamain.netty.protocol.server.event.model.Event;
import com.javamain.netty.protocol.server.event.model.RegistryEvent;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;

@ChannelHandler.Sharable
public class TcpNettyServerHandler extends SimpleChannelInboundHandler {

    //1.网络请求的接收(netty完成)
    //2.事件发布器的实现（EventBus-》event）Spring的事件，Google Guaua
    //3.事件处理器的实现（Listener-》处理event）
    //4.数据存储（基于Map本地内存的方式存储）
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        TcpMsg tcpMsg = (TcpMsg) msg;
        int code = tcpMsg.getCode();
        byte[] body = tcpMsg.getBody();
        Event event = null;
        if (ServerEventCode.REGISTRY.getCode() == code) {
            // event = JSON.parseObject(body, RegistryEvent.class);
            RegistryReqDTO registryDTO = JSON.parseObject(body, RegistryReqDTO.class);
            event = new RegistryEvent();
            System.out.println("解析 event");
            event.setChannelHandlerContext(channelHandlerContext);

            registryResp(event,registryDTO);
        }
//        else if (NameServerEventCode.UN_REGISTRY.getCode() == code) {
//            event = JSON.parseObject(body, UnRegistryEvent.class);
//        } else if (NameServerEventCode.HEART_BEAT.getCode() == code) {
//            event = JSON.parseObject(body, HeartBeatEvent.class);
//        }
        //...

    }

    private void registryResp(Event event, RegistryReqDTO registryDTO) {
        RegistryEvent registryEvent = null;
        if (event instanceof RegistryEvent) {
            registryEvent = (RegistryEvent) event;
        } else {
            return;
        }

        //安全认证
        String rightUser = "yzhou";
        String rightPassword = "password";
        ChannelHandlerContext channelHandlerContext = event.getChannelHandlerContext();
//        if (!rightUser.equals(event.getUser()) || !rightPassword.equals(event.getPassword())) {
//            TcpMsg tcpMsg = new TcpMsg(NameServerResponseCode.ERROR_USER_OR_PASSWORD.getCode(),
//                    NameServerResponseCode.ERROR_USER_OR_PASSWORD.getDesc().getBytes());
//            channelHandlerContext.writeAndFlush(tcpMsg);
//            channelHandlerContext.close();
//            throw new IllegalAccessException("error account to connected!");
//        }
        System.out.println("注册事件接收:" + event);
        channelHandlerContext.attr(AttributeKey.valueOf("reqId")).set(registryEvent.getBrokerIp() + ":" + registryEvent.getBrokerPort());

        RegistryResponseDTO sendMessageToBrokerResponseDTO = new RegistryResponseDTO();
        sendMessageToBrokerResponseDTO.setStatus(ServerResponseCode.REGISTRY_SUCCESS.getCode());
        sendMessageToBrokerResponseDTO.setMsgId(registryDTO.getMsgId());

        TcpMsg tcpMsg = new TcpMsg(ServerResponseCode.REGISTRY_SUCCESS.getCode(),
                JSON.toJSONBytes(sendMessageToBrokerResponseDTO));
        channelHandlerContext.writeAndFlush(tcpMsg);
    }
}
