package com.rjp.expandframework.apm;

import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.content.pm.ProviderInfo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * author: jinpeng.ren create at 2019/7/22 15:35
 * email: jinpeng.ren@11bee.com
 */
public class ActivityAPM {
    private ActivityAPM() {
    }

    private static ActivityAPM instance = null;

    public static ActivityAPM getInstance() {
        if (instance == null) {
            synchronized (ActivityAPM.class) {
                if (instance == null) {
                    instance = new ActivityAPM();
                }
            }
        }
        return instance;
    }

    public void init(Application app) {
        Object/*ActivityThread*/ thread;
        List<ProviderInfo> providers;
        Instrumentation base;
        InstrumentationWrapper wrapper;
        Field f;

        // Get activity thread
        thread = getActivityThread(app);

        // Replace instrumentation
        try {
            f = thread.getClass().getDeclaredField("mInstrumentation");
            f.setAccessible(true);
            base = (Instrumentation) f.get(thread);
            wrapper = new InstrumentationWrapper(base);
            f.set(thread, wrapper);
        } catch (Exception e) {
            throw new RuntimeException("Failed to replace instrumentation for thread: " + thread);
        }
    }

    private static Object getActivityThread(Context context) {
        try {
            //1.首先尝试通过 ActivityThread 内部的静态变量获取。
            Class activityThread = Class.forName("android.app.ActivityThread");
            // ActivityThread.currentActivityThread()
            Method m = activityThread.getMethod("currentActivityThread", new Class[0]);
            m.setAccessible(true);
            Object thread = m.invoke(null, new Object[0]);
            if (thread != null) return thread;

            //2.静态变量获取失败，那么再通过 Application 的 mLoadedApk 中的 mActivityThread 获取。
            Field mLoadedApk = context.getClass().getField("mLoadedApk");
            mLoadedApk.setAccessible(true);
            Object apk = mLoadedApk.get(context);
            Field mActivityThreadField = apk.getClass().getDeclaredField("mActivityThread");
            mActivityThreadField.setAccessible(true);
            return mActivityThreadField.get(apk);
        } catch (Throwable ignore) {
            throw new RuntimeException("Failed to get mActivityThread from context: " + context);
        }
    }
}
