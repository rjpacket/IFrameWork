package com.rjp.shopcomponent;

import android.app.Application;

import com.rjp.commonlib.IAppInit;

public class ShopApplication extends Application implements IAppInit {

    private static Application instance;

    public static Application getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init(this);
    }

    @Override
    public void init(Application application) {
        instance = application;
    }
}
