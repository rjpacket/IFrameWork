package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2019/7/11 16:16
 * email: jinpeng.ren@11bee.com
 */
public class _96 {

    public static void main(String[] args) {
        System.out.println(numTrees(3));
    }

    public static int numTrees(int n) {
        int[] arr = new int[n];
        int[] cache = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i + 1;
        }
        return numTrees(arr, 0, n - 1, cache);
    }

    public static int numTrees(int[] array, int start, int end, int[] cache) {
        if(end <= 0 || start >= end){
            return 1;
        }
        if(cache[end - start] != 0){
            return cache[end - start];
        }
        int count = 0;
        for (int i = start; i <= end; i++) {
            int i1 = numTrees(array, start, i - 1, cache);
            if(i - 1 - start >= 0) {
                cache[i - 1 - start] = i1;
            }
            int i2 = numTrees(array, i + 1, end, cache);
            if(end - i - 1 >= 0) {
                cache[end - i - 1] = i2;
            }
            count += i1 * i2;
        }
        return count;
    }
}
