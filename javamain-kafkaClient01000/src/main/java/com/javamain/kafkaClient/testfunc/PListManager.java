package com.javamain.kafkaClient.testfunc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PListManager {

    private static Map<String, List<Runnable>> pM=new HashMap<>();


    public static void putPList(String name,Runnable func){
        List<Runnable> runnables =null;
        if(pM.get(name)==null){
            runnables = new ArrayList<>();
            runnables.add(func);
        }else{
             runnables = pM.get(name);
            runnables.add(func);
        }
        pM.put(name,runnables);
    }

    public static void doit(String name){
        pM.forEach((k,n)->{
            if(k.equals(name)){
                for(Runnable itemn: n){
                    itemn.run();
                }
            }
        });
    }

}
