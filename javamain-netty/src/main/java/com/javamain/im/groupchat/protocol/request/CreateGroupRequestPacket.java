package com.javamain.im.groupchat.protocol.request;

import com.javamain.im.groupchat.protocol.Packet;
import lombok.Data;

import java.util.List;

import static com.javamain.im.groupchat.protocol.command.Command.CREATE_GROUP_REQUEST;

@Data
public class CreateGroupRequestPacket extends Packet {

    private List<String> userIdList;

    @Override
    public Byte getCommand() {

        return CREATE_GROUP_REQUEST;
    }
}
