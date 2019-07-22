package com.rjp.leetcode;

import java.util.*;

/**
 * author: jinpeng.ren create at 2019/7/18 14:50
 * email: jinpeng.ren@11bee.com
 */
public class _347 {

    public static void main(String[] args) {
        List<Integer> list = topKFrequent(new int[]{1, 1, 1, 2, 2, 3}, 2);
        for (Integer integer : list) {
            System.out.println(integer);
        }
    }

    public static List<Integer> topKFrequent(int[] nums, int k) {

        return null;
    }

    public static List<Integer> topKFrequent1(int[] nums, int k) {
        if(nums == null || nums.length == 0){
            return null;
        }
        int length = nums.length;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < length; i++) {
            if(map.containsKey(nums[i])){
                Integer integer = map.get(nums[i]);
                map.put(nums[i], integer + 1);
            }else{
                map.put(nums[i], 1);
            }
        }
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            Set<Integer> integers = map.keySet();
            int count = 0;
            int max = 0;
            int maxKey = 0;
            for (Integer integer : integers) {
                if(count == 0){
                    max = map.get(integer);
                    maxKey = integer;
                }else{
                    Integer integer1 = map.get(integer);
                    if(integer1 > max){
                        max = integer1;
                        maxKey = integer;
                    }
                }
                count++;
            }
            result.add(maxKey);
            map.remove(maxKey);
        }
        return result;
    }
}
