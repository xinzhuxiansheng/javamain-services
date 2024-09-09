package com.sms.client;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "msg", propOrder = {"des", "message", "msgtime", "realsnddatetime", "state", "teltype"})
public class Msg {
    protected String des;

    protected String message;

    protected String msgtime;

    protected String realsnddatetime;

    protected String state;

    protected String teltype;

    public String getDes() {
        return this.des;
    }

    public void setDes(String value) {
        this.des = value;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String value) {
        this.message = value;
    }

    public String getMsgtime() {
        return this.msgtime;
    }

    public void setMsgtime(String value) {
        this.msgtime = value;
    }

    public String getRealsnddatetime() {
        return this.realsnddatetime;
    }

    public void setRealsnddatetime(String value) {
        this.realsnddatetime = value;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String value) {
        this.state = value;
    }

    public String getTeltype() {
        return this.teltype;
    }

    public void setTeltype(String value) {
        this.teltype = value;
    }
}

