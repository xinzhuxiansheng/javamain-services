package com.javamain.algorithms.sort;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 插入排序
 */
public class InsertionSort {

    public static void main(String[] args) {
        int[] nums = {20, 12, 10, 15, 2};

        insertionSort(nums);
        String result = Arrays.stream(nums).mapToObj(String::valueOf)
                .collect(Collectors.joining(","));
        System.out.println(result);
    }

    public static void insertionSort(int[] array) {
        int n = array.length;
        for (int i = 1; i < n; ++i) {
            int key = array[i]; // 将判断的值先从copy，
            int j = i - 1;

            // Move elements of array[0..i-1], that are
            // greater than key, to one position ahead
            // of their current position
            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j = j - 1;
            }
            array[j + 1] = key;
        }
    }

}
