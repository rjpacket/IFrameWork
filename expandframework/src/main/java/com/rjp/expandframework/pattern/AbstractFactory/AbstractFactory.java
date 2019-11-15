package com.rjp.expandframework.pattern.AbstractFactory;

import com.rjp.expandframework.pattern.SimpleFactory.VirtualAnimal;

/**
 * author: jinpeng.ren create at 2019/11/13 13:02
 * email: jinpeng.ren@11bee.com
 */
public interface AbstractFactory {
    VirtualAnimal getAnimal();

    VirtualFood getFood();
}
