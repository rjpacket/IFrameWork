package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2019/12/2 18:10
 * email: jinpeng.ren@11bee.com
 */
public class _991 {

    public int brokenCalc(int X, int Y) {
        if (Y <= X) {
            return X - Y;
        } else {
            if (Y % 2 == 0) {
                return 1 + brokenCalc(X, Y / 2);
            } else {
                return 1 + brokenCalc(X, Y + 1);
            }
        }
    }
}
