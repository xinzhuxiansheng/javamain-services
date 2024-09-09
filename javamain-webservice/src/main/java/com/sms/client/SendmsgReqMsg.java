package com.sms.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sendmsgReqMsg", propOrder = {"msghead", "msgreq"})
public class SendmsgReqMsg {
    protected Head msghead;

    protected SendmsgReq msgreq;

    public Head getMsghead() {
        return this.msghead;
    }

    public void setMsghead(Head value) {
        this.msghead = value;
    }

    public SendmsgReq getMsgreq() {
        return this.msgreq;
    }

    public void setMsgreq(SendmsgReq value) {
        this.msgreq = value;
    }
}


