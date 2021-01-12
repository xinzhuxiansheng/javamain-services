package com.javamain.designPatterns.uml.realization;

public class Dog implements Animal{
    private String name;

    @Override
    public void run() {
        System.out.println("dog 速度10公里每秒");
    }
}
