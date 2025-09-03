package com.xinzhuxiansheng.javaproject;

import com.alibaba.fastjson2.JSONObject;
import com.xinzhuxiansheng.javaproject.loadplugin.ConfigParser;
import com.xinzhuxiansheng.javaproject.loadplugin.Configuration;
import com.xinzhuxiansheng.javaproject.loadplugin.JarLoader;
import com.xinzhuxiansheng.javaproject.loadplugin.LoadUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

public class SPITest {
  public static void main(String[] args) {

    Configuration configuration = ConfigParser.parse();
    Map reader = configuration.get("reader",HashMap.class);
    Map writer = configuration.get("writer",HashMap.class);
    JarLoader readerJarLoader = LoadUtil.getJarLoader(reader.get("pluginName").toString());
    JarLoader writerJarLoader = LoadUtil.getJarLoader(writer.get("pluginName").toString());
    print(readerJarLoader);
    print(writerJarLoader);
  }

  public static void print(JarLoader classLoader){
    ServiceLoader<Person> loader = ServiceLoader.load(Person.class,classLoader);
    for (Iterator<Person> it = loader.iterator(); it.hasNext(); ) {
      Person person = it.next();
      System.out.println(person.favorite());
    }
  }
}
