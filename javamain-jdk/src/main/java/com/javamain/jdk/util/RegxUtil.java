package com.javamain.jdk.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegxUtil {
     public static String replaceKafkaSpecialCharacters(String str){
         String result = null;
         if(StringUtils.isNoneBlank(str)) {
             Pattern p = Pattern.compile("\\\\+t|\r\n");
             Matcher m = p.matcher(str);
             if(m.find()) {
                 str = m.replaceAll("");
             }
         }
         return str;
     }
}
