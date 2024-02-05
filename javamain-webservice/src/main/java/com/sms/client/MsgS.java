package com.sms.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "msgS", propOrder = {"msg"})
public class MsgS {
    @XmlElement(nillable = true)
    protected List<Msg> msg;

    public List<Msg> getMsg() {
        if (this.msg == null)
            this.msg = new ArrayList<Msg>();
        return this.msg;
    }
}

