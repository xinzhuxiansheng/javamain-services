package com.javamain.im.groupchat.protocol.request;

import com.javamain.im.groupchat.protocol.Packet;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.javamain.im.groupchat.protocol.command.Command.GROUP_MESSAGE_REQUEST;

@Data
@NoArgsConstructor
public class GroupMessageRequestPacket extends Packet {
    private String toGroupId;
    private String message;

    public GroupMessageRequestPacket(String toGroupId, String message) {
        this.toGroupId = toGroupId;
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return GROUP_MESSAGE_REQUEST;
    }
}
