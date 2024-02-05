package com.sms.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sendmsgReq", propOrder = {"oper", "rwct", "sendmsgs"})
public class SendmsgReq {
    protected String oper;

    protected String rwct;

    protected SendmsgS sendmsgs;

    public String getOper() {
        return this.oper;
    }

    public void setOper(String value) {
        this.oper = value;
    }

    public String getRwct() {
        return this.rwct;
    }

    public void setRwct(String value) {
        this.rwct = value;
    }

    public SendmsgS getSendmsgs() {
        return this.sendmsgs;
    }

    public void setSendmsgs(SendmsgS value) {
        this.sendmsgs = value;
    }
}

