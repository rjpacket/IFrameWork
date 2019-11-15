package com.rjp.expandframework.pattern.SimpleFactory;

/**
 * author: jinpeng.ren create at 2019/11/13 12:15
 * email: jinpeng.ren@11bee.com
 */
public class Factory {

    public VirtualAnimal getAnimal(String type){
        if("cat".equals(type)){
            return new Cat();
        }
        return new Dog();
    }
}
