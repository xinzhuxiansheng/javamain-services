package com.javamain.akka.example.ex01;

import lombok.Data;

/**
 * @author yzhou
 * @date 2022/12/15
 */
@Data
public class Message {
    private String name;
    private String age;
    private String step;

    public Message(){

    }

    public Message(String name,String age){
        this.name = name;
        this.age = age;
    }
}
