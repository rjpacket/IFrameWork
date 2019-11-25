package com.rjp.leetcode;

/**
 * 给定二叉树根结点 root ，此外树的每个结点的值要么是 0，要么是 1。
 *
 * 返回移除了所有不包含 1 的子树的原二叉树。
 *
 * ( 节点 X 的子树为 X 本身，以及所有 X 的后代。)
 *
 * 示例1:
 * 输入: [1,null,0,0,1]
 * 输出: [1,null,0,null,1]
 *
 * 解释:
 * 只有红色节点满足条件“所有不包含 1 的子树”。
 * 右图为返回的答案。
 *
 *
 * 示例2:
 * 输入: [1,0,1,0,0,0,1]
 * 输出: [1,null,1,null,1]
 *
 *
 *
 * 示例3:
 * 输入: [1,1,0,1,1,0,1,0]
 * 输出: [1,1,0,1,1,null,1]
 *
 *
 *
 * 说明:
 *
 * 给定的二叉树最多有 100 个节点。
 * 每个节点的值只会为 0 或 1 。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/binary-tree-pruning
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * author: jinpeng.ren create at 2019/11/15 17:42
 * email: jinpeng.ren@11bee.com
 */
public class _814 {

    public TreeNode pruneTree(TreeNode root) {
        if(root == null || isCanPrune(root)){
            return null;
        }
        if(isCanPrune(root.left)){
            root.left = null;
        }else{
            root.left = pruneTree(root.left);
        }
        if(isCanPrune(root.right)){
            root.right = null;
        }else{
            root.right = pruneTree(root.right);
        }
        return root;
    }

    public boolean isCanPrune(TreeNode root){
        if(root == null){
            return true;
        }
        if(root.val != 0){
            return false;
        }
        return isCanPrune(root.left) && isCanPrune(root.right);
    }
}
