package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2019/7/23 19:37
 * email: jinpeng.ren@11bee.com
 */
public class _312 {

    public static int maxCoins(int[] nums) {
        int length = nums.length;
        int[] result = new int[length];
        int[] flag = new int[length];
        int maxIndex = -1;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < length; i++) {
            if(i == 0){
                result[i] = nums[i] * nums[i + 1];
            }else if(i == length - 1){
                result[i] = nums[i] * nums[i - 1];
            }else{
                result[i] = nums[i - 1] * nums[i] * nums[i + 1];
            }
            if(result[i] > max){
                max = result[i];
                maxIndex = i;
            }
        }
        flag[maxIndex] = 1;
        int count = 1;
        while (count < length){

        }
        return 0;
    }
}
