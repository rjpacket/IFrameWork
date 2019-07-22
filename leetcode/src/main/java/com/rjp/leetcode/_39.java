package com.rjp.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * author: jinpeng.ren create at 2019/7/10 14:31
 * email: jinpeng.ren@11bee.com
 */
public class _39 {

    public static void main(String[] args) {
        List<List<Integer>> lists = combinationSum(new int[]{2, 3, 6, 7}, 7);
        for (List<Integer> list : lists) {
            for (Integer integer : list) {
                System.out.print(integer + " | ");
            }
            System.out.println(" ");
        }
    }

    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        back(result, candidates, target, 0, new ArrayList<Integer>(), 0);
        return result;
    }

    public static void back(List<List<Integer>> result, int[] candidates, int target, int total, List<Integer> curNums, int start){
        if(total > target){
            return;
        }
        if(total == target){
            result.add(curNums);
            return;
        }
        int length = candidates.length;
        for (int i = start; i < length; i++) {
            List<Integer> cur = new ArrayList<>();
            cur.addAll(curNums);
            cur.add(candidates[i]);
            back(result, candidates, target, total + candidates[i], cur, i);
        }
    }
}
