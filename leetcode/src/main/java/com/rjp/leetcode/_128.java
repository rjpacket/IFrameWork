package com.rjp.leetcode;

import android.util.SparseArray;

import java.util.HashMap;
import java.util.Map;

/**
 * author: jinpeng.ren create at 2019/7/11 17:00
 * email: jinpeng.ren@11bee.com
 */
public class _128 {

    public static void main(String[] args) {
        System.out.println(longestConsecutive(new int[]{0, -1}));
    }

    public static int longestConsecutive(int[] nums) {
        if(nums == null || nums.length == 0){
            return 0;
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], 1);
        }
        int ans = Integer.MIN_VALUE;
        for (int num : nums) {
            if(!map.containsKey(num - 1)){
                int curNum = num;
                int longer = 1;
                while (map.containsKey(curNum + 1)){
                    curNum++;
                    longer++;
                }
                ans = Math.max(ans, longer);
            }
        }
        return ans;
    }
}
