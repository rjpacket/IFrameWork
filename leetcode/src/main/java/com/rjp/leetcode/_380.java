package com.rjp.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * author: jinpeng.ren create at 2019/6/21 14:47
 * email: jinpeng.ren@11bee.com
 */
public class _380 {

    static class RandomizedSet {

        private HashSet<Integer> hashSet;
        private Map<Integer, String> temp;

        /** Initialize your data structure here. */
        public RandomizedSet() {
            hashSet = new HashSet<>();
            temp = new HashMap<>();
        }

        /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
        public boolean insert(int val) {
            if(hashSet.contains(val)){

                return false;
            }
            return hashSet.add(val);
        }

        /** Removes a value from the set. Returns true if the set contained the specified element. */
        public boolean remove(int val) {
            if(!hashSet.contains(val)){
                return false;
            }
            return hashSet.remove(val);
        }

        /** Get a random element from the set. */
        public int getRandom() {
            int result = new Random().nextInt(hashSet.size());
            Iterator<Integer> iterator = hashSet.iterator();
            while (result != 0){
                iterator.next();
                result--;
            }
            return iterator.next();


        }
    }

    public static void main(String[] args) {
        RandomizedSet set = new RandomizedSet();
        set.insert(1);
        set.insert(1);
        set.insert(1);
        set.remove(2);
        set.remove(2);
        set.remove(2);
        set.insert(2);
        System.out.println(set.getRandom());
        System.out.println(set.getRandom());
        System.out.println(set.getRandom());
        System.out.println(set.getRandom());
        System.out.println(set.getRandom());
        System.out.println(set.getRandom());
        System.out.println(set.getRandom());
        System.out.println(set.getRandom());
        System.out.println(set.getRandom());
        System.out.println(set.getRandom());
        System.out.println(set.getRandom());
        System.out.println(set.getRandom());
        System.out.println(set.getRandom());
        System.out.println(set.getRandom());
    }


}
