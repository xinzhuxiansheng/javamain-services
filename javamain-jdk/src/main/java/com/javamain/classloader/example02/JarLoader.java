package com.javamain.classloader.example02;

import java.net.URL;
import java.net.URLClassLoader;

public class JarLoader {
    public static void main(String[] args) {
        try {
            // 1. 确定JAR文件的路径
            String jarFilePath = "/Users/a/Desktop/DataPipeline/test-encryptor-1.0-SNAPSHOT.jar";  // 替换为你的JAR文件的实际路径

            // 2. 使用URLClassLoader加载JAR文件
            URL[] urls = { new URL("jar:file:" + jarFilePath + "!/") };
            URLClassLoader classLoader = new URLClassLoader(urls);

            // 3. 加载你想要的类
            Class<?> loadedClass = classLoader.loadClass("com.datapipeline.Main1");  // 替换为你想要加载的类的全名
            System.out.println("Class loaded: " + loadedClass.getName());

            // 如果你想实例化这个类（确保这个类有一个公共的无参构造函数）
            Object instance = loadedClass.newInstance();
            System.out.println("Instance created: " + instance);

            classLoader.close();  // 释放资源
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
