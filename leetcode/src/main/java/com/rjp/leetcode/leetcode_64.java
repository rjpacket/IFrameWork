package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2019/7/26 11:27
 * email: jinpeng.ren@11bee.com
 */
public class leetcode_64 {
    public static void main(String[] args) {
        int min = findMin(new int[][]{{1, 3, 1}, {1, 5, 1}, {4, 2, 1}});
        System.out.println(min);
    }

    public static int findMin(int[][] grid) {
        int height = grid.length;
        int width = grid[0].length;
        for (int i = 1; i < width; i++) {
            grid[0][i] += grid[0][i - 1];
        }
        for (int i = 1; i < height; i++) {
            grid[i][0] += grid[i - 1][0];
        }

        int n = height;
        for (int i = 1; i < n; i++) {
            for (int j = i; j < height && i < width; j++) {
                grid[j][i] += Math.min(grid[j - 1][i], grid[j][i - 1]);
            }
            for (int j = i + 1; j < width; j++) {
                grid[i][j] += Math.min(grid[i - 1][j], grid[i][j - 1]);
            }
        }
        return grid[height - 1][width - 1];
    }
}
