package com.javamain.im.groupchat.protocol.request;

import com.javamain.im.groupchat.protocol.Packet;
import lombok.Data;

import static com.javamain.im.groupchat.protocol.command.Command.LIST_GROUP_MEMBERS_REQUEST;

@Data
public class ListGroupMembersRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {

        return LIST_GROUP_MEMBERS_REQUEST;
    }
}
