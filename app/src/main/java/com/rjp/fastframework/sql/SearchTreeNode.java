package com.rjp.fastframework.sql;

/**
 * 二叉查找树
 * author: jinpeng.ren create at 2019/11/1 19:43
 * email: jinpeng.ren@11bee.com
 */
public class SearchTreeNode {

    public TreeNode root;

    public class TreeNode {
        public TreeNode left;
        public TreeNode right;
        public int key;
        public Object value;

        public TreeNode(int key, Object value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * 二叉查找树的插入
     *
     * @param key
     * @param value
     */
    public void put(int key, Object value) {
        root = put(root, key, value);
    }

    private TreeNode put(TreeNode root, int key, Object value) {
        if (root == null) {
            root = new TreeNode(key, value);
        } else {
            if (root.key == key) {
                root.value = value;
            } else if (root.key > key) {
                root.left = put(root.left, key, value);
            } else {
                root.right = put(root.right, key, value);
            }
        }
        return root;
    }

    /**
     * 二叉查找树的查找
     *
     * @param key
     * @return
     */
    public Object get(int key) {
        return get(root, key);
    }

    private Object get(TreeNode root, int key) {
        if (root == null) {
            return null;
        }
        if (root.key == key) {
            return root.value;
        } else if (root.key > key) {
            return get(root.left, key);
        }
        return get(root.right, key);
    }

    /**
     * 二叉查找树的删除
     *
     * @param key
     */
    public void delete(int key) {
        root = delete(root, key);
    }

    private TreeNode delete(TreeNode root, int key) {
        if (root == null) {
            return null;
        }
        if (root.key > key) {
            root.left = delete(root.left, key);
        } else if (root.key < key) {
            root.right = delete(root.right, key);
        } else {
            if(root.right == null){
                return root.left;
            }
            if(root.left == null){
                return root.right;
            }
            TreeNode p = root;
            root = min(p.right);
            root.right = deleteMin(p.right);
            root.left = p.left;
        }
        return root;
    }

    /**
     * 删除最小节点之后的树
     *
     * @param root
     * @return
     */
    public TreeNode deleteMin(TreeNode root) {
        if (root.left == null) {
            return root.right;
        }
        root.left = deleteMin(root.left);
        return root;
    }

    /**
     * 二叉树的最小叶子
     *
     * @param root
     * @return
     */
    public TreeNode min(TreeNode root) {
        if (root == null) {
            return null;
        }
        if (root.left == null) {
            return root;
        }
        return min(root.left);
    }

    public static void main(String[] args) {
        SearchTreeNode container = new SearchTreeNode();
        container.put(100, 100);
        container.put(50, 50);
        container.put(20, 20);
        container.put(10, 10);
        container.put(30, 30);
        container.put(70, 70);
        container.put(60, 60);
        container.put(80, 80);
        container.put(120, 120);
        container.put(110, 110);
        container.put(130, 130);

        container.delete(120);

        print(container.root);
    }

    public static void print(TreeNode root){
        if(root == null){
            return;
        }
        System.out.println(root.key + " ==> " + root.value);
        print(root.left);
        print(root.right);
    }
}
