package com.javamain.im.groupchat.protocol.request;

import com.javamain.im.groupchat.protocol.Packet;
import lombok.Data;

import static com.javamain.im.groupchat.protocol.command.Command.JOIN_GROUP_REQUEST;

@Data
public class JoinGroupRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {

        return JOIN_GROUP_REQUEST;
    }
}
