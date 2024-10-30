package com.javamain.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.itzixi.enums.MsgTypeEnum;
import org.itzixi.pojo.netty.ChatMsg;
import org.itzixi.pojo.netty.DataContent;
import org.itzixi.utils.JsonUtils;
import org.itzixi.utils.LocalDateUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 创建心跳助手类
 * @Auther 风间影月
 */
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // 判断evt是否是IdleStateEvent(空闲事件状态，包含 读空闲/写空闲/读写空闲)
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent)evt;
            if (event.state() == IdleState.READER_IDLE) {
                // System.out.println("进入读空闲...");
            } else if (event.state() == IdleState.WRITER_IDLE) {
                // System.out.println("进入写空闲...");
            } else if (event.state() == IdleState.ALL_IDLE) {
                // System.out.println("chennel 关闭前，clients的数量为：" + ChatHandler.clients.size());
                Channel channel = ctx.channel();
                // 关闭无用的channel，以防资源浪费
                channel.close();
                // System.out.println("chennel 关闭后，clients的数量为：" + ChatHandler.clients.size());
            }
        }
    }
}
