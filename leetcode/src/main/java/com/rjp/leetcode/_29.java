package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2019/7/10 11:22
 * email: jinpeng.ren@11bee.com
 */
public class _29 {

    public static void main(String[] args) {
        System.out.println(divide(-1010369383,-2147483648));
    }

    public static int divide(int dividend, int divisor) {
        if (divisor == 1) {
            return dividend;
        }
        if (divisor == -1) {
            if (dividend == Integer.MIN_VALUE) {
                return Integer.MAX_VALUE;
            }
            return -dividend;
        }
        int flag = -1;
        if ((dividend < 0 && divisor < 0) || (dividend > 0 && divisor > 0)) {
            flag = 1;
        }
        long top = dividend < 0 ? -dividend : dividend;
        if(top == Integer.MIN_VALUE){
            top = -Long.parseLong(String.valueOf(Integer.MIN_VALUE));
        }
        long bot = divisor < 0 ? -divisor : divisor;
        if(bot == Integer.MIN_VALUE){
            bot = -Long.parseLong(String.valueOf(Integer.MIN_VALUE));
        }
        int ans = 0;
        while (top >= bot) {
            ans++;
            top -= bot;
        }
        if(flag < 0){
            return -ans;
        }
        return ans;
    }
}
