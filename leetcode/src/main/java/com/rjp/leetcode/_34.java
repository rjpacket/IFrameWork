package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2019/7/10 13:47
 * email: jinpeng.ren@11bee.com
 */
public class _34 {

    public static void main(String[] args) {
        int[] ints = searchRange(new int[]{1}, 1);
        for (int anInt : ints) {
            System.out.println(anInt);
        }
    }

    public static int[] searchRange(int[] nums, int target) {
        int[] ans = new int[]{-1, -1};
        if(nums == null || nums.length == 0){
            return ans;
        }
        int length = nums.length;
        int pos = dSearch(nums, 0, length - 1, target);
        if(pos == -1){
            return ans;
        }
        int left = pos;
        while (left >= 0 && nums[left] == target){
            left--;
        }
        ans[0] = ++left;
        while (pos < length && nums[pos] == target){
            pos++;
        }
        ans[1] = --pos;
        return ans;
    }

    public static int dSearch(int[] nums, int start, int end, int target){
        if(start > end){
            return -1;
        }
        int mid = (start + end) / 2;
        if(nums[mid] == target){
            return mid;
        }else if(nums[mid] > target){
            return dSearch(nums, start, mid - 1, target);
        }else{
            return dSearch(nums, mid + 1, end, target);
        }
    }
}
