package com.rjp.expandframework.pattern.AbstractFactory;

import com.rjp.expandframework.pattern.SimpleFactory.Cat;
import com.rjp.expandframework.pattern.SimpleFactory.VirtualAnimal;

/**
 * author: jinpeng.ren create at 2019/11/13 15:16
 * email: jinpeng.ren@11bee.com
 */
public class ShangHaiFactory implements AbstractFactory {
    @Override
    public VirtualAnimal getAnimal() {
        return new Cat();
    }

    @Override
    public VirtualFood getFood() {
        return new Fish();
    }
}
