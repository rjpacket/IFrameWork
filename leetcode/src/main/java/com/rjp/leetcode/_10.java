package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2019/7/9 18:21
 * email: jinpeng.ren@11bee.com
 */
public class _10 {

    public static void main(String[] args) {
        boolean aa = isMatch("aa", "a*");
        System.out.println(aa);
    }

    public static boolean isMatch(String s, String p) {
        if (p == null || "".equals(p)) {
            return s == null || "".equals(s);
        }
        boolean firstMatch = !s.isEmpty() && (p.charAt(0) == '.' || p.charAt(0) == s.charAt(0));
        if(p.length() >= 2 && p.charAt(1) == '*'){
            return isMatch(s, p.substring(2)) || (firstMatch && isMatch(s.substring(1), p));
        }
        return firstMatch && isMatch(s.substring(1), p.substring(1));
    }
}
