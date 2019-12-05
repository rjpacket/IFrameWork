package com.rjp.leetcode;

import java.util.Stack;

/**
 * author: jinpeng.ren create at 2019/12/3 20:55
 * email: jinpeng.ren@11bee.com
 */
public class _671 {

    public static void main(String[] args) {
        TreeNode n1 = new TreeNode(2);
        TreeNode n2 = new TreeNode(2);
        TreeNode n3 = new TreeNode(5);
        TreeNode n4 = new TreeNode(5);
        TreeNode n5 = new TreeNode(7);
        n1.left = n2;
        n1.right = n3;
        n3.left = n4;
        n3.right = n5;
        int secondMinimumValue = findSecondMinimumValue(n1);
        System.out.println(secondMinimumValue);
    }

    public static int findSecondMinimumValue(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {
            return -1;
        }
        Stack<TreeNode> stack = new Stack<>();
        int min = root.val;
        int sMin = root.val;
        while (root != null) {
            if (root.right != null) {
                stack.push(root.right);
            }
            if (root.left != null) {
                stack.push(root.left);
            }
            if (root.val <= min) {
                if (root.val < min) {
                    sMin = min;
                }
                min = root.val;
            } else {
                if (sMin == min) {
                    sMin = root.val;
                } else {
                    sMin = Math.min(sMin, root.val);
                }
            }
            if (!stack.isEmpty()) {
                root = stack.pop();
            } else {
                root = null;
            }
        }
        return sMin == min ? -1 : sMin;
    }
}
