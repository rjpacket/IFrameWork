package com.rjp.expandframework.log;

import android.os.Handler;
import android.os.Looper;

public abstract class LogCallback implements OnLogCallback {

    public abstract void onSuccess(String logFilePath);

    public abstract void onFailure();

    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onLogSuccess(final String logFilePath) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                onSuccess(logFilePath);
            }
        });
    }

    @Override
    public void onLogFailure() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                onFailure();
            }
        });
    }
}
