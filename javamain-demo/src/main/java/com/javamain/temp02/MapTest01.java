package com.javamain.temp02;

import java.util.HashMap;
import java.util.Map;

public class MapTest01 {
    public static void main(String[] args) {
        Map<String,String> maps = new HashMap<>();
        setMap(maps);
        System.out.println(maps.size());
    }

    public static void setMap(Map<String,String> maps){
        maps.put("a","a");
    }
}
