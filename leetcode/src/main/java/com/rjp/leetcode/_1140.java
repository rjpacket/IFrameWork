package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2019/7/30 11:42
 * email: jinpeng.ren@11bee.com
 */
public class _1140 {

    public static void main(String[] args) {
        int i = stoneGameII(new int[]{1, 2, 3, 4, 5, 100});
        System.out.println(i);
    }

    public static int stoneGameII(int[] piles) {
        int length = piles.length;
        int M = 1;
        int result = Integer.MIN_VALUE;
        for (int i = 1; i <= 2; i++) {
            int index = 0;
            int sum = 0;
            int X = i;
            while (index < length) {
                int end = X + index;
                if (end > length) {
                    end = length;
                }
                for (int j = index; j < end; j++) {
                    sum += piles[j];
                }
                index += X;
                M = Math.max(M, X);

                X = 2 * M;

                index += X;
                M = Math.max(M, X);

                X = 2 * M;
            }
            result = Math.max(result, sum);
        }
        return result;
    }
}
