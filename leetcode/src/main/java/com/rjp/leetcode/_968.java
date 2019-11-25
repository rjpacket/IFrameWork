package com.rjp.leetcode;

/**
 * 给定一个二叉树，我们在树的节点上安装摄像头。
 * <p>
 * 节点上的每个摄影头都可以监视其父对象、自身及其直接子对象。
 * <p>
 * 计算监控树的所有节点所需的最小摄像头数量。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * <p>
 * <p>
 * 输入：[0,0,null,0,0]
 * 输出：1
 * 解释：如图所示，一台摄像头足以监控所有节点。
 * 示例 2：
 * <p>
 * <p>
 * <p>
 * 输入：[0,0,null,0,null,0,null,null,0]
 * 输出：2
 * 解释：需要至少两个摄像头来监视树的所有节点。 上图显示了摄像头放置的有效位置之一。
 * <p>
 * 提示：
 * <p>
 * 给定树的节点数的范围是 [1, 1000]。
 * 每个节点的值都是 0。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/binary-tree-cameras
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * [0,0,null,null,0,0,null,null,0,0]
 *
 * author: jinpeng.ren create at 2019/11/15 18:17
 * email: jinpeng.ren@11bee.com
 */
public class _968 {

    public static int minCameraCover(TreeNode root) {
        if(root == null){
            return 0;
        }
        if(root.left == null && root.right == null){
            return 1;
        }
        return Math.min(count(root, true), count(root, false));
    }

    public static int count(TreeNode root, boolean need) {
        if (root == null) {
            return 0;
        }
        if (need) {
            return 1 + count(root.left, false) + count(root.right, false);
        }
        return count(root.left, true) + count(root.right, true);
    }

    public static void main(String[] args) {
        TreeNode n1 = new TreeNode(0);
        TreeNode n2 = new TreeNode(0);
        TreeNode n3 = new TreeNode(0);
        TreeNode n4 = new TreeNode(0);
        n1.left = n2;
        n2.left = n3;
        n2.right = n4;
        int i = minCameraCover(n1);
        System.out.println(i);
    }
}
