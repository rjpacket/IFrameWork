package com.rjp.fastframework.scroll_view;

/**
 * author: jinpeng.ren create at 2020/10/15 14:51
 */
public class JavaMethod {

    public static void main(String[] args) {
        System.out.println(Z("abcdefgh".toCharArray(), 3));
    }

    public static String Z(char[] chars, int line) {
        int perCount = 2 * line - 1;
        int length = chars.length;
        int width = length / perCount + 1;
        int[][] arr = new int[line][width * (line - 1)];
        int index = 1;
        int x = 0;
        int y = 0;
        int direction = 1;
        for (int i = 0; i < length; i++) {
            if (x == 0) {
                direction = 1;
            } else if (x == line - 1) {
                direction = -1;
            }
            arr[x][y] = index;
            index++;
            x += direction;
            if (direction == -1) {
                y += 1;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < line; i++) {
            int length1 = arr[i].length;
            for (int j = 0; j < length1; j++) {
                if (arr[i][j] != 0) {
                    sb.append(chars[arr[i][j] - 1]);
                }
            }
        }
        return sb.toString();
    }
}
