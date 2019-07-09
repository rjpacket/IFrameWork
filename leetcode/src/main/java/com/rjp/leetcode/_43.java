package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2019/6/26 13:40
 * email: jinpeng.ren@11bee.com
 */
public class _43 {

    public static void main(String[] args) {
        System.out.println(multiply("9", "9"));
    }

    public static String multiply(String num1, String num2) {
        if(num1 == null || num1.length() == 0 || num2 == null || num2.length() == 0){
            return null;
        }
        if("0".equals(num1) || "0".equals(num2)){
            return "0";
        }
        int len1 = num1.length();
        int len2 = num2.length();
        int[] result = new int[len1 + len2 - 1];
        for (int i = 0; i < len1; i++) {
            for (int j = 0; j < len2; j++) {
                result[i + j] += (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
            }
        }
        int length = result.length;
        int add = 0;
        for (int i = length - 1; i >= 0; i--) {
            int temp = (result[i] + add) % 10;
            add = (result[i] + add) / 10;
            result[i] = temp;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(result[i]);
        }
        if(add != 0){
            return add + sb.toString();
        }
        return sb.toString();
    }
}
