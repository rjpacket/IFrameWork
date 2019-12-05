package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2019/11/29 12:26
 * email: jinpeng.ren@11bee.com
 */
public class _1124 {

    public static void main(String[] args) {
        int i = longestWPI(new int[]{9, 9, 6, 0, 6, 6, 9});
        System.out.println(i);
    }

    public static int longestWPI(int[] hours) {
        int length = hours.length;
        int sum = 0;
        int max = 0;
        int count = 0;
        for (int i = 0; i < length; i++) {
            if (hours[i] >= 8) {
                sum++;
                count++;
            } else if (sum > 0) {
                sum--;
                count++;
            }
            if (sum >= 0) {
                max = Math.max(max, count);
            }
        }
        return max;
    }
}
