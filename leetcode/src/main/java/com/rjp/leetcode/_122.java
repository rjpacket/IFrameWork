package com.rjp.leetcode;

import android.util.SparseIntArray;

import java.util.HashMap;
import java.util.Map;

/**
 * author: jinpeng.ren create at 2019/7/5 20:41
 * email: jinpeng.ren@11bee.com
 */
public class _122 {

    public static void main(String[] args) {
        System.out.println(maxProfit(new int[]{1,2,3,4,5}));
    }

    public static int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0 || prices.length == 1) {
            return 0;
        }
        int length = prices.length;
        int min = prices[0];
        int max = Integer.MIN_VALUE;
        int result = 0;
        for (int i = 0; i < length - 1; i++) {
            if (prices[i] < min) {
                min = prices[i];
            } else if (prices[i] > max) {
                max = prices[i];
                result += (max - min);
                min = prices[i];
                max = Integer.MIN_VALUE;
            }
        }
        if (prices[length - 1] > prices[length - 2]) {
            result += (prices[length - 1] - prices[length - 2]);
        }
        return result;
    }
}
