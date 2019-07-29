package com.rjp.expandframework.apm;

import android.app.Activity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class HookWindowManagerImpl {
    private static final String TAG = "===>";

    public static void hook(Activity activity) {
        Window window = activity.getWindow();
        try {
            final Object oldPM = ReflectUtil.getField("android.view.Window", window, "mWindowManager");
            WindowManager newPM = new WindowManagerWrapper((WindowManager) oldPM);
            ReflectUtil.setField("android.view.Window", window, "mWindowManager", newPM);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}