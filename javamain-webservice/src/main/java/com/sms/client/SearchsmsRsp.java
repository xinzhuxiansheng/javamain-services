package com.sms.client;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "searchsmsRsp", propOrder = {"msgrec", "msgs", "retcode", "retmsg", "telno", "totalpage", "totalrec"})
public class SearchsmsRsp {
    protected int msgrec;

    protected MsgS msgs;

    protected String retcode;

    protected String retmsg;

    protected String telno;

    protected int totalpage;

    protected int totalrec;

    public int getMsgrec() {
        return this.msgrec;
    }

    public void setMsgrec(int value) {
        this.msgrec = value;
    }

    public MsgS getMsgs() {
        return this.msgs;
    }

    public void setMsgs(MsgS value) {
        this.msgs = value;
    }

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

    public String getTelno() {
        return this.telno;
    }

    public void setTelno(String value) {
        this.telno = value;
    }

    public int getTotalpage() {
        return this.totalpage;
    }

    public void setTotalpage(int value) {
        this.totalpage = value;
    }

    public int getTotalrec() {
        return this.totalrec;
    }

    public void setTotalrec(int value) {
        this.totalrec = value;
    }
}

