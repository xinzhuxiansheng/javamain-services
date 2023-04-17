package com.javamain.java8.javastream;

import java.util.Arrays;
import java.util.List;

/**
 * @author yzhou
 * @date 2022/11/20
 */
public class Test01 {
    public static void main(String[] args) {
//        sumTest01();
//        sumTest02();
//        sumTest03();

        System.out.println( Math.ceil(5/2));
        System.out.println(5/2);
    }

    public static void sumTest01() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        Integer sum = integers.stream()
                .reduce(0, (a, b) -> a + b);

        System.out.println("sumTest01: " + sum);
    }

    public static void sumTest02() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        Integer sum = integers.stream()
                .reduce(0, Integer::sum);
        System.out.println("sumTest02: " + sum);
    }

    public static void sumTest03() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        Integer sum = integers.stream()
                .reduce(0, ArithmeticUtils::add);
        System.out.println("sumTest03: " + sum);
    }

}

