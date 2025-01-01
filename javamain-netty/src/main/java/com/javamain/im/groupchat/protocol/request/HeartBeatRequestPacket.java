package com.javamain.im.groupchat.protocol.request;

import com.javamain.im.groupchat.protocol.Packet;

import static com.javamain.im.groupchat.protocol.command.Command.HEARTBEAT_REQUEST;

public class HeartBeatRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return HEARTBEAT_REQUEST;
    }
}