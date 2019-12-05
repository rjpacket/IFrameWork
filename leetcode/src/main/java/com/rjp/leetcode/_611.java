package com.rjp.leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * author: jinpeng.ren create at 2019/12/2 20:28
 * email: jinpeng.ren@11bee.com
 */
public class _611 {

    public static void main(String[] args) {
        int i = triangleNumber(new int[]{2, 2, 3, 4});
        System.out.println(i);
    }

    public static int triangleNumber(int[] nums) {
        if(nums == null){
            return 0;
        }
        int count = 0;
        int length = nums.length;
        int left = 0;
        while (left < length - 1){
            int right = length - 1;
            while (right > 0) {
                for (int i = left + 1; i < right; i++) {
                    int a = nums[left];
                    int b = nums[i];
                    int c = nums[right];
                    if (a > 0 && b > 0 && c > 0 && a + b > c && a + c > b && b + c > a) {
                        count++;
                    }
                }
                right--;
            }
            left++;
        }
        return count;
    }

    public static int triangleNumber1(int[] nums) {
        if (nums == null) {
            return 0;
        }
        int length = nums.length;
        int[] number = new int[length];
        for (int i = 0; i < length; i++) {
            number[i] = i;
        }
        List<List<Integer>> result = new ArrayList<>();
        int numLen = number.length;
        for (int i = 0; i < 3; i++) {
            if (i == 0) {
                for (int j = 0; j < numLen; j++) {
                    List<Integer> tmp = new ArrayList<>();
                    tmp.add(number[j]);
                    result.add(tmp);
                }
            } else {
                int size = result.size();
                for (int j = 0; j < size; j++) {
                    List<Integer> list = result.get(j);
                    int index = list.size();
                    if (index == i) {
                        for (int k = list.get(index - 1) + 1; k < numLen; k++) {
                            if (!list.contains(number[k])) {
                                List<Integer> tmp = new ArrayList<>();
                                for (Integer integer : list) {
                                    tmp.add(integer);
                                }
                                tmp.add(number[k]);
                                result.add(tmp);
                            }
                        }
                    }
                }
            }
        }
        int count = 0;
        int size = result.size();
        for (int i = 0; i < size; i++) {
            List<Integer> list = result.get(i);
            if (list.size() == 3) {
                int a = nums[list.get(0)];
                int b = nums[list.get(1)];
                int c = nums[list.get(2)];
                if (a > 0 && b > 0 && c > 0 && a + b > c && a + c > b && b + c > a) {
                    count++;
                }
            }
        }
        return count;
    }
}
