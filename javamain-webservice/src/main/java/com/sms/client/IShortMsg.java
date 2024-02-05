package com.sms.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


@WebService(name = "IShortMsg", targetNamespace = "http://Server_15.sevice.uxunplat.uxun.com/")
public interface IShortMsg {
    @WebMethod
    @WebResult(name = "uxunmsg", targetNamespace = "")
    @RequestWrapper(localName = "downsms", targetNamespace = "http://Server_15.sevice.uxunplat.uxun.com/", className = "com.sms.client.Downsms")
    @ResponseWrapper(localName = "downsmsResponse", targetNamespace = "http://Server_15.sevice.uxunplat.uxun.com/", className = "com.sms.client.DownsmsResponse")
    SendmsgRspMsg downsms(@WebParam(name = "uxunmsg", targetNamespace = "") SendmsgReqMsg paramSendmsgReqMsg);

    @WebMethod
    @WebResult(name = "uxunmsg", targetNamespace = "")
    @RequestWrapper(localName = "searchsms", targetNamespace = "http://Server_15.sevice.uxunplat.uxun.com/", className = "com.sms.client.Searchsms")
    @ResponseWrapper(localName = "searchsmsResponse", targetNamespace = "http://Server_15.sevice.uxunplat.uxun.com/", className = "com.sms.client.SearchsmsResponse")
    SearchsmsRspMsg searchsms(@WebParam(name = "uxunmsg", targetNamespace = "") SearchsmsReqMsg paramSearchsmsReqMsg);
}

