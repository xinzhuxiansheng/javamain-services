package com.javamain.im.groupchat.protocol.request;

import com.javamain.im.groupchat.protocol.Packet;
import lombok.Data;

import static com.javamain.im.groupchat.protocol.command.Command.QUIT_GROUP_REQUEST;

@Data
public class QuitGroupRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {

        return QUIT_GROUP_REQUEST;
    }
}
