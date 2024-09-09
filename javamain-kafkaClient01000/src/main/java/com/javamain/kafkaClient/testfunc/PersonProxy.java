package com.javamain.kafkaClient.testfunc;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

public class PersonProxy {

    public Map<String,Person> proxyMa;

    PersonProxy(){
        proxyMa = new HashMap<>();
    }

    public void init(Integer i){
        Person  p1  = new Person("name"+i);
        Person  p2  = new Person("name"+(i+1));
        proxyMa.put(p1.getName(),p1);
        PListManager.putPList(p1.getName(),()->setManager(p1.getName()));
        proxyMa.put(p2.getName(),p2);
        PListManager.putPList(p2.getName(),()->setManager(p2.getName()));
    }


    public void setManager(String name){
        Person p = proxyMa.get(name);
        p.setTag("触发");
        proxyMa.put(p.getName(),p);
    }
}
