package com.javamain.kafkaClient.demo;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class offsetMain {

    public static void main(String[] args) throws IOException {

        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("offset.log");

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String str = null;

        List<String> offsetlist = new ArrayList<>();

        while(StringUtils.isNoneBlank((str = reader.readLine()))){

            if(offsetlist.contains(str)){
                System.out.println(str);
                break;
            }else{
                offsetlist.add(str);
            }

        }
        System.out.println("结束");
    }
}
