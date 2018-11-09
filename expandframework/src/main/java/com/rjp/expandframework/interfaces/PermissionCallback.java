package com.rjp.expandframework.interfaces;

/**
 * author : Gimpo create on 2018/6/6 14:56
 * email  : jimbo922@163.com
 */
public interface PermissionCallback {
    /**
     * 权限允许
     */
    void allow();

    /**
     * 权限拒绝
     */
    void deny();
}
