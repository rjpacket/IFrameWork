package com.rjp.leetcode;

/**
 * author: jinpeng.ren create at 2019/11/19 10:58
 * email: jinpeng.ren@11bee.com
 */
public class _1003 {

    public boolean isValid(String S) {
        if(S == null || S.length() == 0){
            return false;
        }
        while (S.contains("abc")){
            S = S.replace("abc", "");
        }
        return S.length() == 0;
    }
}
