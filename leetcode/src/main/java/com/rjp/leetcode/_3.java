package com.rjp.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * author: jinpeng.ren create at 2019/7/3 23:21
 * email: jinpeng.ren@11bee.com
 */
public class _3 {

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("abcabcbb"));
    }

    public static int lengthOfLongestSubstring(String s) {
        if(s == null || s.length() == 0){
            return 0;
        }
        if(s.length() == 1){
            return 1;
        }
        int length = s.length();
        int max = 0;
        int side = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < length; i++) {
            char ch = s.charAt(i);
            if(map.containsKey(ch)){
                int point = map.get(ch);
                if(point >= side){
                    side = point + 1;
                }
            }
            map.put(ch, i);
            max = Math.max(max, i - side + 1);
        }
        return max;
    }
}
