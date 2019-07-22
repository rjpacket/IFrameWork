package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2019/7/5 19:50
 * email: jinpeng.ren@11bee.com
 */
public class _198 {

    public static void main(String[] args) {
        System.out.println(rob(new int[]{1}));
    }

    //  1  7  9  3  1
    public static int rob(int[] nums) {
        int curMax = 0;
        int preMax = 0;
        for (int num : nums) {
            int temp = curMax;
            curMax = Math.max(curMax, preMax + num);
            preMax = temp;
        }
        return curMax;
    }
}
