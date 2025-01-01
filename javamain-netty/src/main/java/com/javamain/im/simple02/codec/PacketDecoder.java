package com.javamain.im.simple02.codec;

import com.javamain.im.simple02.protocol.Packet;
import com.javamain.im.simple02.protocol.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List out) {
        Packet packet = PacketCodeC.INSTANCE.decode(in);
        if (packet != null) {
            out.add(packet);
        }
    }
}
