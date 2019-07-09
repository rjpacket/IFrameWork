package com.rjp.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author: jinpeng.ren create at 2019/7/8 17:28
 * email: jinpeng.ren@11bee.com
 */
public class _146 {

    static class LRUCache {

        class ListNode {
            int key;
            int val;
            ListNode next;
            ListNode pre;

            public ListNode(){}

            public ListNode(int key, int val) {
                this.key = key;
                this.val = val;
            }
        }

        ListNode head;
        ListNode last;
        Map<Integer, ListNode> cache = new HashMap<>();
        int size;
        int capacity;

        public LRUCache(int capacity) {
            this.capacity = capacity;
            head = new ListNode();
            last = new ListNode();

            head.next = last;
            last.pre = head;
        }

        public void addNode(ListNode node){
            node.pre = head;
            node.next = head.next;

            head.next.pre = node;
            head.next = node;
        }

        public void removeNode(ListNode node){
            ListNode pre = node.pre;
            ListNode next = node.next;

            pre.next = next;
            next.pre = pre;
        }

        public void moveToHead(ListNode node){
            removeNode(node);
            addNode(node);
        }

        public ListNode popLast(){
            ListNode pre = last.pre;
            removeNode(pre);
            return pre;
        }

        public int get(int key) {
            ListNode node = cache.get(key);
            if (node == null) {
                return -1;
            }
            moveToHead(node);
            return node.val;
        }

        public void put(int key, int value) {
            ListNode node = cache.get(key);
            if(node == null){
                ListNode newNode = new ListNode(key, value);
                addNode(newNode);

                cache.put(key, newNode);
                size++;

                if(size > capacity){
                    ListNode listNode = popLast();
                    cache.remove(listNode.key);
                    size--;
                }
            }else{
                node.val = value;
                moveToHead(node);
            }
        }
    }

    public static void main(String[] args) {
        LRUCache cache = new LRUCache( 2 /* 缓存容量 */ );

        cache.put(1, 1);
        cache.put(2, 2);
        cache.get(1);       // 返回  1
        cache.put(3, 3);    // 该操作会使得密钥 2 作废
        int i = cache.get(2);// 返回 -1 (未找到)
        System.out.println(i);
        cache.put(4, 4);    // 该操作会使得密钥 1 作废
        cache.get(1);       // 返回 -1 (未找到)
        cache.get(3);       // 返回  3
        cache.get(4);
    }
}
