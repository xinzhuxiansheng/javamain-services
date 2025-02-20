package com.javamain.netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

class ServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) {
        // 读取客户端发送的消息
        String received = msg.toString(StandardCharsets.UTF_8);
        System.out.println("Server received: " + received);

        // 响应客户端
        String response = "Hello, Client!";
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeInt(response.getBytes(StandardCharsets.UTF_8).length); // 先写入长度
        buffer.writeBytes(response.getBytes(StandardCharsets.UTF_8)); // 写入实际数据
        ctx.writeAndFlush(buffer);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
