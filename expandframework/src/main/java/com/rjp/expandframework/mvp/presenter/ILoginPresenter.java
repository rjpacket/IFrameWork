package com.rjp.expandframework.mvp.presenter;

/**
 * author : Gimpo create on 2019/2/28 12:24
 * email  : jimbo922@163.com
 */
public interface ILoginPresenter {
    void networkGetUserInfo();

    void login(String username, String password);

    void clear();
}
