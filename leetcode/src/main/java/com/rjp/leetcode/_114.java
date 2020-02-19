package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2019/12/9 10:54
 * email: jinpeng.ren@11bee.com
 */
public class _114 {

    public static void main(String[] args) {
        TreeNode t1 = new TreeNode(1);
        TreeNode t2 = new TreeNode(2);
        TreeNode t3 = new TreeNode(5);
        TreeNode t4 = new TreeNode(3);
        TreeNode t5 = new TreeNode(4);
        TreeNode t6 = new TreeNode(6);
        t1.left = t2;
        t1.right = t3;
        t2.left = t4;
        t2.right = t5;
        t3.right = t6;
        flatten(t1);
        while (t1 != null){
            System.out.println(t1.val);
            t1 = t1.right;
        }
    }

    public static void flatten(TreeNode root) {
        if (root == null) {
            return;
        }
        if (root.left == null) {
            flatten(root.right);
            return;
        }
        if (root.right == null) {
            flatten(root.left);
            root.right = root.left;
            root.left = null;
            return;
        }
        flatten(root.left);
        flatten(root.right);
        TreeNode tmpRight = root.right;
        TreeNode tmpLeft = root.left;
        while (tmpLeft.right != null) {
            tmpLeft = tmpLeft.right;
        }
        tmpLeft.right = tmpRight;
        root.right = root.left;
        root.left = null;
    }
}
