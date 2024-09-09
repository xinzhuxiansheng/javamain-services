package com.sms.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sendmsg", propOrder = {"accno", "custno", "lastsndtime", "msg", "telno", "type"})
public class Sendmsg {
    protected int accno;

    protected String custno;

    protected String lastsndtime;

    protected String msg;

    protected String telno;

    protected String type;

    public int getAccno() {
        return this.accno;
    }

    public void setAccno(int value) {
        this.accno = value;
    }

    public String getCustno() {
        return this.custno;
    }

    public void setCustno(String value) {
        this.custno = value;
    }

    public String getLastsndtime() {
        return this.lastsndtime;
    }

    public void setLastsndtime(String value) {
        this.lastsndtime = value;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String value) {
        this.msg = value;
    }

    public String getTelno() {
        return this.telno;
    }

    public void setTelno(String value) {
        this.telno = value;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String value) {
        this.type = value;
    }
}

