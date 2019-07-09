package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2019/6/28 20:07
 * email: jinpeng.ren@11bee.com
 */
public class _26 {

    public static void main(String[] args) {
        int i = removeDuplicates(new int[]{1, 1, 1, 1, 2, 3, 3});
        System.out.println(i);
    }

    public static int removeDuplicates(int[] nums) {
        if(nums == null || nums.length == 0){
            return 0;
        }
        if(nums.length == 1){
           return 1;
        }
        int pov = nums[0];
        int index = 1;
        int length = nums.length;
        for (int i = 1; i < length; i++) {
            if(nums[i] != pov){
                int temp = nums[i];
                nums[i] = nums[index];
                nums[index] = temp;

                pov = temp;
                index++;
            }
        }
        return index;
    }
}
