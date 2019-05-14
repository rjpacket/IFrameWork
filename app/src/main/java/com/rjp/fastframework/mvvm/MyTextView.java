package com.rjp.fastframework.mvvm;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyTextView extends TextView {
    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public void init(Context context){
        MvvmViewModel mvvmViewModel = MvvmUtils.of((FragmentActivity) context).get(MvvmViewModel.class);
        mvvmViewModel.getmDataMap().observe((LifecycleOwner) context, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                setText(s);
            }
        });
    }
}
