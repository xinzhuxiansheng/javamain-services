package com.javamain.im.simple02.protocol;

import com.javamain.im.simple02.protocol.request.LoginRequestPacket;
import com.javamain.im.simple02.protocol.request.MessageRequestPacket;
import com.javamain.im.simple02.protocol.response.LoginResponsePacket;
import com.javamain.im.simple02.protocol.response.MessageResponsePacket;
import com.javamain.im.simple02.serialize.Serializer;
import com.javamain.im.simple02.serialize.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

import static com.javamain.im.simple01.protocol.command.Command.LOGIN_REQUEST;
import static com.javamain.im.simple01.protocol.command.Command.LOGIN_RESPONSE;
import static com.javamain.im.simple02.protocol.command.Command.MESSAGE_REQUEST;
import static com.javamain.im.simple02.protocol.command.Command.MESSAGE_RESPONSE;

public class PacketCodeC {

    private static final int MAGIC_NUMBER = 0x12345678;
    public static final PacketCodeC INSTANCE = new PacketCodeC();

    private final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private final Map<Byte, Serializer> serializerMap;


    private PacketCodeC() {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(MESSAGE_RESPONSE, MessageResponsePacket.class);

        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlogrithm(), serializer);
    }

    public void encode(ByteBuf byteBuf, Packet packet) {
        // 1. 序列化 java 对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // 2. 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlogrithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }


    public Packet decode(ByteBuf byteBuf) {
        // bytebuf 可读长度
        int readableBytesBufLen = byteBuf.readableBytes();
        if (readableBytesBufLen < 11) {
            return null;
        }

        int readerIndex = byteBuf.readerIndex();

        // 跳过 magic number
        byteBuf.skipBytes(4);

        // 跳过版本号
        byteBuf.skipBytes(1);

        // 序列化算法
        byte serializeAlgorithm = byteBuf.readByte();

        // 指令
        byte command = byteBuf.readByte();

        // 数据包长度
        int length = byteBuf.readInt();

        // 如何剩余的长度不够读 length，仍然需要回到起点，并返回 null。
        if (byteBuf.readableBytes() < length) {
            byteBuf.readerIndex(readerIndex); // 重置 readerIndex
            return null;
        }

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }

        byteBuf.readerIndex(readerIndex);
        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {

        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {

        return packetTypeMap.get(command);
    }
}