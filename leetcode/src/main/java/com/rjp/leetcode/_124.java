package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2019/7/8 11:16
 * email: jinpeng.ren@11bee.com
 */
public class _124 {

    //[1,-2,-3,1,3,-2,null,-1]
    public static void main(String[] args) {
//        TreeNode n1 = new TreeNode(-10);
//        TreeNode n2 = new TreeNode(9);
//        n1.left = n2;
//        TreeNode n3 = new TreeNode(20);
//        n1.right = n3;
//        n3.left = new TreeNode(15);
//        n3.right = new TreeNode(7);
//        System.out.println(maxPathSum(n1));

        TreeNode n1 = new TreeNode(-2);
//        TreeNode n2 = new TreeNode(-1);
//        n1.left = n2;
//        TreeNode n4 = new TreeNode(1);
//        n2.left = n4;
//        n4.left = new TreeNode(-1);
//        n2.right = new TreeNode(3);
        TreeNode n3 = new TreeNode(-3);
        n1.right = n3;
//        n3.left = new TreeNode(-2);
        System.out.println(maxPathSum(n1));
    }

    public static int maxPathSum(TreeNode root) {
        int[] resultArr = new int[1];
        resultArr[0] = Integer.MIN_VALUE;
        max(root, resultArr);
        return resultArr[0];
    }

    public static int max(TreeNode root, int[] resultArr){
        if(root == null){
            return 0;
        }

        int left = Math.max(0, max(root.left, resultArr));
        int right = Math.max(0, max(root.right, resultArr));
        resultArr[0] = Math.max(resultArr[0], root.val + left + right);
        return Math.max(left, right) + root.val;
    }
}
