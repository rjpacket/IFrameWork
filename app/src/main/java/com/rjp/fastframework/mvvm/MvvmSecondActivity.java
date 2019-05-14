package com.rjp.fastframework.mvvm;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rjp.fastframework.R;

public class MvvmSecondActivity extends AppCompatActivity {

    private MvvmViewModel mvvmViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvvm_second);

        mvvmViewModel = MvvmUtils.of(this).get(MvvmViewModel.class);
        mvvmViewModel.mockData();
    }
}
