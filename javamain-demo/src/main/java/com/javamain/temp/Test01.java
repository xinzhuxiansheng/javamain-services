package com.javamain.temp;

import java.util.BitSet;
import java.util.concurrent.atomic.AtomicInteger;

public class Test01 {
    public static void main(String[] args) {
//        AtomicInteger errorCount = new AtomicInteger(9);
//        int count = errorCount.get();
//        errorCount.set(0);
//        System.out.println(count);

        //System.out.println(Integer.toBinaryString(5000)); // 1001110001000  <<

        //System.out.println(5000 << 10000);


        String a = null;
        try{
            if(a.contains("b")){
                System.out.println("aaaaa");
            }
        }catch(Exception e){
            System.out.println(e.getCause().getMessage());
        }
    }
}
