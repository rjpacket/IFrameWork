package com.rjp.leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * author: jinpeng.ren create at 2019/6/26 20:10
 * email: jinpeng.ren@11bee.com
 */
public class _46 {

    public static void main(String[] args) {
        List<List<Integer>> permute = permute1(new int[]{1, 2, 3, 4});
        print(permute);
    }

    public static void permute(int[] nums){

    }

    public static void f(List<Integer> nums, List<List<Integer>> out, int start){
        if(start == nums.size()){
            out.add(nums);
        }
        int size = nums.size();
        for (int i = start; i < size; i++) {
            Collections.swap(nums, start, i);
        }
    }

    private static void print(List<List<Integer>> permute) {
        System.out.println("-------------------------------------------");
        for (List<Integer> list : permute) {
            System.out.print("[");
            for (Integer integer : list) {
                System.out.print(integer + ",");
            }
            System.out.println("]");
        }
    }

    public static List<List<Integer>> permute1(int[] nums) {
        if(nums == null || nums.length == 0){
            return null;
        }
        List<List<Integer>> result = new ArrayList<>();
        int length = nums.length;
        for (int i = 0; i < length; i++) {
            if(result.size() != 0){
                List<List<Integer>> temp = new ArrayList<>();
                for (List<Integer> list : result) {
                    if(list.size() != i){
                        continue;
                    }
                    int index = 0;
                    while (index < 2 * i + 1){
                        List<Integer> newList = new ArrayList<>();
                        for (int j = 0; j <= i; j++) {
                            if(index / 2 == j){
                                newList.add(nums[i]);
                            }
                            if(j < list.size()){
                                newList.add(list.get(j));
                            }
                        }
                        temp.add(newList);
                        index += 2;
                    }
                }
                result.addAll(temp);
                print(result);
            }else{
                List<Integer> list = new ArrayList<>();
                list.add(nums[0]);
                result.add(list);
                print(result);
            }
        }
        int size = result.size();
        for (int i = size - 1; i >= 0; i--) {
            List<Integer> list = result.get(i);
            if(list.size() != length){
                result.remove(list);
            }
        }
        return result;
    }
}
