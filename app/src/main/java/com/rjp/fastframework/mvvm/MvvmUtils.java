package com.rjp.fastframework.mvvm;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.FragmentActivity;

import com.rjp.fastframework.MainApplication;

public class MvvmUtils {

    public static ViewModelProvider of(FragmentActivity activity){
        return ViewModelProviders.of(activity);
    }
}
