package com.javamain.netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

class ClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // 发送消息
        String message = "Hello, Server!";
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeInt(message.getBytes(StandardCharsets.UTF_8).length); // 先写入长度
        buffer.writeBytes(message.getBytes(StandardCharsets.UTF_8)); // 写入实际数据
        ctx.writeAndFlush(buffer);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) {
        // 读取服务器响应
        String received = msg.toString(StandardCharsets.UTF_8);
        System.out.println("Client received: " + received);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
