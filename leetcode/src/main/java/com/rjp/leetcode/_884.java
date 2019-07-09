package com.rjp.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author: jinpeng.ren create at 2019/6/21 13:37
 * email: jinpeng.ren@11bee.com
 */
public class _884 {

    public static void main(String[] args) {

    }

    public static String[] charge(String A, String B) {
        String[] arrA = A.split(" ");
        Map<String, Integer> mapA = new HashMap<>();
        for (String aA : arrA) {
            if (!mapA.containsKey(aA)) {
                mapA.put(aA, 1);
            } else {
                Integer integer = mapA.get(aA);
                mapA.put(aA, integer + 1);
            }
        }

        String[] arrB = B.split(" ");
        for (String aB : arrB) {
            if (!mapA.containsKey(aB)) {
                mapA.put(aB, 1);
            } else {
                Integer integer = mapA.get(aB);
                mapA.put(aB, integer + 1);
            }
        }

        List<String> list = new ArrayList<>();
        for (String keyA : mapA.keySet()) {
            if (mapA.get(keyA) == 1) {
                list.add(keyA);
            }
        }

        String[] result = new String[list.size()];
        int index = 0;
        for (String key : list) {
            result[index++] = key;
        }
        return result;
    }
}
