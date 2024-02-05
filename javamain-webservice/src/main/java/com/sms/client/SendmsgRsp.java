package com.sms.client;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sendmsgRsp", propOrder = {"retcode", "retmsg"})
public class SendmsgRsp {
    protected String retcode;

    protected String retmsg;

    public String getRetcode() {
        return this.retcode;
    }

    public void setRetcode(String value) {
        this.retcode = value;
    }

    public String getRetmsg() {
        return this.retmsg;
    }

    public void setRetmsg(String value) {
        this.retmsg = value;
    }
}