package com.rjp.expandframework.pattern.AbstractFactory;

/**
 * author: jinpeng.ren create at 2019/11/13 13:52
 * email: jinpeng.ren@11bee.com
 */
public class Fish implements VirtualFood {


    @Override
    public void create() {
        System.out.println("A Fish");
    }
}
