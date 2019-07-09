package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2019/6/25 12:53
 * email: jinpeng.ren@11bee.com
 */
public class _8 {

    public static void main(String[] args) {
        System.out.println(myAtoi("+2 234"));
    }

    public static int myAtoi(String str) {
        int length = str.length();
        int flag = 1;
        boolean isJudge = false;
        long sum = 0;
        for (int i = 0; i < length; i++) {
            if(str.charAt(i) == ' '){
                if(isJudge){
                    return (int)sum * flag;
                }
                continue;
            }
            if(str.charAt(i) == '+' || str.charAt(i) == '-'){
                if(isJudge){
                    return 0;
                }else{
                    if(str.charAt(i) == '-'){
                        flag = -1;
                    }
                    isJudge = true;
                    continue;
                }
            }
            if(isNumber(str.charAt(i))){
                sum = sum * 10 + str.charAt(i) - '0';
                if(sum * flag > Integer.MAX_VALUE){
                    return Integer.MAX_VALUE;
                }else if(sum * flag < Integer.MIN_VALUE){
                    return Integer.MIN_VALUE;
                }
            }else{
                return (int)sum * flag;
            }
            isJudge = true;
        }
        return (int)sum * flag;
    }

    public static boolean isNumber(char ch) {
        return ch == '0' || ch == '1' ||
                ch == '2' || ch == '3' ||
                ch == '4' || ch == '5' ||
                ch == '6' || ch == '7' ||
                ch == '8' || ch == '9';
    }
}
