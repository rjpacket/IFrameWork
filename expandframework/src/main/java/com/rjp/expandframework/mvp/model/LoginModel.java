package com.rjp.expandframework.mvp.model;

/**
 * author : Gimpo create on 2019/2/28 12:14
 * email  : jimbo922@163.com
 */
public class LoginModel {
    private String username;
    private String password;

    public LoginModel(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
