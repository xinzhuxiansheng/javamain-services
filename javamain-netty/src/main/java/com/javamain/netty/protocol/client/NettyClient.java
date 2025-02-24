package com.javamain.netty.protocol.client;

import com.alibaba.fastjson.JSON;
import com.javamain.netty.protocol.coder.TcpMsg;
import com.javamain.netty.protocol.coder.TcpMsgDecoder;
import com.javamain.netty.protocol.coder.TcpMsgEncoder;
import com.javamain.netty.protocol.common.ServerSyncFutureManager;
import com.javamain.netty.protocol.common.SyncFuture;
import com.javamain.netty.protocol.common.enums.ServerEventCode;
import com.javamain.netty.protocol.dto.RegistryReqDTO;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.internal.StringUtil;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.UUID;

public class NettyClient {

    private EventLoopGroup clientGroup = new NioEventLoopGroup();
    private Bootstrap bootstrap = new Bootstrap();
    private Channel channel;
    // private String DEFAULT_NAMESERVER_IP = "127.0.0.1";

    /**
     * 初始化链接
     */
    public void initConnection() {
        String ip = "127.0.0.1";
        Integer port = 9090;
        if (StringUtil.isNullOrEmpty(ip) || port == null || port < 0) {
            throw new RuntimeException("error port or ip");
        }
        bootstrap.group(clientGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new TcpMsgDecoder());
                ch.pipeline().addLast(new TcpMsgEncoder());
                ch.pipeline().addLast(new ServerRespChannelHandler());
            }
        });
        ChannelFuture channelFuture = null;
        try {
            channelFuture = bootstrap.connect(ip, port).sync();
            channel = channelFuture.channel();
            System.out.println("success connected to nameserver!");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Channel getChannel() {
        if (channel == null) {
            throw new RuntimeException("channel has not been connected!");
        }
        return channel;
    }


    public void doRegistry() {
        RegistryReqDTO registryDTO = new RegistryReqDTO();
        try {
            System.out.println("NettyClient 当前线程名称: " + Thread.currentThread().getName());
            String msgId = UUID.randomUUID().toString();
            registryDTO.setBrokerIp(Inet4Address.getLocalHost().getHostAddress());
            registryDTO.setBrokerPort(5050);
            registryDTO.setUser("yzhou");
            registryDTO.setPassword("password");
            registryDTO.setMsgId(msgId);
            TcpMsg tcpMsg = new TcpMsg(ServerEventCode.REGISTRY.getCode(), JSON.toJSONBytes(registryDTO));

            TcpMsg respMsg = sendSyncMsg(tcpMsg, msgId);

            System.out.println("redistry resp: " + new String(respMsg.getBody()));
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public TcpMsg sendSyncMsg(TcpMsg tcpMsg, String msgId) {
        channel.writeAndFlush(tcpMsg);
        SyncFuture syncFuture = new SyncFuture();
        syncFuture.setMsgId(msgId);
        ServerSyncFutureManager.put(msgId, syncFuture);
        try {
            return (TcpMsg) syncFuture.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
