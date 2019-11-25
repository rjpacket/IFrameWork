package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2019/11/18 21:11
 * email: jinpeng.ren@11bee.com
 */
public class _1089 {

    public void duplicateZeros(int[] arr) {
        if(arr == null){
            return;
        }
        int length = arr.length;
        for (int i = 0; i < length - 1; i++) {
            if(arr[i] != 0){
                continue;
            }
            int temp = 0;
            for (int j = i + 1; j < length; j++) {
                int local = temp;
                temp = arr[j];
                arr[j] = local;
            }
            i++;
        }
    }
}
