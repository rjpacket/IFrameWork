package com.rjp.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * author: jinpeng.ren create at 2019/6/28 16:14
 * email: jinpeng.ren@11bee.com
 */
public class _13 {

    public static void main(String[] args) {
        System.out.println(romanToInt("MCMXCIV"));
    }

    public static int romanToInt(String s) {
        Map<Character, Integer> map = new HashMap<>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);
        int length = s.length();
        int sum = 0;
        int start = 0;
        while (start < length - 1) {
            sum += map.get(s.charAt(start));
            String sub = s.substring(start, start + 2);
            if ("IV".equals(sub) || "IX".equals(sub)) {
                sum -= 2;
            } else if ("XL".equals(sub) || "XC".equals(sub)) {
                sum -= 20;
            } else if ("CD".equals(sub) || "CM".equals(sub)) {
                sum -= 200;
            }
            start++;
        }
        sum += map.get(s.charAt(start));
        return sum;
    }
}















