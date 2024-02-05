package com.sms.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sendmsgRspMsg", propOrder = {"msghead", "msgrsp"})
public class SendmsgRspMsg {
    protected Head msghead;

    protected SendmsgRsp msgrsp;

    public Head getMsghead() {
        return this.msghead;
    }

    public void setMsghead(Head value) {
        this.msghead = value;
    }

    public SendmsgRsp getMsgrsp() {
        return this.msgrsp;
    }

    public void setMsgrsp(SendmsgRsp value) {
        this.msgrsp = value;
    }
}

