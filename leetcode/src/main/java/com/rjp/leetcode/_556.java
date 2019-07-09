package com.rjp.leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * author: jinpeng.ren create at 2019/6/26 17:35
 * email: jinpeng.ren@11bee.com
 */
public class _556 {

    public static void main(String[] args) {
        System.out.println(nextGreaterElement(198765432));
    }


    public static int nextGreaterElement(int n) {
        String s = String.valueOf(n);
        char[] chars = s.toCharArray();
        int length = chars.length;
        int start = length - 2;
        char check = chars[length - 1];
        boolean find = false;
        while (start >= 0){
            if(chars[start] >= check){
                check = chars[start];
                start--;
            }else{
                find = true;
                char tmp = chars[start + 1];
                int tmpIndex = start + 1;
                for (int i = start + 1; i < length; i++) {
                    if(chars[i] < tmp && chars[i] > chars[start]){
                        tmp = chars[i];
                        tmpIndex = i;
                    }
                }

                char temp = chars[tmpIndex];
                chars[tmpIndex] = chars[start];
                chars[start] = temp;
                break;
            }
        }
        if(!find){
            return -1;
        }

        fast(chars, start + 1, length - 1);
        long parseLong = Long.parseLong(String.valueOf(chars));
        if(parseLong > Integer.MAX_VALUE){
            return -1;
        }
        return (int)parseLong;
    }

    private static void fast(char[] chars, int left, int right) {
        if (left >= right) {
            return;
        }
        int tempLeft = left;
        int tempRight = right;
        char key = chars[tempRight];
        while (left < right) {
            while (left < right && chars[left] <= key) {
                left++;
            }
            while (left < right && chars[right] >= key) {
                right--;
            }
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
        }
        char temp = chars[left];
        chars[left] = key;
        chars[tempRight] = temp;

        fast(chars, tempLeft, left - 1);
        fast(chars, left + 1, tempRight);
    }
}
