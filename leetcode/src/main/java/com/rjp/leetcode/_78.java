package com.rjp.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * author: jinpeng.ren create at 2019/7/4 18:57
 * email: jinpeng.ren@11bee.com
 */
public class _78 {

    public static void main(String[] args) {
        List<List<Integer>> subsets = subsets(new int[]{1, 2, 3, 4});
        for (List<Integer> subset : subsets) {
            for (Integer integer : subset) {
                System.out.print(integer + " | ");
            }
            System.out.println("");
        }
    }

    public static List<List<Integer>> subsets(int[] nums) {
        if(nums == null){
            return null;
        }
        List<List<Integer>> result = new ArrayList<>();
        int length = nums.length;
        for (int i = 0; i < length; i++) {
            if(i != 0){
                List<List<Integer>> resultTemp = new ArrayList<>();
                for (List<Integer> list : result) {
                    List<Integer> temp = new ArrayList<>();
                    temp.addAll(list);
                    temp.add(nums[i]);
                    resultTemp.add(temp);
                }
                result.addAll(resultTemp);
            }
            List<Integer> one = new ArrayList<>();
            one.add(nums[i]);
            result.add(one);
        }
        result.add(new ArrayList<Integer>());
        return result;
    }
}
