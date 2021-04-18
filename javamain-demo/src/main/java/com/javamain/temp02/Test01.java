package com.javamain.temp02;

import org.apache.commons.lang3.StringUtils;

public class Test01 {
    public static void main(String[] args) {
//        ConfigManager configManager = new ConfigManager();
//
//        Person p1= new Person("zhangsan",new Config(configManager));
//        Person p2= new Person("lisi",new Config(configManager));
//
//        p1 = null;
//
//        System.out.println(configManager.getListeners().size());

        String str = "373b7015f8254730a70ff29f088703be|3|xx.xxx.244.232:9092-xx.xxx.214.4:20470-55656";
        String[] b = StringUtils.splitByWholeSeparatorPreserveAllTokens(str,"|");
        String[] a = str.split("|");
        System.out.println(a.length);
        System.out.println(b.length);

    }
}
