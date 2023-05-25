package com.javamain.algorithms.exercise;

/*
    53. 最大子数组和

    https://leetcode.cn/problems/maximum-subarray/description/
 */
public class maxSubArray {
    public static void main(String[] args) {
        int[] arr = {-2,1,-3,4,-1,2,1,-5,4};
        System.out.println(maxSubArray(arr));
    }

    public static int maxSubArray(int[] nums) {
        int n = nums.length;
        int currSum = nums[0], maxSum = nums[0];

        for (int i = 1; i < n; ++i) {
            currSum = Math.max(nums[i], currSum + nums[i]);
            maxSum = Math.max(maxSum, currSum);
        }
        return maxSum;
    }
}