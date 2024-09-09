package com.javamain.kafkaClient.testfunc;

import com.alibaba.fastjson.JSON;

public class testmain {

    public static void main(String[] args) {

        PersonProxy p1 = new PersonProxy();
        p1.init(1);
        System.out.println(JSON.toJSONString(p1.proxyMa));

        PersonProxy p2 = new PersonProxy();
        p2.init(1);
        System.out.println(JSON.toJSONString(p2.proxyMa));

        PListManager.doit("name1");

        System.out.println("\r");
        System.out.println("结果");
        System.out.println(JSON.toJSONString(p1.proxyMa));
        System.out.println(JSON.toJSONString(p2.proxyMa));
    }
}
