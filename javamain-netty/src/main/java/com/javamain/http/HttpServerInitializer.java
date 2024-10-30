package com.javamain.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {

        // 通过SocketChannel获得对应的管道
        io.netty.channel.ChannelPipeline pipeline = channel.pipeline();

        /**
         * 通过管道，添加handler处理器
         */

        // HttpServerCodec 是由netty自己提供的助手类，此处可以理解为管道中的拦截器
        // 当请求到服务端，我们需要进行做解码，相应到客户端做编码
        pipeline.addLast("HttpServerCodec", new io.netty.handler.codec.http.HttpServerCodec());

        // 添加自定义的助手类，当请求访问，返回“hello netty”
        pipeline.addLast("HttpHandler", new HttpHandler());

    }
}
