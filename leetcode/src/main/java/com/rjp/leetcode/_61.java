package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2019/7/4 9:57
 * email: jinpeng.ren@11bee.com
 */
public class _61 {

    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(2);
        ListNode l3 = new ListNode(3);
        ListNode l4 = new ListNode(4);
        ListNode l5 = new ListNode(5);
        l1.next = l2;
        l2.next = l3;
        l3.next = l4;
        l4.next = l5;
        rotateRight(l1, 2);
    }

    public static ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null || k == 0) {
            return head;
        }
        ListNode temp = head;
        int length = 1;
        while (temp.next != null) {
            length++;
            temp = temp.next;
        }
        k %= length;
        if (k == 0) {
            return head;
        }
        ListNode fast = head;
        for (int i = 0; i < k; i++) {
            fast = fast.next;
        }
        ListNode slow = head;
        ListNode slowPre = null;
        while (fast != null) {
            fast = fast.next;
            slowPre = slow;
            slow = slow.next;
        }
        if (slowPre != null) {
            slowPre.next = null;
        }
        temp.next = head;
        return slow;
    }
}
