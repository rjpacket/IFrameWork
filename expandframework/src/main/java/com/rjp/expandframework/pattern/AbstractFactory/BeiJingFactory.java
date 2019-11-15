package com.rjp.expandframework.pattern.AbstractFactory;

import com.rjp.expandframework.pattern.SimpleFactory.Dog;
import com.rjp.expandframework.pattern.SimpleFactory.VirtualAnimal;

/**
 * author: jinpeng.ren create at 2019/11/13 15:15
 * email: jinpeng.ren@11bee.com
 */
public class BeiJingFactory implements AbstractFactory {
    @Override
    public VirtualAnimal getAnimal() {
        return new Dog();
    }

    @Override
    public VirtualFood getFood() {
        return new Meat();
    }
}
