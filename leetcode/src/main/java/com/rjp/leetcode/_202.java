package com.rjp.leetcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * author: jinpeng.ren create at 2019/12/4 21:25
 * email: jinpeng.ren@11bee.com
 */
public class _202 {

    public static void main(String[] args) {
        int lock = openLock(new String[]{"0010"}, "0030");
        System.out.println(lock);
    }

    public static boolean isHappy(int n) {
        Set<Integer> set = new HashSet<>();
        while (!set.contains(n)) {
            set.add(n);
            int sum = 0;
            while (n != 0) {
                int a = n % 10;
                sum += (a * a);
                n = n / 10;
            }
            n = sum;
        }
        return n == 1;
    }

    public static int openLock(String[] deadends, String target) {
        if(find(deadends, "0000")) {
            return -1;
        }

        // 获取target 每位分别加减1的
        List<String> options = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            char[] chars = target.toCharArray();
            char temp = chars[i];
            chars[i] = (char)((temp - 48 + 1) % 10 + 48);
            String t1 = new String(chars);
            if(!find(deadends, t1)) {
                options.add(t1);
            }

            chars[i] = (char)((temp - 48 + 9) % 10 + 48);
            String t2 = new String(chars);
            if(!find(deadends, t2)) {
                options.add(t2);
            }
        }

        if(options.isEmpty()) {
            return -1;
        }

        int step = 0;
        for(String option : options) {
            char[] chars = option.toCharArray();
            int cur = 1;
            for(int j = 0; j < 4; j++) {
                int t = chars[j] - 48;
                if(t > 5) {
                    cur += 10 - t;
                } else {
                    cur += t;
                }
            }
            if(step == 0 || cur < step) {
                step = cur;
            }
        }

        return step;
    }

    public static boolean find(String[] deadends, String target) {
        for(String deadend : deadends) {
            if(deadend.equals(target)) {
                return true;
            }
        }
        return false;
    }
}
