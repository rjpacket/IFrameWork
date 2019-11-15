package com.rjp.expandframework.pattern.SimpleFactory;

/**
 * author: jinpeng.ren create at 2019/11/13 12:18
 * email: jinpeng.ren@11bee.com
 */
public class Cat implements VirtualAnimal {
    @Override
    public void show() {
        System.out.println("A Cat");
    }
}
