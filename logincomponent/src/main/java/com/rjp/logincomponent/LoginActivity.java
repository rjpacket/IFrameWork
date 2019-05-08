package com.rjp.logincomponent;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.rjp.annotation.Route;
import com.rjp.commonlib.ServiceFactory;

@Route(path = "/login/login")
public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void toast(View view) {
        Toast.makeText(this, "登录组件", Toast.LENGTH_SHORT).show();
    }
}
