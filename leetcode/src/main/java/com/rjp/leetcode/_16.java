package com.rjp.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * author: jinpeng.ren create at 2019/6/25 17:27
 * email: jinpeng.ren@11bee.com
 */
public class _16 {

    public static void main(String[] args) {
        int i = threeSumClosest(new int[]{-1, 2, 1, -4}, 1);
        System.out.println(i);
    }

    public static int threeSumClosest(int[] nums, int target) {
        int length = nums.length;
        if(length < 3){
            return -1;
        }
        Arrays.sort(nums);
        int result = nums[0] + nums[1] + nums[2];
        for (int i = 0; i < length; i++) {
            if(i > 0 && nums[i] == nums[i - 1]){
                continue;
            }
            int left = i + 1;
            int right = length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == target) {
                    return sum;
                } else if (sum > target) {
                    result = Math.abs(sum - target) > Math.abs(result - target) ? result : sum;
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    right--;
                } else {
                    result = Math.abs(sum - target) > Math.abs(result - target) ? result : sum;
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
