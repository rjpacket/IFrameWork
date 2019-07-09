package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2019/6/24 12:38
 * email: jinpeng.ren@11bee.com
 */
public class _5 {

    public static void main(String[] args) {
        System.out.println(longestPalindrome("qabccba"));
    }

    public static String longestPalindrome(String s) {
        if(s == null || s.equals("")){
            return s;
        }
        int start = 0, end = 0;
        int length = s.length();
        for (int i = 0; i < length; i++) {
            int len1 = getCenterLength(s, i, i);
            int len2 = getCenterLength(s, i, i + 1);
            int maxLen = Math.max(len1, len2);
            if(maxLen > (end - start)){
                start = i - (maxLen - 1) / 2;
                end = start + maxLen - 1;
            }
        }
        return s.substring(start, end + 1);
    }

    public static int getCenterLength(String s, int left, int right){
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)){
            left--;
            right++;
        }
        return right - left - 1;
    }
}
