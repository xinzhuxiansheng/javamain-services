package com.sms.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "searchsmsRspMsg", propOrder = {"msghead", "msgrsp"})
public class SearchsmsRspMsg {
    protected Head msghead;

    protected SearchsmsRsp msgrsp;

    public Head getMsghead() {
        return this.msghead;
    }

    public void setMsghead(Head value) {
        this.msghead = value;
    }

    public SearchsmsRsp getMsgrsp() {
        return this.msgrsp;
    }

    public void setMsgrsp(SearchsmsRsp value) {
        this.msgrsp = value;
    }
}
