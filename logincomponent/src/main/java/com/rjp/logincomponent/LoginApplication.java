package com.rjp.logincomponent;

import android.app.Application;

import com.rjp.commonlib.IAppInit;
import com.rjp.commonlib.ServiceFactory;

public class LoginApplication implements IAppInit {

//    private static Application instance;
//
//    public static Application getInstance(){
//        return instance;
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        init(this);
//    }

    @Override
    public void init(Application application) {
//        instance = application;
        ServiceFactory.getInstance().setLoginService(new LoginService());
    }
}
