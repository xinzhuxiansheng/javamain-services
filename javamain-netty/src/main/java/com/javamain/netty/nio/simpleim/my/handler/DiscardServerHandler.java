package com.javamain.netty.nio.simpleim.my.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.HashSet;
import java.util.Set;

/**
 * @description:
 * @author: lld
 * @version: 1.0
 */
public class DiscardServerHandler  extends ChannelInboundHandlerAdapter {

    static Set<Channel> channelList = new HashSet<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    }

    //网络调试助手 -> 服务端
    //？直接发送！？
    //网络调试助手 -> 操作系统 -> 网络 -> 对方操作系统 -> 9000找到对应进程（我们的服务端）
    //!字符串
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {



    }

    /**
     * @description: channel 处于不活跃的时候会调用
     * @param
     * @return void
     * @author lld
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    }
}
