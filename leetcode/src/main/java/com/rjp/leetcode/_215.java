package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2019/7/9 15:51
 * email: jinpeng.ren@11bee.com
 */
public class _215 {

    public static void main(String[] args) {
        int[] arr = new int[]{3,2,3,1,2,4,5,5,6,7,7,8,2,3,1,1,1,10,11,5,6,2,4,7,8,5,6};
        System.out.println(findKthLargest(arr, 20));
    }

    public static int findKthLargest(int[] nums, int k) {
        return fastSort(nums, 0, nums.length - 1, nums.length - k);
    }

    private static int fastSort(int[] nums, int left, int right, int index) {
        int tempLeft = left;
        int key = nums[right];
        int keyIndex = right;
        while (left < right) {
            while (left < right && nums[left] < key) {
                left++;
            }
            while (left < right && nums[right] >= key) {
                right--;
            }
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
        }
        int temp = nums[left];
        nums[left] = key;
        nums[keyIndex] = temp;
        if (left == index) {
            return nums[left];
        } else if(left > index){
            return fastSort(nums, tempLeft, left - 1, index);
        } else {
            return fastSort(nums, left + 1, keyIndex, index);
        }
    }
}
