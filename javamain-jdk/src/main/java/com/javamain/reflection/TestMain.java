package com.javamain.reflection;

import java.lang.reflect.Field;

/**
 * @author yzhou
 * @date 2023/2/2
 */
public class TestMain {

    public static void main(String[] args) throws Exception {

        //获取字节码文件中字段getDeclaredField （能取出所有权限的字段）

        String classname = "com.javamain.reflection.Person";
        //寻找名称的类文件，加载进内存 产生class对象
        Class cl = Class.forName(classname);

        //获取Person类中的 private String name 字段
        Field field_age = cl.getDeclaredField("age");
        //打印
        System.out.println(field_age);

        //创建一个新的Person对象 (无参执行)
        Object obj = cl.newInstance();

        //对私有字段访问取消权限检查。 暴力访问
        field_age.setAccessible(true);

        //获取新对象里的私有字段age的值
        Object age_val = field_age.get(obj);

        //输出私有字段age的值
        System.out.println("private age :" + age_val);

        //对私有字段赋值
        field_age.set(obj, 28);

        //在此获取私有字段字段age的值
        age_val = field_age.get(obj);

        //输出私有字段的值
        System.out.println("private age :" + age_val);
    }
}
