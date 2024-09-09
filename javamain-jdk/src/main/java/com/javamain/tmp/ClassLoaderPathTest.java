package com.javamain.tmp;

import sun.misc.Launcher;

import java.net.URL;

public class ClassLoaderPathTest {
    public static void main(String[] args) {
        System.out.println("引导类加载器");
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        for (URL url : urLs) {
            System.out.println(url.toExternalForm());
        }

        System.out.println("扩展类加载器");
        String extPath = System.getProperty("java.ext.dirs");
        System.out.println("extPath :" + extPath);
    }
}
