package com.rjp.leetcode;

import java.util.Arrays;

/**
 * [186,419,83,408]
 * 6249
 * author: jinpeng.ren create at 2019/11/29 14:15
 * email: jinpeng.ren@11bee.com
 */
public class _322 {

    public static void main(String[] args) {
        int i = coinChange(new int[]{186, 419, 83, 408}, 6249);
        System.out.println(i);
    }

    public static int coinChange(int[] coins, int amount) {
        if (amount == 0) {
            return 0;
        }
        Arrays.sort(coins);
        int length = coins.length;
        int count = 0;
        for (int i = length - 1; i >= 0; i--) {
            while (amount >= coins[i]) {
                count += amount / coins[i];
                amount %= coins[i];
            }
        }
        if (amount > 0) {
            return -1;
        }
        return count == 0 ? -1 : count;
    }
}
