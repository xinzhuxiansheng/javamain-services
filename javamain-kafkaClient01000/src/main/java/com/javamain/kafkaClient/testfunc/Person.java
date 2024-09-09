package com.javamain.kafkaClient.testfunc;

public class Person {

    private String name;
    private String tag ;

    Person(String name){
        this.name = name;
        this.tag = "";
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
