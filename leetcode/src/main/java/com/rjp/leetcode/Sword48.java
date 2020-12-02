package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2020/12/2 17:23
 */
public class Sword48 {

    public static int findMaxStr(String source) {
        char[] chars = source.toCharArray();
        int length = chars.length;
        int max = 0;
        int cur = 0;
        int[] map = new int[26];
        for (int i = 0; i < length; i++) {
            if (map[chars[i] - 'a'] == 0) {
                map[chars[i] - 'a'] = i + 1;
                cur++;
                max = Math.max(cur, max);
            } else {
                i = map[chars[i] - 'a'];
                cur = 0;
                map = new int[26];
            }
        }
        return max;
    }
}
