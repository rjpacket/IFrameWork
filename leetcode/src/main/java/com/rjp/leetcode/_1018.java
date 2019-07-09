package com.rjp.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * author: jinpeng.ren create at 2019/6/24 10:53
 * email: jinpeng.ren@11bee.com
 */
public class _1018 {

    public static void main(String[] args) {
        int[] a = new int[]{1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1};
        List<Boolean> booleans = prefixesDivBy5(a);
        for (Boolean aBoolean : booleans) {
            System.out.println(aBoolean);
        }
    }

    public static List<Boolean> prefixesDivBy5(int[] A) {
        int length = A.length;
        int result = 0;
        List<Boolean> list = new ArrayList();
        for (int i = 0; i < length; i++) {
            result = result * 2 + A[i];
            list.add(result % 5 == 0);
            result %= 5;
        }
        return list;
    }
}
