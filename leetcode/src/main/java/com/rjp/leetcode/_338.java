package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2019/7/18 14:18
 * email: jinpeng.ren@11bee.com
 */
public class _338 {

    public int[] countBits(int num) {
        int[] zero = new int[32];
        int count = 0;
        int[] result = new int[num + 1];
        result[0] = 0;
        for (int i = 1; i <= num; i++) {
            for (int j = 0; j < 32; j++) {
                if(zero[j] == 0){
                    zero[j] = 1;
                    count++;
                    break;
                }else{
                    zero[j] = 0;
                    count--;
                }
            }
            result[i] = count;
        }
        return result;
    }
}
