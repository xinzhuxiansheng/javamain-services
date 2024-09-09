package com.javamain.java8.annotation;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

public class MyFieldTest {

    @MyField(description = "用户名",length = 12)
    private String userName;

    @Test
    public void testMyField(){
        Class c =  MyFieldTest.class;

        for(Field f: c.getDeclaredFields()){
            if(f.isAnnotationPresent(MyField.class)){
                MyField annotation = f.getAnnotation(MyField.class);
                System.out.println("字段:[" + f.getName() + "], 描述:[" + annotation.description() + "], 长度:[" + annotation.length() +"]");
            }
        }
    }

}
