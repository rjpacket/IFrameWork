package com.rjp.expandframework.proxy;

/**
 * author: jinpeng.ren create at 2019/12/30 20:41
 * email: jinpeng.ren@11bee.com
 */
public interface IPermission {

    void allow();

    void denied();

    void goSetting();
}
