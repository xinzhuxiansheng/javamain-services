package com.javamain.im.singlechat.codec;

import com.javamain.im.singlechat.protocol.Packet;
import com.javamain.im.singlechat.protocol.PacketCodeC;
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
