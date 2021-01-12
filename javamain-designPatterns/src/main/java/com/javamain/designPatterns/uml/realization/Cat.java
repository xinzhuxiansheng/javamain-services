package com.javamain.designPatterns.uml.realization;

public class Cat implements Animal{
    private String name;

    @Override
    public void run() {
        System.out.println("cat 速度5公里每秒");
    }
}
