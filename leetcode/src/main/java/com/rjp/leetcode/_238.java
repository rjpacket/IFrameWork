package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2019/7/9 16:46
 * email: jinpeng.ren@11bee.com
 */
public class _238 {

    public static int[] productExceptSelf(int[] nums) {
        if(nums == null || nums.length == 0){
            return null;
        }
        int length = nums.length;
        int[] ltr = new int[length];
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                ltr[i] = nums[i];
            } else {
                ltr[i] = nums[i] * ltr[i - 1];
            }
        }
        int[] rtl = new int[length];
        for (int i = length - 1; i >= 0; i--) {
            if (i == length - 1) {
                rtl[i] = nums[i];
            } else {
                rtl[i] = nums[i] * rtl[i + 1];
            }
        }
        int[] result = new int[length];
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                result[i] = rtl[i + 1];
            } else if (i == length - 1) {
                result[i] = ltr[i - 1];
            } else {
                result[i] = ltr[i - 1] * rtl[i + 1];
            }
        }
        return result;
    }
}
