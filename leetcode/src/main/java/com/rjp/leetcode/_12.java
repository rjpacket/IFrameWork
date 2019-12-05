package com.rjp.leetcode;

import android.util.ArrayMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * author: jinpeng.ren create at 2019/11/26 10:32
 * email: jinpeng.ren@11bee.com
 */
public class _12 {

    public static void main(String[] args) {
        String s = intToRoman(3);
        System.out.println(s);
    }

    public static String intToRoman(int num) {
        String[] values = new String[]{"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] keys = new int[]{1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        StringBuilder sb = new StringBuilder();
        int size = keys.length;
        for (int i = 0; i < size; i++) {
            if (num >= keys[i]) {
                sb.append(values[i]);
                num -= keys[i];
                i--;
            }
        }
        return sb.toString();
    }
}
