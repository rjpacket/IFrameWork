package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2019/7/9 10:59
 * email: jinpeng.ren@11bee.com
 */
public class _148 {

    public static void main(String[] args) {
        ListNode n1 = new ListNode(5);
        ListNode n2 = new ListNode(1);
        ListNode n3 = new ListNode(6);
        ListNode n4 = new ListNode(3);
        ListNode n5 = new ListNode(7);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        ListNode node = sortList(n1);
        while (node != null){
            System.out.println(node.val);
            node = node.next;
        }
    }

    public static ListNode sortList(ListNode head){
        if(head == null || head.next == null){
            return head;
        }
        ListNode slow = head;
        ListNode slowPre = null;
        ListNode fast = head;
        while (slow != null && fast != null && fast.next != null){
            slowPre = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        slowPre.next = null;
        ListNode left = sortList(head);
        ListNode right = sortList(slow);
        return merge(left, right);
    }

    private static ListNode merge(ListNode left, ListNode right) {
        if(left == null){
            return right;
        }
        if(right == null){
            return left;
        }
        ListNode head = new ListNode(0);
        ListNode temp = head;
        while (left != null && right != null){
            if(left.val < right.val){
                head.next = left;
                head = head.next;
                left = left.next;
            }else{
                head.next = right;
                head = head.next;
                right = right.next;
            }
        }
        while (left != null){
            head.next = left;
            head = head.next;
            left = left.next;
        }
        while (right != null){
            head.next = right;
            head = head.next;
            right = right.next;
        }
        return temp.next;
    }

}
