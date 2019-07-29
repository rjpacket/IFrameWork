package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2019/7/24 17:46
 * email: jinpeng.ren@11bee.com
 */
public class _303 {
    public int[] temp;
    public int[] nums;

    public _303(int[] nums) {
        int length = nums.length;
        temp = new int[length];
        for (int i = 0; i < length; i++) {
            if(i == 0){
                temp[i] = nums[i];
            }else{
                temp[i] = temp[i - 1] + nums[i];
            }
        }
        this.nums = nums;
    }

    public int sumRange(int i, int j) {
        return temp[j] - temp[i] + nums[i];
    }

    public static void main(String[] args) {
        _303 x = new _303(new int[]{-2, 0, 3, -5, 2, -1});
        System.out.println(x.sumRange(0, 2));
    }
}
