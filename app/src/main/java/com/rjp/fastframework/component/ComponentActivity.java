package com.rjp.fastframework.component;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rjp.commonlib.ServiceFactory;
import com.rjp.expandframework.liveDataBus.LiveDataBus;
import com.rjp.fastframework.R;
import com.rjp.fastframework.Hello;

public class ComponentActivity extends AppCompatActivity {

    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_component);

        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setText(Hello.sayHi());

        getLifecycle().addObserver(new LifecycleData());

        LiveDataBus.get().with("rjp", String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                loginBtn.setText(s);
                Toast.makeText(ComponentActivity.this, "Main" + s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void login(View view) {
        ServiceFactory.getInstance().getLoginService().start(this);
    }

    public void shop(View view) {
        startActivity(new Intent(ComponentActivity.this, NextActivity.class));
    }

    public void loadShop(View view) {
        LiveDataBus.get().with("rjp", String.class).postValue("你好啊");
    }
}
