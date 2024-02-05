package com.yzhou.webservice;

import com.sms.client.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainTest {
    public static void main(String[] args) {
        // 调用之前要修改IShortMsg_Service 类的wsdlLocation 的路径
        // http://10.1.9.34:8080/smspre/ShortMsg?wsdl   根据每个行的实际地址修改
        IShortMsg_Service service=new IShortMsg_Service();
        IShortMsg shortMsg = service.getShortMsgServerPort();

        Head head=new Head();
        head.setAuthcode("123456");//认证码
        head.setDevno("");//设备号(可选)
        head.setReqsn(Long.toString(System.nanoTime()));//请求流水号
        head.setServicename("downsms");//后台服务方法
        head.setTranchannel("900");//渠道类型
        head.setTrandatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));//前端时间
        head.setVersion("V1.0");//版本号

        SendmsgS sendMsgs = new SendmsgS();
        Sendmsg sendMsg = new Sendmsg();
        sendMsg.setTelno("18356017166");    //短信手机号码
        sendMsg.setMsg("yzhou msg 1");    //短信内容
        sendMsg.setType("01");      //短信发送方式
        sendMsg.setCustno("");    //客户号，有就传，没有传空
        sendMsg.setLastsndtime("");   //短信预约时间，在短信发送方式为02的时候生效
        sendMsgs.getSendmsg().add(sendMsg);

        SendmsgReq sendmsgReq = new SendmsgReq();
        sendmsgReq.setOper("0100");   //系统ID，短信平台系统分配，请联系科技人员
        sendmsgReq.setRwct("1");
        sendmsgReq.setSendmsgs(sendMsgs);

        SendmsgReqMsg uxunmsg = new SendmsgReqMsg();
        uxunmsg.setMsgreq(sendmsgReq);
        uxunmsg.setMsghead(head);
        SendmsgRspMsg sendmsgRspMsg =  shortMsg.downsms(uxunmsg);

        System.out.println("yzhou1: "+sendmsgRspMsg.getMsgrsp().getRetcode());
        System.out.println("yzhou2: "+sendmsgRspMsg.getMsgrsp().getRetmsg());
    }
}
