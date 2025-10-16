package com.xinzhuxiansheng.sso;

public class Teacher implements Person {

  public String favorite() {
//    return "老师喜欢给学生上课";
    AClass aClass = new AClass();
    return aClass.doSomething();
  }

  @Override
  public String name() {
    return "teacher";
  }
}
