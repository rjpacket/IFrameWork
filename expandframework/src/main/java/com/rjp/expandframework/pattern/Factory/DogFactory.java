package com.rjp.expandframework.pattern.Factory;

import android.view.View;
import android.view.ViewGroup;
import com.rjp.expandframework.pattern.SimpleFactory.Dog;
import com.rjp.expandframework.pattern.SimpleFactory.VirtualAnimal;

import java.security.PublicKey;

/**
 * author: jinpeng.ren create at 2019/11/13 12:58
 * email: jinpeng.ren@11bee.com
 */
public class DogFactory implements VirtualFactory {
    @Override
    public VirtualAnimal getAnimal() {
        return new Dog();
    }



}
