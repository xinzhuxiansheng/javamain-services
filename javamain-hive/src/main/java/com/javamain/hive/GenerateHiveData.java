package com.javamain.hive;

import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * Created by xuwei
 */
public class GenerateHiveData {
    public static void main(String[] args) throws Exception{
        String fileName = "D:\\stu_textfile.dat";
        System.out.println("start: 开始生成文件->"+fileName);
        BufferedWriter bfw = new BufferedWriter(new FileWriter(fileName));
        int num = 0;
        while(num<60000000){
            bfw.write("1"+num+",zs"+num+",beijing"+num);
            bfw.newLine();
            num ++;
            if(num%10000==0){
                bfw.flush();
            }
        }
        System.out.println("end: 文件已生成");
    }
}
