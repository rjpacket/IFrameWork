package com.rjp.fastframework.component;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rjp.commonlib.ServiceFactory;
import com.rjp.fastframework.R;
import com.rjp.fastframework.Hello;

public class ComponentActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_component);

        Button loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setText(Hello.sayHi());
    }

    public void login(View view) {
        ServiceFactory.getInstance().getLoginService().start(this);
    }

    public void shop(View view) {

    }

    public void loadShop(View view) {

    }
}
