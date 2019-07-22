package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2019/7/11 11:15
 * email: jinpeng.ren@11bee.com
 */
public class _79 {

    public static void main(String[] args) {
        char[][] a = new char[][]{{'a', 'a', 'a', 'a'}, {'a', 'a', 'a', 'a'}, {'a', 'a', 'a', 'a'}};
//        char[][] a = new char[][]{{'a', 'b', 'c', 'e'}, {'s', 'f', 'c', 's'}, {'a', 'd', 'e', 'e'}};
//        char[][] a = new char[][]{{'a', 'b'}, {'c', 'd'}};
        System.out.println(exist(a, "aaaaaaaaaaaaa"));
    }

    public static boolean exist(char[][] board, String word) {
        if (word == null || "".equals(word)) {
            return false;
        }
        if (board == null || board.length == 0) {
            return false;
        }
        int x = board.length;
        int y = board[0].length;
        boolean result = false;
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                int[][] flag = new int[x][y];
                result = result || exist(board, i, j, word, 0, flag);
            }
        }
        return result;
    }

    public static boolean exist(char[][] board, int x, int y, String word, int index, int[][] flag) {
        if (x < 0 || y < 0 || x >= board.length || y >= board[0].length || flag[x][y] == 1) {
            return false;
        }
        flag[x][y] = 1;
        if (board[x][y] == word.charAt(index)) {
            if (index == word.length() - 1) {
                return true;
            } else {
                boolean b1 = exist(board, x + 1, y, word, index + 1, flag);
                if(b1) return true;
                if(x < board.length - 1) {
                    flag[x + 1][y] = 0;
                }
                boolean b2 = exist(board, x - 1, y, word, index + 1, flag);
                if(b2) return true;
                if(x > 0) {
                    flag[x - 1][y] = 0;
                }
                boolean b3 = exist(board, x, y + 1, word, index + 1, flag);
                if(b3) return true;
                if(y < board[0].length - 1) {
                    flag[x][y + 1] = 0;
                }
                boolean b4 = exist(board, x, y - 1, word, index + 1, flag);
                if(b4) return true;
                if(y > 0) {
                    flag[x][y - 1] = 0;
                }
            }
        }
        return false;
    }
}
