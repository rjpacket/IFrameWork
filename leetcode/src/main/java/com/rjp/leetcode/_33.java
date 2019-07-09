package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2019/6/26 11:03
 * email: jinpeng.ren@11bee.com
 */
public class _33 {

    public static void main(String[] args) {
        System.out.println(search(new int[]{8,9,2,3,4}, 9));
    }

    public static int search(int[] nums, int target) {
        if(nums == null || nums.length == 0){
            return -1;
        }
        if(nums.length == 1){
            if(nums[0] == target){
                return 0;
            }else{
                return -1;
            }
        }
        int minIndex = findMin(nums);
        int length = nums.length;
        if(minIndex == 0 || target < nums[0]){
            return findTarget(nums, minIndex, length - 1, target);
        }
        return findTarget(nums, 0, minIndex, target);
    }

    private static int findTarget(int[] nums, int left, int right, int target) {
        while (left <= right){
            int mid = (left + right) / 2;
            if(nums[mid] == target){
                return mid;
            }else if(nums[mid] > target){
                right = mid - 1;
            }else{
                left = left + 1;
            }
        }
        return -1;
    }

    private static int findMin(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        if(nums[left] < nums[right]){
            return 0;
        }
        while (left <= right){
            int mid = (left + right) / 2;
            if(nums[mid] > nums[mid + 1]){
                return mid + 1;
            }else{
                if(nums[mid] < nums[left]){
                    right = mid - 1;
                }else{
                    left = mid + 1;
                }
            }
        }
        return 0;
    }
}
