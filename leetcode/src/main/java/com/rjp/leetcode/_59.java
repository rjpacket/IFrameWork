package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2019/7/3 13:51
 * email: jinpeng.ren@11bee.com
 */
public class _59 {

    public static void main(String[] args) {
        int[][] ints = generateMatrix(3);
        for (int[] anInt : ints) {
            for (int i : anInt) {
                System.out.print(i + " | ");
            }
            System.out.println("");
        }
    }

    public static int[][] generateMatrix(int n) {
        if(n == 0){
            return null;
        }
        if(n == 1){
            int[][] ints = new int[1][1];
            ints[0][0] = 1;
            return ints;
        }
        int[][] result = new int[n][n];
        int sx = 0;
        int sy = 0;
        int count = 0;
        int pos = 1;
        int total = n * n;
        int d = 1;
        while (pos <= total){
            if(d == 1 && sx < n - count){
                while (sx < n - count){
                    result[sy][sx] = pos++;
                    sx++;
                }
                d++;
                sy++;
                sx--;
            }
            if(d == 2 && sy < n - count){
                while (sy < n - count){
                    result[sy][sx] = pos++;
                    sy++;
                }
                d++;
                sx--;
                sy--;
            }
            if(d == 3 && sx >= count){
                while (sx >= count){
                    result[sy][sx] = pos++;
                    sx--;
                }
                d++;
                sx++;
                sy--;
            }
            if(d == 4 && sy > count){
                while (sy > count){
                    result[sy][sx] = pos++;
                    sy--;
                }
                d++;
                sy++;
                sx++;
            }
            count++;
            d %= 4;
        }
        return result;
    }
}
