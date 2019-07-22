package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2019/7/10 15:15
 * email: jinpeng.ren@11bee.com
 */
public class _42 {

    public static void main(String[] args) {
//        System.out.println(trap(new int[]{4,2,0,3,2,5}));
        System.out.println(trap(new int[]{2,0,2}));
    }

    public static int trap(int[] height) {
        int[] ans = new int[1];
        trapArea(height, 0, height.length - 1, ans);
        return ans[0];
    }

    private static void trapArea(int[] height, int start, int end, int[] ans) {
        if (start >= end) {
            return;
        }
        int midMaxIndex = getMaxIndex(height, start, end);

        int leftIndex = getMaxIndex(height, start, midMaxIndex - 1);
        if(leftIndex >= start && leftIndex < midMaxIndex) {
            int sum = 0;
            for (int i = leftIndex + 1; i < midMaxIndex; i++) {
                sum += Math.min(height[leftIndex], height[i]);
            }
            ans[0] += height[leftIndex] * (midMaxIndex - leftIndex - 1) - sum;
            trapArea(height, start, leftIndex, ans);
        }

        int rightIndex = getMaxIndex(height, midMaxIndex + 1, end);
        if(rightIndex > midMaxIndex && rightIndex <= end) {
            int sum = 0;
            for (int i = midMaxIndex + 1; i < rightIndex; i++) {
                sum += Math.min(height[rightIndex], height[i]);
            }
            ans[0] += height[rightIndex] * (rightIndex - midMaxIndex - 1) - sum;
            trapArea(height, rightIndex, end, ans);
        }

    }

    private static int getMaxIndex(int[] height, int start, int end) {
        int index = start;
        for (int i = start + 1; i <= end; i++) {
            if (height[i] > height[index]) {
                index = i;
            }
        }
        return index;
    }

}
