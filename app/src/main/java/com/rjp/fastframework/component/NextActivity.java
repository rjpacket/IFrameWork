package com.rjp.fastframework.component;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.rjp.expandframework.liveDataBus.LiveDataBus;
import com.rjp.fastframework.R;

public class NextActivity extends AppCompatActivity {

    private Button btn;
    private Button btn1;
    private Observer<String> observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        btn = findViewById(R.id.btn);
        btn1 = findViewById(R.id.btn1);

        LiveDataBus.get().with("rjp", String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                btn.setText(s);
                Toast.makeText(NextActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });

        observer = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                btn1.setText(s);
                Toast.makeText(NextActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        };
        LiveDataBus.get().with("rjp", String.class).observeForever(observer);
    }

    @Override
    protected void onDestroy() {
        LiveDataBus.get().with("rjp", String.class).removeObserver(observer);
        super.onDestroy();
    }
}
