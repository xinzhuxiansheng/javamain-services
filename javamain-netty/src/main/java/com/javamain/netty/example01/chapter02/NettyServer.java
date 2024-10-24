package com.javamain.netty.example01.chapter02;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

public class NettyServer {
    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        serverBootstrap
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) {
                        extracted(ch);
                    }
                }).bind(8000).addListener(future -> {
                    if (future.isSuccess()) {
                        System.out.println("端口绑定成功!");
                    } else {
                        System.err.println("端口绑定失败!");
                    }
                });
    }

    private static void extracted(NioSocketChannel ch) {
        ch.pipeline().addLast(new StringDecoder());
        ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
            @Override
            protected void channelRead0(ChannelHandlerContext ctx, String msg) {
                System.out.println(msg);
            }
        });
        // 新开一个线程
        new Thread(() -> {
            try {
                Thread.sleep(1000);

                System.out.println("send back");
                ch.writeAndFlush("hello back1");
                ch.writeAndFlush("hello back2");
                ch.writeAndFlush("hello back3");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
