package com.sms.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "head", propOrder = {"authcode", "devno", "reqsn", "servicename", "tranchannel", "trandatetime", "version"})
public class Head {
    protected String authcode;

    protected String devno;

    protected String reqsn;

    protected String servicename;

    protected String tranchannel;

    protected String trandatetime;

    protected String version;

    public String getAuthcode() {
        return this.authcode;
    }

    public void setAuthcode(String value) {
        this.authcode = value;
    }

    public String getDevno() {
        return this.devno;
    }

    public void setDevno(String value) {
        this.devno = value;
    }

    public String getReqsn() {
        return this.reqsn;
    }

    public void setReqsn(String value) {
        this.reqsn = value;
    }

    public String getServicename() {
        return this.servicename;
    }

    public void setServicename(String value) {
        this.servicename = value;
    }

    public String getTranchannel() {
        return this.tranchannel;
    }

    public void setTranchannel(String value) {
        this.tranchannel = value;
    }

    public String getTrandatetime() {
        return this.trandatetime;
    }

    public void setTrandatetime(String value) {
        this.trandatetime = value;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String value) {
        this.version = value;
    }
}


