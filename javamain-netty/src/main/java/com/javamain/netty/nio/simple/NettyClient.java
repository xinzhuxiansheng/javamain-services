package com.javamain.netty.nio.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author yzhou
 * @date 2023/2/14
 */
public class NettyClient {

    private static int MAX_RETRY = 5;

    public static void main(String[] args) {

        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {

                    }
                });
    }

    public static void connect(Bootstrap bootstrap, String host, Integer port, Integer retry) {
        bootstrap.connect(host, port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("链接成功!");
                } else if (retry == 0) {
                    System.out.println("重试次数已用完，放弃链接!");
                } else {
                    int order = (MAX_RETRY - retry) + 1;
                    int delay = 1 << order;
                    System.out.println(String.format("%s:连接失败，第%s次重连...", new Date(), order));
                    bootstrap.config().group().schedule(new Runnable() {
                        @Override
                        public void run() {
                            connect(bootstrap, host, port, retry - 1);
                        }
                    }, delay, TimeUnit.SECONDS);
                }
            }
        });
    }
}
