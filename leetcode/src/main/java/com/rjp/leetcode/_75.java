package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2019/7/10 18:27
 * email: jinpeng.ren@11bee.com
 */
public class _75 {

    public static void main(String[] args) {
        int[] nums = {2, 0, 1};
        sortColors(nums);
        for (int num : nums) {
            System.out.print(num + " | ");
        }
    }

    public static void sortColors(int[] nums) {
        if(nums == null || nums.length == 0){
            return;
        }
        int length = nums.length;
        int start = 0;
        int end = length - 1;
        for (int i = 0; i < length; i++) {
            if(i <= end){
                if(nums[i] == 0){
                    swap(nums, start, i);
                    start++;
                }else if(nums[i] == 2){
                    swap(nums, i, end);
                    end--;
                    i--;
                }
            }
        }
    }

    private static void swap(int[] nums, int start, int end) {
        int temp = nums[start];
        nums[start] = nums[end];
        nums[end] = temp;
    }
}
