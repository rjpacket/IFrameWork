package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2019/12/5 11:56
 * email: jinpeng.ren@11bee.com
 */
public class _147 {

    public static void main(String[] args) {
        ListNode n1 = new ListNode(0);
        ListNode n2 = new ListNode(0);
        ListNode n3 = new ListNode(0);
        ListNode n4 = new ListNode(0);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;

        ListNode node = insertionSortList(n1);
        while (node != null){
            System.out.println(node.val);
            node = node.next;
        }
    }

    public static ListNode insertionSortList(ListNode head) {
        if(head == null || head.next == null){
            return head;
        }
        ListNode root = new ListNode(Integer.MIN_VALUE);
        ListNode pre = root;
        ListNode tail = root;
        ListNode cur = head;
        while (cur != null){
            if(tail.val < cur.val){
                tail.next = cur;
                tail = cur;
                cur = cur.next;
            }else{
                ListNode tmp = cur.next;
                tail.next = tmp;
                while (pre.next != null && pre.next.val < cur.val){
                    pre = pre.next;
                }
                cur.next = pre.next;
                pre.next = cur;
                pre = root;
                cur = tmp;
            }
        }
        return root.next;
    }
}
