package com.rjp.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * author: jinpeng.ren create at 2019/7/30 17:44
 * email: jinpeng.ren@11bee.com
 */
public class _120 {

    public static void main(String[] args) {
        ArrayList<List<Integer>> triangle = new ArrayList<>();
        triangle.add(arrayToList(new int[]{-10}));
//        triangle.add(arrayToList(new int[]{3, 4}));
//        triangle.add(arrayToList(new int[]{6, 5, 7}));
//        triangle.add(arrayToList(new int[]{4, 1, 8, 3}));
        int i = minimumTotal(triangle);
        System.out.println(i);
    }

    private static List<Integer> arrayToList(int[] ints) {
        ArrayList<Integer> nums = new ArrayList<>();
        for (int anInt : ints) {
            nums.add(anInt);
        }
        return nums;
    }

    public static int minimumTotal(List<List<Integer>> triangle) {
        if(triangle == null || triangle.size() == 0){
            return 0;
        }
        int size = triangle.size();
        if(size == 1){
            return triangle.get(0).get(0);
        }
        for (int i = size - 1; i > 0; i--) {
            List<Integer> bottomList = triangle.get(i);
            List<Integer> topList = triangle.get(i - 1);
            int length = bottomList.size();
            int start = 0;
            if (i != size - 1) {
                start = i + 1;
            }
            for (int j = start; j < length - 1; j++) {
                topList.add(topList.get(j - start) + Math.min(bottomList.get(j), bottomList.get(j + 1)));
            }
        }
        return triangle.get(0).get(1);
    }
}
