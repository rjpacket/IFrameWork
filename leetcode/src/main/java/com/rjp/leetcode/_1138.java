package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2019/7/30 10:42
 * email: jinpeng.ren@11bee.com
 */
public class _1138 {

    public static void main(String[] args) {
        String leet = alphabetBoardPath("zz");
        System.out.println(leet);
    }

    public static String alphabetBoardPath(String target) {
        Character[][] boards = new Character[6][];
        boards[0] = new Character[]{'a', 'b', 'c', 'd', 'e'};
        boards[1] = new Character[]{'f', 'g', 'h', 'i', 'j'};
        boards[2] = new Character[]{'k', 'l', 'm', 'n', 'o'};
        boards[3] = new Character[]{'p', 'q', 'r', 's', 't'};
        boards[4] = new Character[]{'u', 'v', 'w', 'x', 'y'};
        boards[5] = new Character[]{'z'};

        int px = 0;
        int py = 0;
        int x = -1;
        int y = -1;
        StringBuilder sb = new StringBuilder();
        char[] words = target.toCharArray();
        for (char word : words) {
            if(word == 'z'){
                y = 5;
            }else if(word >= 'u'){
                y = 4;
            }else if(word >= 'p'){
                y = 3;
            }else if(word >= 'k'){
                y = 2;
            }else if(word >= 'f'){
                y = 1;
            }else if(word >= 'a'){
                y = 0;
            }
            int length = boards[y].length;
            for (int i = 0; i < length; i++) {
                if(boards[y][i] == word){
                    x = i;
                    break;
                }
            }

            //如果连续zz的时候，特殊处理
            if(y == 5 && (px == 0 && py == 5)){
                sb.append('!');
                continue;
            }

            //如果当前word是'z'的时候，特殊处理
            int tempX = -1;
            int tempY = -1;
            if(y == 5){
                tempX = 0;
                tempY = 5;

                x = 0;
                y = 4;
            }
            //如果之前的word是'z'的时候，特殊处理
            if(px == 0 && py == 5){
                sb.append('U');
                px = 0;
                py = 4;
            }


            if(y > py){
                int abs = y - py;
                for (int i = 0; i < abs; i++) {
                    sb.append('D');
                }
            }
            if(y < py){
                int abs = py - y;
                for (int i = 0; i < abs; i++) {
                    sb.append('U');
                }
            }
            if(x > px){
                int abs = x - px;
                for (int i = 0; i < abs; i++) {
                    sb.append('R');
                }
            }
            if(x < px){
                int abs = px - x;
                for (int i = 0; i < abs; i++) {
                    sb.append('L');
                }
            }

            if(tempX != -1){
                sb.append('D');
            }
            sb.append('!');
            px = tempX == -1 ? x : tempX;
            py = tempY == -1 ? y : tempY;
        }

        return sb.toString();
    }
}
















