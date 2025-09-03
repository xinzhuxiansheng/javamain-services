package com.xinzhuxiansheng.javaproject;

public class Student implements Person {
  public String favorite() {
    return "学生喜欢努力学习";
  }

  @Override
  public String name() {
    return "student";
  }
}
