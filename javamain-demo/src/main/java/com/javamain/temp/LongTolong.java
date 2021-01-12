package com.javamain.temp;

public class LongTolong {
    public static void main(String[] args) {
         long start = System.currentTimeMillis();
         sum();
         long end = System.currentTimeMillis();
        System.out.println(end-start);
    }

    private static long sum(){
        long sum =0L;
        for(long i=0;i<=Integer.MAX_VALUE;i++){
            sum+=i;
        }
        return sum;
    }
}
