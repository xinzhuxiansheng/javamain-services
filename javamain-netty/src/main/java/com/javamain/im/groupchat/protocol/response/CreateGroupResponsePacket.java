package com.javamain.im.groupchat.protocol.response;

import com.javamain.im.groupchat.protocol.Packet;
import lombok.Data;

import java.util.List;

import static com.javamain.im.groupchat.protocol.command.Command.CREATE_GROUP_RESPONSE;

@Data
public class CreateGroupResponsePacket extends Packet {
    private boolean success;

    private String groupId;

    private List<String> userNameList;

    @Override
    public Byte getCommand() {

        return CREATE_GROUP_RESPONSE;
    }
}
