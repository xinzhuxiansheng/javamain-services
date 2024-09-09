package com.sms.client;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "searchsmsReq", propOrder = {"bgndate", "enddate", "indexpage", "oper", "pagesize", "telno"})
public class SearchsmsReq {
    protected String bgndate;

    protected String enddate;

    protected String indexpage;

    protected String oper;

    protected String pagesize;

    protected String telno;

    public String getBgndate() {
        return this.bgndate;
    }

    public void setBgndate(String value) {
        this.bgndate = value;
    }

    public String getEnddate() {
        return this.enddate;
    }

    public void setEnddate(String value) {
        this.enddate = value;
    }

    public String getIndexpage() {
        return this.indexpage;
    }

    public void setIndexpage(String value) {
        this.indexpage = value;
    }

    public String getOper() {
        return this.oper;
    }

    public void setOper(String value) {
        this.oper = value;
    }

    public String getPagesize() {
        return this.pagesize;
    }

    public void setPagesize(String value) {
        this.pagesize = value;
    }

    public String getTelno() {
        return this.telno;
    }

    public void setTelno(String value) {
        this.telno = value;
    }
}
