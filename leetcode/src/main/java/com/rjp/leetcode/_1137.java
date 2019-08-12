package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2019/7/30 10:32
 * email: jinpeng.ren@11bee.com
 */
public class _1137 {

    public static void main(String[] args) {
        int tribonacci = tribonacci(4);
        System.out.println(tribonacci);
    }

    public static int tribonacci(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        int[] array = new int[n + 1];
        array[0] = 0;
        array[1] = 1;
        array[2] = 1;
        for (int i = 3; i <= n; i++) {
            array[i] = array[i - 1] + array[i - 2] + array[i - 3];
        }
        return array[n];
    }
}
