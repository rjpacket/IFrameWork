package com.rjp.expandframework.mvp.presenter;

import android.text.TextUtils;

import com.rjp.expandframework.mvp.model.LoginModel;
import com.rjp.expandframework.mvp.view.ILoginView;

/**
 * author : Gimpo create on 2019/2/28 12:24
 * email  : jimbo922@163.com
 */
public class LoginPresenterImpl implements ILoginPresenter {

    public ILoginView loginView;
    public LoginModel loginModel;

    public LoginPresenterImpl(ILoginView loginView){
        this.loginView = loginView;
    }

    @Override
    public void networkGetUserInfo() {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        loginModel = new LoginModel("rjp", "123456");
    }

    @Override
    public void login(String username, String password) {
        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
            loginView.toast("用户名或者密码不合法");
            return;
        }else{
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(username.equals(loginModel.getUsername()) && password.equals(loginModel.getPassword())){
                loginView.loginSuccess();
            }else{
                loginView.loginFailed();
            }
        }
    }

    @Override
    public void clear() {
        loginView.clearUserName();
        loginView.clearUserPassword();
    }
}
