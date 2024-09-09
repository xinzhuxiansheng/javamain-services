package com.javamain.javassist.tutorial;

import javassist.ClassPool;
import javassist.CtClass;

public class Tutorial {
    public static void main(String[] args) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        // test.Rectangle该类必须存在
        CtClass cc = pool.get("test.Rectangle");
        cc.setSuperclass(pool.get("test.Point"));
        cc.writeFile();
    }
}
