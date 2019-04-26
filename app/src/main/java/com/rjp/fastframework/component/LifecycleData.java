package com.rjp.fastframework.component;

import android.arch.lifecycle.GenericLifecycleObserver;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.util.Log;

public class LifecycleData implements GenericLifecycleObserver {

    @Override
    public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
        Log.d("-------------->", event.toString());
    }
}
