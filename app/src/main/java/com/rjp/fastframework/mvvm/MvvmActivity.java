package com.rjp.fastframework.mvvm;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.rjp.fastframework.R;

public class MvvmActivity extends AppCompatActivity {

    private MvvmViewModel mvvmViewModel;
    private TextView tvMessage1;
    private TextView tvMessage2;
    private TextView tvMessage3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvvm);

        tvMessage1 = findViewById(R.id.tv_message1);
        tvMessage2 = findViewById(R.id.tv_message2);
        tvMessage3 = findViewById(R.id.tv_message3);

        //模拟网络请求
        mvvmViewModel = MvvmUtils.of(this).get(MvvmViewModel.class);

        mvvmViewModel.getmDataHolder().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                tvMessage1.setText(user.toString());
            }
        });

        mvvmViewModel.getmDataMap().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String str) {
                tvMessage2.setText(str);
            }
        });
    }

    public void addAge(View view) {
//        User value = mvvmViewModel.getmDataHolder().getValue();
//        value.setAge(value.getAge() + 1);
//        mvvmViewModel.getmDataHolder().setValue(value);
        startActivity(new Intent(this, MvvmSecondActivity.class));
    }
}


