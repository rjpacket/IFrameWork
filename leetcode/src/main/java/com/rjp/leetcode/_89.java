package com.rjp.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * author: jinpeng.ren create at 2019/7/5 12:54
 * email: jinpeng.ren@11bee.com
 */
public class _89 {

    public static void main(String[] args) {
        List<Integer> list = grayCode(3);
        for (Integer integer : list) {
            System.out.println(integer);
        }
    }

    public static List<Integer> grayCode(int n) {
        List<Integer> result = new ArrayList<>();
        result.add(0);
        for (int i = 0; i < n; i++) {
            int pow = 1 << i;
            int size = result.size() - 1;
            while (size >= 0){
                Integer integer = result.get(size);
                result.add(integer ^ pow);
                size--;
            }
        }
        return result;
    }
}
