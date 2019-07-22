package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2019/7/9 17:51
 * email: jinpeng.ren@11bee.com
 */
public class _557 {

    public static void main(String[] args) {
        System.out.println(reverseWords("Let's take LeetCode contest"));
    }

    public static String reverseWords(String s) {
        if(s == null){
            return null;
        }
        if("".equals(s)){
            return "";
        }
        char[] chars = s.toCharArray();
        int length = chars.length;
        int start = 0;
        int end = 0;
        while (start < length){
            while (start < length && chars[start] == ' '){
                start++;
            }
            end = start;
            while (end < length && chars[end] != ' '){
                end++;
            }
            reverse(chars, start, end - 1);
            start = end;
        }
        return String.valueOf(chars);
    }

    private static void reverse(char[] chars, int start, int end) {
        while (start < end){
            char temp = chars[start];
            chars[start] = chars[end];
            chars[end] = temp;

            start++;
            end--;
        }
    }
}
