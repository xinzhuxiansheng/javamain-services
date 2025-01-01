package com.javamain.im.groupchat.protocol.response;


import com.javamain.im.groupchat.protocol.Packet;
import com.javamain.im.groupchat.session.Session;
import lombok.Data;

import java.util.List;

import static com.javamain.im.groupchat.protocol.command.Command.LIST_GROUP_MEMBERS_RESPONSE;

@Data
public class ListGroupMembersResponsePacket extends Packet {

    private String groupId;

    private List<Session> sessionList;

    @Override
    public Byte getCommand() {

        return LIST_GROUP_MEMBERS_RESPONSE;
    }
}
