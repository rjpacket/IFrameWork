package com.rjp.leetcode;

import java.util.HashMap;

/**
 *
 * author: jinpeng.ren create at 2019/7/11 15:24
 * email: jinpeng.ren@11bee.com
 */

/**
 * 镜像二叉树
 *
 *
 */
public class _101 {

    public static void main(String[] args) {
        HashMap[] maps = new HashMap[10];
    }

    public static boolean isSymmetric(TreeNode root) {
        if(root == null){
            return true;
        }
        return check(root.left, root.right);
    }

    private static boolean check(TreeNode left, TreeNode right) {
        if(left == null && right == null){
            return true;
        }else if(left == null || right == null){
            return false;
        }
        if(left.val != right.val){
            return false;
        }
        return check(left.left, right.right) && check(left.right, right.left);
    }
}
