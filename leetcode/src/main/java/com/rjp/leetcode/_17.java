package com.rjp.leetcode;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author: jinpeng.ren create at 2019/7/9 20:10
 * email: jinpeng.ren@11bee.com
 */
public class _17 {

    public static void main(String[] args) {
        List<String> strings = letterCombinations("23");
        for (String string : strings) {
            System.out.println(string);
        }
    }

    public static List<String> letterCombinations(String digits) {
        if (digits == null) {
            return null;
        }
        if(digits.length() == 0){
            return new ArrayList<>();
        }
        Map<Integer, char[]> map = new HashMap<>();
        map.put(2, new char[]{'a', 'b', 'c'});
        map.put(3, new char[]{'d', 'e', 'f'});
        map.put(4, new char[]{'g', 'h', 'i'});
        map.put(5, new char[]{'j', 'k', 'l'});
        map.put(6, new char[]{'m', 'n', 'o'});
        map.put(7, new char[]{'p', 'q', 'r', 's'});
        map.put(8, new char[]{'t', 'u', 'v'});
        map.put(9, new char[]{'w', 'x', 'y', 'z'});

        char[] chars = digits.toCharArray();
        List<String> result = new ArrayList<>();
        for (char ch : chars) {
            char[] ds = map.get(ch - '0');
            if (result.size() == 0) {
                for (char d : ds) {
                    result.add(String.valueOf(d));
                }
            }else{
                List<String> temp = new ArrayList<>();
                for (String ts : result) {
                    for (char d : ds) {
                        temp.add(ts + d);
                    }
                }
                result = temp;
            }
        }
        return result;
    }


}
