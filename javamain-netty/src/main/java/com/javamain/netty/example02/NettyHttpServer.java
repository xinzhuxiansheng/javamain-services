package com.javamain.netty.example02;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.json.JsonObjectDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class NettyHttpServer {

    private static final int PORT = 8080;

    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new HttpServerCodec());
                            p.addLast(new HttpObjectAggregator(65536));
                            p.addLast(new JsonObjectDecoder());
                            p.addLast(new StringEncoder(CharsetUtil.UTF_8));
                            p.addLast(new HttpServerHandler());
                        }
                    });

            ChannelFuture f = b.bind(PORT).sync();
            System.out.println("Server started, listening on " + PORT);
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    static class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

        static {
            HashMap map = new HashMap();
            map.put("id",1);
            map.put("name","yzhou");
            map.put("address","China");
        }

        private static final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
            if (request.uri().equals("/netty/api/getUserInfo") && request.method().name().equals("POST")) {
                String json = request.content().toString(StandardCharsets.UTF_8);
                Map<String, Object> data = objectMapper.readValue(json, HashMap.class);

                int userId = (int) data.get("id");
                Map<String, Object> response = getUserInfo(userId);

                FullHttpResponse httpResponse = createHttpResponse(objectMapper.writeValueAsString(response));
                ctx.writeAndFlush(httpResponse);
            } else {
                ctx.writeAndFlush(createHttpResponse("404 Not Found", HttpResponseStatus.NOT_FOUND));
            }
        }

        private Map<String, Object> getUserInfo(int userId) {
            // 模拟从数据库或其他服务获取用户信息
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("id", userId);
            userInfo.put("name", "User" + userId);
            userInfo.put("email", "user" + userId + "@example.com");
            return userInfo;
        }

        private FullHttpResponse createHttpResponse(String content) {
            return createHttpResponse(content, HttpResponseStatus.OK);
        }

        private FullHttpResponse createHttpResponse(String content, HttpResponseStatus status) {
            FullHttpResponse response = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1, status,
                    Unpooled.copiedBuffer(content, CharsetUtil.UTF_8)
            );
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json; charset=UTF-8");
            response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
            return response;
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }
    }
}
