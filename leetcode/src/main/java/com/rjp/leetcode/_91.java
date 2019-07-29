package com.rjp.leetcode;

import java.util.HashMap;

/**
 * author: jinpeng.ren create at 2019/7/26 12:29
 * email: jinpeng.ren@11bee.com
 */
public class _91 {

    public static void main(String[] args) {
        int i = numDecodings("1024");
        System.out.println(i);
//        int[] ints = {2, 8, 6, 5, 3, 1, 4, 7, 9};
//        fast(ints, 0, ints.length - 1);
//        for (int anInt : ints) {
//            System.out.print(anInt + " | ");
//        }
    }

    public static int numDecodings(String s) {
        if (s == null || s.length() == 0 || s.startsWith("0")) {
            return 0;
        }
        int length = s.length();
        int[] result = new int[length + 1];
        result[0] = 1;
        result[1] = 1;
        for (int i = 1; i < length; i++) {
            if (s.charAt(i - 1) != '0') {
                int num = (s.charAt(i - 1) - '0') * 10 + (s.charAt(i) - '0');
                if (num >= 1 && num < 27) {
                    result[i + 1] = (s.charAt(i) == '0' ? result[i - 1] : (result[i - 1] + result[i]));
                } else if (s.charAt(i) != '0') {
                    result[i + 1] = result[i];
                } else {
                    return 0;
                }
            } else if (s.charAt(i) != '0') {
                result[i + 1] = result[i];
            } else {
                return 0;
            }
        }
        return result[length];
    }

    public static int numDecodings1(String s) {
        if (s == null || s.length() == 0 || s.startsWith("0")) {
            return 0;
        }
        int[] result = new int[1];
        step(s, 0, 1, result);
        step(s, 0, 2, result);
        return result[0];
    }

    public static void step(String s, int start, int step, int[] count) {
        int length = s.length();
        int end = start + step;
        if (end > length) {
            return;
        }
        String substring = s.substring(start, end);
        if (substring.startsWith("0")) {
            return;
        }
        int number = Integer.parseInt(substring);
        if (number > 26) {
            return;
        }
        if (end == length) {
            count[0] += 1;
        }
        step(s, end, 1, count);
        step(s, end, 2, count);
    }

    public static void fast(int[] array, int start, int end) {
        if (start >= end) {
            return;
        }
        int left = start;
        int right = end;
        int key = right;
        while (left < right) {
            while (left < right && array[left] < array[key]) {
                left++;
            }
            while (left < right && array[right] > array[key]) {
                right--;
            }
            int temp = array[left];
            array[left] = array[right];
            array[right] = temp;
        }
        int temp = array[left];
        array[left] = array[key];
        array[key] = temp;

        fast(array, start, left - 1);
        fast(array, left + 1, end);
    }
}
