package com.javamain.netty.nio.simpleim.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class NettyByteBuf {
    public static void main(String[] args) {
        // 创建byteBuf对象，该对象内部包含一个字节数组byte[10]
        ByteBuf byteBuf = Unpooled.buffer(10);
        System.out.println("byteBuf=" + byteBuf);

        for (int i = 0; i < 8; i++) {
            byteBuf.writeByte(i);
        }
        System.out.println("byteBuf=" + byteBuf);

        for (int i = 0; i < 5; i++) {
            System.out.println(byteBuf.getByte(i));
        }
        System.out.println("byteBuf=" + byteBuf);

        for (int i = 0; i < 5; i++) {
            System.out.println(byteBuf.readByte());
        }
        System.out.println("byteBuf=" + byteBuf);


    }
}
