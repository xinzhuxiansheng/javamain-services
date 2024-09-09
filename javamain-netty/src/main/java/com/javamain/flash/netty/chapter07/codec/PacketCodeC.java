//package com.javamain.flash.netty.chapter07.codec;
//
//import com.javamain.flash.netty.chapter07.Packet;
//import com.javamain.flash.netty.chapter07.Serializer;
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.ByteBufAllocator;
//
//import java.util.Map;
//
//public class PacketCodeC {
//    private static final int MAGIC_NUMBER = 0x12345678;
//
//    private final Map<Byte, Class<? extends Packet>> packetTypeMap;
//    private final Map<Byte, Serializer> serializerMap;
//
//    public PacketCodeC() {
//    }
//
//    public ByteBuf encode(Packet packet) {
//        // 创建 ByteBuf 对象
//        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
//        // 序列化 Java 对象
//        byte[] bytes = Serializer.DEFAULT.serialize(packet);
//
//        // 实际编码过程
//        byteBuf.writeInt(MAGIC_NUMBER);
//        byteBuf.writeByte(packet.getVersion());
//        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
//        byteBuf.writeByte(packet.getCommand());
//        byteBuf.writeInt(bytes.length);
//        byteBuf.writeBytes(bytes);
//        return byteBuf;
//    }
//
//    public Packet decode(ByteBuf byteBuf) {
//        // 跳过魔数
//        byteBuf.skipBytes(4);
//        // 跳过版本号
//        byteBuf.skipBytes(1);
//        // 序列化算法标识
//        byte serializeAlgorithm = byteBuf.readByte();
//        // 指令
//        byte command = byteBuf.readByte();
//        // 数据包长度
//        int length = byteBuf.readInt();
//        byte[] bytes = new byte[length];
//        byteBuf.readBytes(bytes);
//        Class<? extends Packet> requestType = getRequestType(command);
//        Serializer serializer = getSerializer(serializeAlgorithm);
//        if (requestType != null && serializer != null) {
//            return serializer.deserialize(requestType, bytes);
//        }
//        return null;
//    }
//
//    private Serializer getSerializer(byte serializeAlgorithm) {
//
//        return serializerMap.get(serializeAlgorithm);
//    }
//
//    private Class<? extends Packet> getRequestType(byte command) {
//
//        return packetTypeMap.get(command);
//    }
//}
