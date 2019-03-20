package com.rjp.expandframework.mvp.view;

/**
 * author : Gimpo create on 2019/2/28 12:24
 * email  : jimbo922@163.com
 */
public interface ILoginView extends BaseView{
    void clearUserName();

    void clearUserPassword();

    void loginSuccess();

    void loginFailed();
}
