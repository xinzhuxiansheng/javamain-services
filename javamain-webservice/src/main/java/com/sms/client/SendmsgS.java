package com.sms.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sendmsgS", propOrder = {"sendmsg"})
public class SendmsgS {
    @XmlElement(nillable = true)
    protected List<Sendmsg> sendmsg;

    public List<Sendmsg> getSendmsg() {
        if (this.sendmsg == null)
            this.sendmsg = new ArrayList<Sendmsg>();
        return this.sendmsg;
    }
}

