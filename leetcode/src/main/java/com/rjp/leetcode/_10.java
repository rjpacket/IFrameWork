package com.rjp.leetcode;

import android.util.Log;

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

    /**
     * @param s
     *
     *    0cab
     *   01000
     *   c0100
     *   a0010
     *   *0111
     *   b0001
     * @param p
     */
    public static boolean isMatchBest(String s, String p) {
        int left = p.length();
        int top = s.length();
        if (p.charAt(0) == '*') {
            return false;
        }
        boolean[][] result = new boolean[top + 1][left + 1];
        result[0][0] = true;
        for (int i = 1; i <= top; i++) {
            for (int j = 1; j <= left; j++) {
                if (p.charAt(j - 1) == '*') {
                    result[i][j] = result[i][j - 2];
                    if (matches(s, p, i, j - 1)) {
                        result[i][j] = result[i][j] || result[i - 1][j];
                    }
                } else {
                    if (matches(s, p, i, j)) {
                        result[i][j] = result[i - 1][j - 1];
                    }
                }
            }
        }
        return result[top][left];
    }

    private static boolean matches(String s, String p, int i, int j) {
        if (p.charAt(j - 1) == '.') {
            return true;
        }
        return s.charAt(i - 1) == p.charAt(j - 1);
    }
}
