package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2019/11/25 11:43
 * email: jinpeng.ren@11bee.com
 */
public class TransReverseNode {

    public static void main(String[] args) {
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(3);
        ListNode n4 = new ListNode(4);
        ListNode n5 = new ListNode(5);
//        ListNode n6 = new ListNode(6);
//        ListNode n7 = new ListNode(7);
//        ListNode n8 = new ListNode(8);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
//        n5.next = n6;
//        n6.next = n7;
//        n7.next = n8;
        ListNode trans = reverseKGroup(n1, 1);
        while (trans != null){
            System.out.println(trans.val);
            trans = trans.next;
        }
    }

    public static ListNode trans(ListNode head, int K){
        ListNode reverse = reverse(head);
        ListNode node = reverseKGroup(reverse, 3);
        return reverse(node);
    }

    /**
     * 反转K
     * @param head
     * @param K
     * @return
     */
    public static ListNode reverseKGroup(ListNode head, int K){
        ListNode temp = head;
        if(temp == null){
            return null;
        }
        int count = K - 1;
        while (temp.next != null && count != 0){
            count--;
            temp = temp.next;
        }
        if(count == 0){
            ListNode next = temp.next;
            temp.next = null;
            ListNode reverseHead = reverse(head);
            ListNode tmp = reverseHead;
            while (tmp != null && tmp.next != null){
                tmp = tmp.next;
            }
            tmp.next = reverseKGroup(next, K);
            return reverseHead;
        }else{
            return head;
        }
    }

    /**
     * 反转
     * @param head
     * @return
     */
    public static ListNode reverse(ListNode head){
        if(head == null || head.next == null){
            return head;
        }
        ListNode last = reverse(head.next);
        head.next.next = head;
        head.next = null;
        return last;
    }
}
