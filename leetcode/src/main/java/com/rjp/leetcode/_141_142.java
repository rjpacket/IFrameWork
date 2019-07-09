package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2019/7/8 17:03
 * email: jinpeng.ren@11bee.com
 */
public class _141_142 {

    public static void main(String[] args) {

    }

    public static boolean hasCycle(ListNode head) {
        if(head == null || head.next == null){
            return false;
        }
        if(head.next.next == head){
            return true;
        }
        ListNode fast = head;
        ListNode slow = head;
        do{
            fast = fast.next.next;
            slow = slow.next;
        }while (fast != null && slow != null && fast.next != null && fast != slow);
        return fast == slow;
    }

    public static ListNode detectCycle(ListNode head) {
        if(head == null || head.next == null){
            return null;
        }
        if(head.next.next == head){
            return head;
        }
        ListNode fast = head;
        ListNode slow = head;
        do{
            fast = fast.next.next;
            slow = slow.next;
        }while (fast != null && slow != null && fast.next != null && fast != slow);

        if(fast == slow) {
            while (head.next != slow.next) {
                head = head.next;
                slow = slow.next;
            }
            return head.next;
        }
        return null;
    }
}
