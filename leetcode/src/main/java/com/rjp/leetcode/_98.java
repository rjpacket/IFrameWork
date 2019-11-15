package com.rjp.leetcode;

import java.util.Stack;

/**
 * author: jinpeng.ren create at 2019/11/5 15:20
 * email: jinpeng.ren@11bee.com
 */
public class _98 {

    public boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        }

        Stack<TreeNode> stack = new Stack<>();
        TreeNode pre = null;
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }

            root = stack.pop();
            if (pre != null && pre.val >= root.val) {
                return false;
            }
            pre = root;
            root = root.right;
        }

        return true;
    }

    public boolean isBalanceTree(TreeNode root){
        if(root == null){
            return true;
        }
        if(Math.abs(deepHeight(root.left) - deepHeight(root.right)) > 1){
            return false;
        }
        return isBalanceTree(root.left) && isBalanceTree(root.right);
    }

    private int deepHeight(TreeNode root) {
        if(root == null){
            return 0;
        }
        return 1 + Math.max(deepHeight(root.left), deepHeight(root.right));
    }
}
