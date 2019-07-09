package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2019/6/25 13:41
 * email: jinpeng.ren@11bee.com
 */
public class _11 {

    public static void main(String[] args) {
        System.out.println(max(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));
    }

    public static int max(int[] a) {
        int max = 0;
        for (int i = 0, j = a.length - 1; i < j; ) {
            int minHeight = a[i] < a[j] ? a[i++] : a[j--];
            max = Math.max(max, (j - i + 1) * minHeight);
        }
        return max;
    }


    public static int maxArea(int[] height) {
        int length = height.length;
        int max = 0;
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                int temp = getMaxIJ(height, i, j);
                if (max < temp) {
                    max = temp;
                }
            }
        }
        return max;
    }

    private static int getMaxIJ(int[] height, int i, int j) {
        int min = Math.min(height[i], height[j]);
        return min * (j - i);
    }
}
