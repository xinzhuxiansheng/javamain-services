package com.javamain.classloader.example01;

public class SubClass extends SuperClass {
    static {
        System.out.println("SubClass init!");
    }
}
