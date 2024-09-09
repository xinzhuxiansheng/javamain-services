package com.javamain.akka.example.ex02;

import lombok.Data;

/**
 * @author yzhou
 * @date 2022/12/12
 */
@Data
public class SomeOne {
    private Integer id;
    private String name;
    private Integer age;

    public SomeOne(Integer id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}
