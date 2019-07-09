package com.rjp.leetcode;

import java.util.function.DoubleUnaryOperator;

/**
 * author: jinpeng.ren create at 2019/6/24 11:10
 * email: jinpeng.ren@11bee.com
 */
public class _906 {

    public static void main(String[] args) {
        System.out.println(superpalindromesInRange("1", "2"));
    }

    public static int superpalindromesInRange(String L, String R) {
        int count = 0;
        long left = (long) Math.ceil(Math.sqrt(Double.parseDouble(L)));
        long right = (long) Math.floor(Math.sqrt(Double.parseDouble(R)));
        long magic = (long) Math.ceil(Math.pow(10, 9));
        for (long i = left; i <= right; i++) {
            if(isPalindromes(String.valueOf(i)) && isPalindromes(String.valueOf(i * i))){
                count++;
            }
        }
        return count;
    }

    public static boolean isPalindromes(String num) {
        char[] chars = num.toCharArray();
        int length = chars.length;
        for (int i = 0; i < length / 2; i++) {
            char temp = chars[i];
            chars[i] = chars[length - 1 - i];
            chars[length - 1 - i] = temp;
        }
        return String.valueOf(chars).equals(num);
    }
}
