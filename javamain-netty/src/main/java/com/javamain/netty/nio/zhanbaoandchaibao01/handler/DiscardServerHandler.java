package com.javamain.netty.nio.zhanbaoandchaibao01.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.HashSet;
import java.util.Set;

public class DiscardServerHandler  extends ChannelInboundHandlerAdapter {

    static Set<Channel> channelList = new HashSet<>();

//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        //通知其他人 我上线了
//        channelList.forEach(e->{
//            e.writeAndFlush("[客户端]" + ctx.channel().remoteAddress() + "上线了");
//        });
//
//        channelList.add(ctx.channel());
//    }

    //网络调试助手 -> 服务端
    //？直接发送！？
    //网络调试助手 -> 操作系统 -> 网络 -> 对方操作系统 -> 9000找到对应进程（我们的服务端）
    //!字符串
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        String message = (String) msg;
//        Charset gbk = Charset.forName("GBK");
        System.out.println("收到数据：" + message);
//        分发给聊天室内的所有客户端
//        通知其他人 我上线了
        channelList.forEach(e->{
            if(e == ctx.channel()){
                e.writeAndFlush("[自己] ： " + message);
            }else{
                e.writeAndFlush("[客户端] " +ctx.channel().remoteAddress()+"：" + message);
            }
        });
    }

    /**
     * @description: channel 处于不活跃的时候会调用
     * @param
     * @return void
     * @author lld
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //通知其他客户端 我下线了
        channelList.remove(ctx.channel());
        //通知其他人 我上线了
        channelList.forEach(e->{
            e.writeAndFlush("[客户端]" + ctx.channel().remoteAddress() + "下线了");
        });
    }
}
