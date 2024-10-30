package com.javamain;

import com.javamain.http.HttpServerInitializer;

public class ChatServer {
    public static void main(String[] args) throws Exception {

        // 定义主从线程组
        // 定义主线程池，用于接受客户端的连接，但是不做任何处理，比如老板会谈业务，拉到业务就会交给下面的员工去做了
        io.netty.channel.EventLoopGroup bossGroup = new io.netty.channel.nio.NioEventLoopGroup();
        // 定义从线程池，处理主线程池交过来的任务，公司业务员开展业务，完成老板交代的任务
        io.netty.channel.EventLoopGroup workerGroup = new io.netty.channel.nio.NioEventLoopGroup();

        try {
            // 构建Netty服务器
            io.netty.bootstrap.ServerBootstrap server = new io.netty.bootstrap.ServerBootstrap();     // 服务的启动类
            server.group(bossGroup, workerGroup)                // 把主从线程池组放入到启动类中
                    .channel(io.netty.channel.socket.nio.NioServerSocketChannel.class)      // 设置Nio的双向通道
                    .childHandler(new HttpServerInitializer());   // 设置处理器，用于处理workerGroup

            // 启动server，并且绑定端口号875，同时启动方式为"同步"
            io.netty.channel.ChannelFuture channelFuture = server.bind(875).sync();
            // 请求：http://127.0.0.1:875

            // 监听关闭的channel
            channelFuture.channel().closeFuture().sync();
        } finally {
            // 优雅的关闭线程池组
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
