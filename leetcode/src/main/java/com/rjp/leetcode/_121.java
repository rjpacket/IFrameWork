package com.rjp.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author: jinpeng.ren create at 2019/7/5 16:58
 * email: jinpeng.ren@11bee.com
 */
public class _121 {

    public static void main(String[] args) {
        System.out.println(maxProfit(new int[]{1,2}));
    }

    //[7,1,5,3,6,4]
    public static int maxProfit(int[] prices) {
        if(prices == null || prices.length == 0){
            return 0;
        }
        int length = prices.length;
        int result = 0;
        int min = prices[0];
        for (int i = 0; i < length; i++) {
            if(min > prices[i]){
                min = prices[i];
            }else {
                result = Math.max(result, prices[i] - min);
            }
        }
        return result;
    }
}
