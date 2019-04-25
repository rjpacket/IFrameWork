package com.rjp.logincomponent;

import android.content.Context;
import android.content.Intent;

import com.rjp.commonlib.ILoginService;

public class LoginService implements ILoginService {
    @Override
    public void start(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }
}
