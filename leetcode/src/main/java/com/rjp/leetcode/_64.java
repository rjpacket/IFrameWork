package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2019/7/10 17:53
 * email: jinpeng.ren@11bee.com
 */
public class _64 {

    public static void main(String[] args) {
        int[][] ints = {{1, 2, 5}, {3, 2, 1}};
        int i = minPathSum(ints);
        System.out.println(i);
    }

    public static int minPathSum(int[][] grid) {
        int height = grid.length;
        int length = grid[0].length;
        if(height == 1){
            int sum = 0;
            for (int i = 0; i < length; i++) {
                sum += grid[0][i];
            }
            return sum;
        }
        if(length == 1){
            int sum = 0;
            for (int i = 0; i < height; i++) {
                sum += grid[i][0];
            }
            return sum;
        }

        int[][] min = new int[height][length];
        min[height - 1][length - 1] = grid[height - 1][length - 1];
        for (int i = height - 2; i >= 0; i--) {
            min[i][length - 1] = grid[i][length - 1] + min[i + 1][length - 1];
        }
        for (int i = length - 2; i >= 0; i--) {
            min[height - 1][i] = grid[height - 1][i] + min[height - 1][i + 1];
        }
        for (int i = height - 2; i >= 0; i--) {
            for (int j = length - 2; j >= 0; j--) {
                min[i][j] = Math.min(min[i + 1][j], min[i][j + 1]) + grid[i][j];
            }
        }
        return min[0][0];
    }
}
