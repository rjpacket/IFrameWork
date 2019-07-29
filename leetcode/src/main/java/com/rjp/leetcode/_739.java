package com.rjp.leetcode;

import java.util.Stack;

/**
 * author: jinpeng.ren create at 2019/7/23 13:32
 * email: jinpeng.ren@11bee.com
 */
public class _739 {

    public static void main(String[] args) {
        int[] array = new int[]{73, 74, 75, 71, 69, 72, 76, 73};

        int[] ints = dailyTemperatures(array);
        for (int anInt : ints) {
            System.out.println(anInt);
        }
    }

    public static int[] dailyTemperatures(int[] T) {
        if (T == null) {
            return null;
        }
        int length = T.length;
        int[] result = new int[length];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < length; i++) {
            while (!stack.isEmpty() && T[i] > T[stack.peek()]) {
                Integer pop = stack.pop();
                result[pop] = i - pop;
            }
            stack.push(i);
        }
        return result;
    }
}
