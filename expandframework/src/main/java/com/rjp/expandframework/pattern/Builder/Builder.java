package com.rjp.expandframework.pattern.Builder;

/**
 * author: jinpeng.ren create at 2019/11/15 14:37
 * email: jinpeng.ren@11bee.com
 */
public abstract class Builder {
    public abstract void buildPartA(String partA);

    public abstract void buildPartB(String partB);

    public abstract void buildPartC(String partC);

    public abstract Product build();
}
