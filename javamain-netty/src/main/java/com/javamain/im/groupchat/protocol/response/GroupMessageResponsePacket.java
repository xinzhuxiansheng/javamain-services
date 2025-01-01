package com.javamain.im.groupchat.protocol.response;

import com.javamain.im.groupchat.protocol.Packet;
import com.javamain.im.groupchat.session.Session;
import lombok.Data;

import static com.javamain.im.groupchat.protocol.command.Command.GROUP_MESSAGE_RESPONSE;

@Data
public class GroupMessageResponsePacket extends Packet {

    private String fromGroupId;

    private Session fromUser;

    private String message;

    @Override
    public Byte getCommand() {

        return GROUP_MESSAGE_RESPONSE;
    }
}
