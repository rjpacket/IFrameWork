package com.rjp.leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * author: jinpeng.ren create at 2019/7/24 10:23
 * email: jinpeng.ren@11bee.com
 */
public class _102 {

    public static void main(String[] args) {
        TreeNode n1 = new TreeNode(3);
        TreeNode n2 = new TreeNode(2);
        TreeNode n3 = new TreeNode(5);
        TreeNode n4 = new TreeNode(7);
        TreeNode n5 = new TreeNode(9);
        n1.left = n2;
        n1.right = n3;
        n3.left = n4;
        n3.right = n5;
        List<List<Integer>> lists = levelOrder(n1);
        for (List<Integer> list : lists) {
            for (Integer integer : list) {
                System.out.print(integer + " | ");
            }
            System.out.println(" ");
        }
    }

    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if(root == null){
            return result;
        }
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.addFirst(root);
        int count = 1;
        while (!queue.isEmpty()) {
            List<Integer> list = new ArrayList<>();
            int nextCount = 0;
            while (count > 0) {
                TreeNode treeNode = queue.removeLast();
                if (treeNode.left != null) {
                    queue.addFirst(treeNode.left);
                    nextCount++;
                }
                if (treeNode.right != null) {
                    queue.addFirst(treeNode.right);
                    nextCount++;
                }
                list.add(treeNode.val);
                count--;
            }
            count = nextCount;
            result.add(list);
        }
        return result;
    }
}
