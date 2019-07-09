package com.rjp.leetcode;


import org.w3c.dom.Node;

/**
 * author: jinpeng.ren create at 2019/6/25 17:47
 * email: jinpeng.ren@11bee.com
 */
public class _23 {

    public static void main(String[] args) {
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(4);
        ListNode n3 = new ListNode(5);
        n1.next = n2;
        n2.next = n3;

        ListNode n4 = new ListNode(1);
        ListNode n5 = new ListNode(3);
        ListNode n6 = new ListNode(4);
        n4.next = n5;
        n5.next = n6;

        ListNode n7 = new ListNode(2);
        ListNode n8 = new ListNode(6);
        n7.next = n8;

        ListNode[] arr = new ListNode[]{n1, n4, n7};
        ListNode node = mergeKLists(arr);
        while (node != null) {
            System.out.println(node.val);
            node = node.next;
        }
    }

    public static ListNode mergeKLists(ListNode[] lists) {
        if(lists == null || lists.length == 0){
            return null;
        }
        int length = lists.length;
        int skip = 1;
        while (skip < length){
            for (int i = 0; i < length - skip; i += skip * 2) {
                lists[i] = mergeTwo(lists[i], lists[i + skip]);
            }
            skip *= 2;
        }
        return lists[0];
    }

    public static ListNode mergeTwo(ListNode L, ListNode R){
        ListNode head = new ListNode(0);
        ListNode temp = head;
        while (L != null && R != null){
            if(L.val < R.val){
                head.next = L;
                L = L.next;
            }else{
                head.next = R;
                R = R.next;
            }
            head = head.next;
        }
        if(L != null){
            head.next = L;
        }else{
            head.next = R;
        }
        return temp.next;
    }

    public static ListNode mergeKLists1(ListNode[] lists) {
        if(lists == null || lists.length == 0){
            return null;
        }
        ListNode head = new ListNode(0);
        ListNode temp = head;
        while (true) {
            int index = -1;
            int count = 0;
            ListNode min = null;
            int length = lists.length;
            boolean first = true;
            for (int i = 0; i < length; i++) {
                if (lists[i] == null) {
                    count++;
                    if (count == length) {
                        return temp.next;
                    }
                    continue;
                } else {
                    if (first) {
                        first = false;
                        min = lists[i];
                        index = i;
                    }
                }
                if (lists[i].val < min.val) {
                    min = lists[i];
                    index = i;
                }
            }
            head.next = min;
            head = head.next;
            lists[index] = lists[index].next;
        }
    }
}
