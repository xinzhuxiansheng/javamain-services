//package com.javamain.nettyhttp.restapi_v1;
//
//import io.netty.bootstrap.ServerBootstrap;
//import io.netty.channel.ChannelOption;
//import io.netty.channel.EventLoopGroup;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.nio.NioServerSocketChannel;
//import io.netty.handler.logging.LogLevel;
//import io.netty.handler.logging.LoggingHandler;
//
//public class BootstrapServer {
//
//    /**
//     * Server 启动入口
//     * @param args
//     */
//    public static void main(String[] args) {
//
//        //主从Reactor多线程模型
//        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
//        EventLoopGroup workerGroup = new NioEventLoopGroup();
//        try{
//            ServerBootstrap b = new ServerBootstrap();
//            b.option(ChannelOption.SO_BACKLOG,1024);
//            b.group(bossGroup,workerGroup)
//                    .channel(NioServerSocketChannel.class)
//                    .handler(new LoggingHandler(LogLevel.INFO))
//                    .childHandler(n)
//
//        }catch(InterruptedException e){
//            e.printStackTrace();
//        }finally {
//            bossGroup.shutdownGracefully();
//            workerGroup.shutdownGracefully();
//        }
//    }
//}
