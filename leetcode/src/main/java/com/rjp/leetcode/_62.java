package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2019/7/4 13:21
 * email: jinpeng.ren@11bee.com
 */
public class _62 {

    public static void main(String[] args) {
        System.out.println(uniquePaths(3, 2));
    }

    public static int uniquePaths(int m, int n) {
        int[][] arr = new int[n][m];
        for (int i = 0; i < m; i++) {
            arr[0][i] = 1;
        }
        for (int i = 0; i < n; i++) {
            arr[i][0] = 1;
        }
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                arr[i][j] = arr[i - 1][j] + arr[i][j - 1];
            }
        }
        return arr[n - 1][m - 1];
    }

    public static int uniquePaths1(int m, int n) {
        int[] count = new int[1];
        path(count, 0, 0, m, n);
        return count[0];
    }

    public static void path(int[] count, int x, int y, int m, int n) {
        if (x >= m || y >= n) {
            return;
        }
        if(x == m - 1 && y == n - 1){
            count[0]++;
        }
        path(count, x + 1, y, m, n);
        path(count, x, y + 1, m, n);
    }
}
