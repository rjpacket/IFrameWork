package com.rjp.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * author: jinpeng.ren create at 2019/12/2 20:05
 * email: jinpeng.ren@11bee.com
 */
public class _401 {

    public List<String> readBinaryWatch(int num) {
        List<String> result = new ArrayList<>();
        int[] counts = new int[60];
        for (int i = 0; i < 60; i++) {
            counts[i] = Integer.bitCount(i);
        }
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 60; j++) {
                if (counts[i] + counts[j] == num) {
                    result.add(String.format("%d:%02d", i, j));
                }
            }
        }
        return result;
    }
}
