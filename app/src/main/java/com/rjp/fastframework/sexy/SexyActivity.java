package com.rjp.fastframework.sexy;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.rjp.fastframework.R;

import static android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;

public class SexyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sexy);
    }
}
