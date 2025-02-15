package com.javamain.java8;

import org.apache.logging.log4j.core.appender.db.jpa.converter.ThrowableAttributeConverter;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;

public class ArrayListDebug {
    public static void main(String[] args) throws InterruptedException {

//        ArrayList<String> arrayList = new ArrayList<>();
//        arrayList.add("add");
        //arrayList.remove(0);


//        Thread th1 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while(true){
//                    arrayList.set(0,"th1");
//                }
//            }
//        });
//
//        Thread th2 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true){
//                    arrayList.set(0,"th2");
//                }
//            }
//        });
//
//        th1.start();
//        th2.start();
//
//        Thread.sleep(10000);
//        System.out.println(arrayList.get(0));
//        System.out.println("结束");
        Clock clock = Clock.systemUTC();
        System.out.println(clock.millis());
    }
}
