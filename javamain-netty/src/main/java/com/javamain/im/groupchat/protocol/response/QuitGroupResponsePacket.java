package com.javamain.im.groupchat.protocol.response;

import com.javamain.im.groupchat.protocol.Packet;
import lombok.Data;

import static com.javamain.im.groupchat.protocol.command.Command.QUIT_GROUP_RESPONSE;

@Data
public class QuitGroupResponsePacket extends Packet {

    private String groupId;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {

        return QUIT_GROUP_RESPONSE;
    }
}
