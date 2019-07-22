package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2019/7/12 18:24
 * email: jinpeng.ren@11bee.com
 */
public class _287 {

    public static void main(String[] args) {
        System.out.println(findDuplicate(new int[]{2,3,5,3}));
    }

    public static int findDuplicate(int[] nums) {
        if (nums == null || nums.length == 0 || nums.length == 1) {
            return -1;
        }
        if(nums.length == 2){
            if((nums[0] ^ nums[1]) == 0){
                return nums[0];
            }
            return -1;
        }
        int length = nums.length;
        int preNum = 0;
        for (int i = 0; i < length; i++) {
            int curNum = preNum ^ nums[i];
            if(curNum == preNum){
                return nums[i];
            }
            preNum = curNum;
        }
        return -1;
    }
}
