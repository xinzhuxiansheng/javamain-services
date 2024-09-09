package com.javamain.reflection;

/**
 * @author yzhou
 * @date 2023/2/2
 */
public class Person {

    private int age;
    private String name;

    public Person( String name,int age) {
        this.age = age;
        this.name = name;
        System.out.println("构造函数Person(有参数)执行");
    }

    public Person() {
        System.out.println("构造函数Person(无参数)执行");
    }
}
