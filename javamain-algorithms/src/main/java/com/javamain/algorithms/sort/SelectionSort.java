package com.javamain.algorithms.sort;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 选择排序
 */
public class SelectionSort {
    public static void main(String[] args) {
        int nums[] = {20, 12, 10, 15, 2};
        selectionSort(nums);
        String result = Arrays.stream(nums).mapToObj(String::valueOf)
                .collect(Collectors.joining(","));
        System.out.println(result);
    }

    /**
     * 核心是选择剩余数字中最小值
     * @param nums
     */
    public static void selectionSort(int[] nums) {
        int n = nums.length;

        // One by one move boundary of unsorted subarray
        for (int i = 0; i < n - 1; i++) {
            // Find the minimum element in remaining unsorted array
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (nums[j] < nums[minIndex]) {
                    minIndex = j;
                }
            }

            // Swap the found minimum element with the first element
            if (minIndex != i) {
                int temp = nums[minIndex];
                nums[minIndex] = nums[i];
                nums[i] = temp;
            }
        }
    }
}
