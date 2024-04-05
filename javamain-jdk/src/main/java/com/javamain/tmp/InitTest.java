package com.javamain.tmp;

public class InitTest {
    static {
        a = 20;
    }
    public static int a = 10;
    public static void main(String[] args) {
        System.out.println(InitTest.a);
    }
}
