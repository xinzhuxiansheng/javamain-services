package com.javamain.im.groupchat.protocol.response;

import com.javamain.im.groupchat.protocol.Packet;

import static com.javamain.im.groupchat.protocol.command.Command.HEARTBEAT_RESPONSE;

public class HeartBeatResponsePacket extends Packet {
    @Override
    public Byte getCommand() {
        return HEARTBEAT_RESPONSE;
    }
}
