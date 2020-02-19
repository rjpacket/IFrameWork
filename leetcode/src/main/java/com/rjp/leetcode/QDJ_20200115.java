package com.rjp.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * author: jinpeng.ren create at 2020/1/15 17:54
 * email: jinpeng.ren@11bee.com
 */
public class QDJ_20200115 {

    public static void main(String[] args) {
        ListNode n1 = new ListNode(2);
        ListNode n2 = new ListNode(5);
        ListNode n3 = new ListNode(7);
        n1.next = n2;
        n2.next = n3;

        ListNode n4 = new ListNode(1);
        ListNode n5 = new ListNode(3);
        ListNode n6 = new ListNode(8);
        n4.next = n5;
        n5.next = n6;

        ListNode n7 = new ListNode(4);
        ListNode n8 = new ListNode(6);
        ListNode n9 = new ListNode(9);
        ListNode n10 = new ListNode(10);
        n7.next = n8;
        n8.next = n9;
        n9.next = n10;

        List<ListNode> nodes = new ArrayList<>();
        nodes.add(n1);
        nodes.add(n4);
        nodes.add(n7);
        ListNode merge = merge(nodes);
        while (merge != null) {
            System.out.print(merge.val + " -> ");
            merge = merge.next;
        }
    }

    public static ListNode merge(List<ListNode> nodes) {
        ListNode result = new ListNode(-1);
        ListNode temp = result;
        while (nodes.size() != 0) {
            temp.next = getMin(nodes);
            temp = temp.next;
        }
        return result.next;
    }

    public static ListNode getMin(List<ListNode> nodes) {
        int index = 0;
        ListNode minNode = nodes.get(index);
        int size = nodes.size();
        for (int i = size - 1; i >= 0; i--) {
            ListNode node = nodes.get(i);
            if (node.val < minNode.val) {
                minNode = node;
                index = i;
            }
        }
        ListNode temp = minNode;
        temp = temp.next;
        nodes.remove(index);
        if (temp != null) {
            nodes.add(temp);
        }
        return minNode;
    }
}
