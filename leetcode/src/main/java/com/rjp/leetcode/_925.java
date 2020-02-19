package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2019/12/5 15:41
 * email: jinpeng.ren@11bee.com
 */
public class _925 {

    public static void main(String[] args) {
        System.out.println(isLongPressedName("al", "aall"));
    }

    public static boolean isLongPressedName(String name, String typed) {
        int nLen = name.length();
        int tLen = typed.length();
        if (tLen < nLen) {
            return false;
        }
        int nIndex = 0;
        int tIndex = 0;
        while (nIndex < nLen && tIndex < tLen) {
            if (name.charAt(nIndex) == typed.charAt(tIndex)) {
                nIndex++;
                tIndex++;
            } else {
                tIndex++;
            }
        }
        while (tIndex < tLen) {
            if (name.charAt(nIndex - 1) == typed.charAt(tIndex)) {
                tIndex++;
            }
        }
        return nIndex == nLen && tIndex == tLen;
    }
}
