package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2019/7/9 21:01
 * email: jinpeng.ren@11bee.com
 */
public class _19 {

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        removeNthFromEnd(head, 2);
    }

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode fast = head;
        int pos = n;
        while (pos != 0){
            fast = fast.next;
            pos--;
        }
        ListNode slow = head;
        ListNode slowPre = null;
        while (fast != null){
            fast = fast.next;
            slowPre = slow;
            slow = slow.next;
        }
        if(slowPre != null) {
            slowPre.next = slow.next;
        }else{
            return slow.next;
        }
        slow.next = null;
        return head;
    }
}
