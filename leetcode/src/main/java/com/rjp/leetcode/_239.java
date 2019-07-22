package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2019/7/12 11:48
 * email: jinpeng.ren@11bee.com
 */
public class _239 {

    public static void main(String[] args) {
        int[] ints = maxSlidingWindow(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3);
        for (int anInt : ints) {
            System.out.println(anInt);
        }
    }

    public static int[] maxSlidingWindow(int[] nums, int k) {
        if(nums == null || nums.length == 0 || k == 0){
            return new int[0];
        }
        int maxIndex = getMax(nums, 0, k - 1);
        int length = nums.length;
        int[] max = new int[length - k + 1];
        int index = 0;
        max[index] = nums[maxIndex];
        for (int i = k; i < length; i++) {
            if(nums[i] > nums[maxIndex]){
                max[++index] = nums[i];
                maxIndex = i;
            }else if(nums[i - k] < nums[maxIndex]){
                max[++index] = nums[maxIndex];
            }else{
                maxIndex = getMax(nums, i - k + 1, i);
                max[++index] = nums[maxIndex];
            }
        }
        return max;
    }

    private static int getMax(int[] nums, int start, int end) {
        int max = nums[start];
        int maxIndex = start;
        for (int i = start + 1; i <= end; i++) {
            if(nums[i] > max){
                max = nums[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }
}
