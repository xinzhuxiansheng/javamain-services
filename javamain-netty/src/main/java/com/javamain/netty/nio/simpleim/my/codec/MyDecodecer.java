package com.javamain.netty.nio.simpleim.my.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @description:
 * @author: lld
 * @version: 1.0
 */
public class MyDecodecer extends ByteToMessageDecoder {


    //数据长度 + 数据
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if(in.readableBytes() < 4){
            return;
        }
        //数据长度 4 + 10000  9999
        int i = in.readInt();
        if(in.readableBytes() < i){
            in.resetReaderIndex();
            return;
        }
        byte[] data = new byte[i];//10000
        in.readBytes(data);
        System.out.println(new String(data));
        in.markReaderIndex();//10004
    }
}
