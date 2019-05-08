package com.rjp.logincomponent;

import android.app.Activity;
import android.os.Bundle;

import com.rjp.annotation.Route;

@Route(path = "/login/register")
public class RegisterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }
}
