package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2019/11/19 12:10
 * email: jinpeng.ren@11bee.com
 */
public class _440 {

    public static void main(String[] args) {
        int kthNumber = findKthNumber(13, 2);
    }

    public static int findKthNumber(int n, int k) {
        String nStr = String.valueOf(n);
        int length = nStr.length();
        int quan = (int) Math.pow(10, length);
        int[] array = new int[n];
        for (int i = 1; i <= n; i++) {
            array[i - 1] = i * quan;
        }
        fastSort(array, 0, n - 1);
        return array[k - 1] / quan;
    }

    private static void fastSort(int[] array, int start, int end) {
        if (start >= end) {
            return;
        }
        int tempStart = start;
        int tempEnd = end;
        int key = array[end];
        while (start < end) {
            while (start < end && array[start] <= key) {
                start++;
            }
            while (start < end && array[end] >= key) {
                end--;
            }
            int temp = array[start];
            array[start] = array[end];
            array[end] = temp;
        }
        array[tempEnd] = array[start];
        array[start] = key;

        fastSort(array, tempStart, start - 1);
        fastSort(array, start + 1, tempEnd);
    }
}
