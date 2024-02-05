package com.sms.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "searchsmsReqMsg", propOrder = {"msghead", "msgreq"})
public class SearchsmsReqMsg {
    protected Head msghead;

    protected SearchsmsReq msgreq;

    public Head getMsghead() {
        return this.msghead;
    }

    public void setMsghead(Head value) {
        this.msghead = value;
    }

    public SearchsmsReq getMsgreq() {
        return this.msgreq;
    }

    public void setMsgreq(SearchsmsReq value) {
        this.msgreq = value;
    }
}