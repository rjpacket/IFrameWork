package com.rjp.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * author: jinpeng.ren create at 2019/6/28 21:18
 * email: jinpeng.ren@11bee.com
 */
public class _54 {

    public static void main(String[] args) {
        int[][] a = new int[3][4];
        a[0] = new int[]{1, 2, 3, 4};
        a[1] = new int[]{5, 6, 7, 8};
        a[2] = new int[]{9, 10, 11, 12};
        List<Integer> list = spiralOrder(a);
        for (Integer integer : list) {
            System.out.println(integer);
        }
    }

    public static List<Integer> spiralOrder(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return null;
        }
        List<Integer> list = new ArrayList<>();
        int x = matrix[0].length;
        int y = matrix.length;
        int sx = 0;
        int sy = 0;
        int d = 1;
        int count = 0;
        while (count < (x + 1) / 2) {
            if (d == 1 && sx < x - count) {
                while (sx < x - count) {
                    list.add(matrix[sy][sx++]);
                }
                sx--;
                sy++;
                d++;
            }
            if (d == 2 && sy < y - count) {
                while (sy < y - count) {
                    list.add(matrix[sy++][sx]);
                }
                sy--;
                sx--;
                d++;
            }
            if (d == 3 && sx >= count) {
                while (sx >= count) {
                    list.add(matrix[sy][sx--]);
                }
                sx++;
                sy--;
                d++;
            }
            if (d == 4 && sy > count) {
                while (sy > count) {
                    list.add(matrix[sy--][sx]);
                }
                sx++;
                sy++;
                d++;
            }
            d %= 4;
            count++;
        }
        return list;
    }
}
