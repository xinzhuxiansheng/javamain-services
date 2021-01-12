package com.javamain.jdk.util;

import java.util.BitSet;

public class BitSetDebug {

    public static void main(String[] args) {

    }

    private static void computerPrime(){
        BitSet sieve = new BitSet(1024);
        int size = sieve.size();
        System.out.println(size);
        for (int i = 2; i < size; i++) {
            sieve.set(i);
        }
        System.out.println(size);
        int finalBit = (int) Math.sqrt(size);

        for (int i = 2; i < finalBit; i++) {
            if (sieve.get(i)) {
                for (int j = 2*i; j < size; j+=i) {
                    sieve.clear(j);
                }
            }
        }
        int counter = 0;
        for (int i = 1; i < size; i++) {
            if (sieve.get(i)){
                System.out.printf("%5d",i);
                if (++counter % 15 == 0){
                    System.out.println();
                }
            }
        }
        System.out.println();
    }
}
