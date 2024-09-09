package com.javamain.classloader.example01;

/*
    对于静态字段，只有直接定义这个字段的类才会被初始化，因此通过其子类来引用父类中定义的静态字段，只会触发
    父类的初始化而不会触发子类的初始化。
 */
public class Notnitialization {
    public static void main(String[] args) {
        // 测试01
        //System.out.println(SubClass.value);

        // 测试02
        //SuperClass[] sca = new SuperClass[10];

        // 测试03
        System.out.println(ConstClass.HELLOWORLD);

    }
}
