package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2019/7/9 14:57
 * email: jinpeng.ren@11bee.com
 */
public class _160 {

    public static void main(String[] args) {

    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if(headA == null || headB == null){
            return null;
        }
        ListNode tempA = headA;
        ListNode tempB = headB;
        int lenA = 0;
        int lenB = 0;
        while (headA != null){
            lenA++;
            headA = headA.next;
        }
        while (headB != null){
            lenB++;
            headB = headB.next;
        }
        int len = Math.abs(lenA - lenB);
        if(lenA > lenB){
            while (len != 0){
                tempA = tempA.next;
                len--;
            }
        }else{
            while (len != 0){
                tempB = tempB.next;
                len--;
            }
        }
        while (tempA != tempB){
            tempA = tempA.next;
            tempB = tempB.next;
        }
        return tempA;
    }
}
