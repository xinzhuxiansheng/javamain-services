package com.javamain.kafkaClient;

import com.javamain.kafkaClient.common.Kafka_Info;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MonitorADMain {

    private static List<Kafka_Info> list = new ArrayList<>();

    public static void main(String[] args) {

        try{
            int i=0;


            byte[] sss1 = new byte[1000000000];
            byte[] sss2 = new byte[1000000000];
            byte[] sss3 = new byte[1000000000];
            byte[] sss4 = new byte[1000000000];
            byte[] sss5 = new byte[1000000000];
            byte[] sss6 = new byte[1000000000];
            byte[] sss7 = new byte[1000000000];
            byte[] sss8 = new byte[1000000000];
            byte[] sss9 = new byte[1000000000];
            byte[] sss10 = new byte[1000000000];
            byte[] sss11 = new byte[1000000000];
            byte[] sss12 = new byte[1000000000];
            byte[] sss13 = new byte[1000000000];
            byte[] sss14 = new byte[1000000000];
            byte[] sss15 = new byte[1000000000];
            byte[] sss16 = new byte[1000000000];
            byte[] sss17 = new byte[1000000000];
            byte[] sss18 = new byte[1000000000];
            byte[] sss19 = new byte[1000000000];
            byte[] sss20 = new byte[1000000000];
            byte[] sss21 = new byte[1000000000];
            byte[] sss22 = new byte[1000000000];
            byte[] sss23 = new byte[1000000000];
            byte[] sss24 = new byte[1000000000];
            byte[] sss25 = new byte[1000000000];
            byte[] sss26 = new byte[1000000000];
            byte[] sss27 = new byte[1000000000];
            byte[] sss28 = new byte[1000000000];
            byte[] sss29 = new byte[1000000000];
            byte[] sss30 = new byte[1000000000];
            byte[] sss31 = new byte[1000000000];
            byte[] sss32 = new byte[1000000000];
            byte[] sss33 = new byte[1000000000];
            byte[] sss34 = new byte[1000000000];
            byte[] sss35 = new byte[1000000000];
            byte[] sss36 = new byte[1000000000];
            byte[] sss37 = new byte[1000000000];
            byte[] sss38 = new byte[1000000000];
            byte[] sss39 = new byte[1000000000];
            byte[] sss40 = new byte[1000000000];


            while(true){
                i++;
                Kafka_Info kafka_info = new Kafka_Info("clusterName"+i,"topicName"+i,"port"+i,"consuemrGroup"+i);
                list.add(kafka_info);
                if(i%100000==0){

                    for(int k=0;k<35;k++){
                        byte[] aaaa = new byte[1000000000];
                    }

                    Thread.currentThread().sleep(10000L);

                    List<Kafka_Info> newlist = list.stream().filter(k -> k.getClusterName().hashCode()>100).collect(Collectors.toList());
                    list.clear();
                    i=0;
                }
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
