package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2019/7/12 17:11
 * email: jinpeng.ren@11bee.com
 */
public class _48 {

    public static void main(String[] args) {
        int[][] a = new int[][]{{1,2,3}, {4,5,6}, {7,8,9}};
        rotate(a);
        System.out.print(" —— | — | ——\n");
        for (int[] ints : a) {
            System.out.print("| ");
            for (int anInt : ints) {
                System.out.print(anInt + " | ");
            }
            System.out.print("\n —— | — | ——\n");
        }
    }

    public static void rotate(int[][] matrix) {
        if(matrix == null){
            return;
        }
        int x = matrix.length;
        int y = matrix[0].length;
        int count = (x + 1) / 2;
        for (int i = 0; i < count; i++) {
            int xi1 = x - i - 1;
            for (int j = i; j < xi1; j++) {
                int temp = matrix[i][j];
                int xj1 = x - j - 1;
                matrix[i][j] = matrix[xj1][i];
                matrix[xj1][i] = matrix[xi1][xj1];
                matrix[xi1][xj1] = matrix[j][xi1];
                matrix[j][xi1] = temp;
            }
        }
    }
}
