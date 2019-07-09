package com.rjp.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * author: jinpeng.ren create at 2019/6/25 14:49
 * email: jinpeng.ren@11bee.com
 */
public class _15 {

    public static void main(String[] args) {
        List<List<Integer>> lists = threeSum(new int[]{-1, 0, 1, 2, -1, -4});
        for (List<Integer> list : lists) {
            for (Integer integer : list) {
                System.out.print(integer + "|");
            }
            System.out.println(" ");
        }
    }

    public static List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        int length = nums.length;
        for (int i = 0; i < length; i++) {
            if(i > 0 && nums[i] == nums[i - 1]){
                continue;
            }
            int left = i + 1;
            int right = length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == 0) {
                    List<Integer> ints = new ArrayList<>();
                    ints.add(nums[i]);
                    ints.add(nums[left]);
                    ints.add(nums[right]);
                    result.add(ints);

                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }

                    left++;
                    right--;
                } else if (sum > 0) {
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    right--;
                } else {
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    left++;
                }
            }
        }
        return result;
    }
}
