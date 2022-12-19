package com.javamain.example;

import java.util.Scanner;

/**
 * @author yzhou
 * @date 2022/12/17
 */
public class ScannerTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            while (true) {
                System.out.print(":> ");
                String statStr = sc.nextLine();
                if ("exit".equals(statStr) || "quit".equals(statStr)) {
                    break;
                }
                try {
                    System.out.println(statStr);
                    //System.out.println(new String(res));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } finally {
            sc.close();
        }
    }
}
