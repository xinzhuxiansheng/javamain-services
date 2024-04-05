package com.javamain.tmp;

import sun.security.ec.SunEC;

public class ClassLoaderTest {
    public static void main(String[] args) {
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println("Sys:" + systemClassLoader); // jdk.internal.loader.ClassLoaders$AppClassLoader@251a69d7

        ClassLoader extClassLoader = systemClassLoader.getParent();
        System.out.println("Ext:" + extClassLoader); // jdk.internal.loader.ClassLoaders$PlatformClassLoader@5305068a

        ClassLoader bootstrapClassloader = extClassLoader.getParent();
        System.out.println("Bootstap:" + bootstrapClassloader);

        ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
        System.out.println("User:" + classLoader);

        ClassLoader classLoader2 = SunEC.class.getClassLoader();
        System.out.println("Ext:" + classLoader2);

        ClassLoader classLoader1 = Boolean.class.getClassLoader();
        System.out.println("Bool:" + classLoader1);
    }
}
