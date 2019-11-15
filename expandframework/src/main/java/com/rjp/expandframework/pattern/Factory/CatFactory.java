package com.rjp.expandframework.pattern.Factory;

import com.rjp.expandframework.pattern.SimpleFactory.Cat;
import com.rjp.expandframework.pattern.SimpleFactory.VirtualAnimal;

/**
 * author: jinpeng.ren create at 2019/11/13 12:58
 * email: jinpeng.ren@11bee.com
 */
public class CatFactory implements VirtualFactory {
    @Override
    public VirtualAnimal getAnimal() {
        return new Cat();
    }
}
