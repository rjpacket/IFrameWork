package com.rjp.expandframework.apm;

import android.app.Activity;
import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.*;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;

/**
 * author: jinpeng.ren create at 2019/7/22 11:51
 * email: jinpeng.ren@11bee.com
 */
public class InstrumentationWrapper extends Instrumentation {

    private Instrumentation sHostInstrumentation;

    public InstrumentationWrapper(Instrumentation instrumentation) {
        this.sHostInstrumentation = instrumentation;
    }

    /**
     * @Override V21+ Wrap activity from REAL to STUB
     */
    public ActivityResult execStartActivity(Context who, IBinder contextThread, IBinder token, Activity target,
                                            Intent intent, int requestCode, android.os.Bundle options) {
        return ReflectAccelerator.execStartActivity(sHostInstrumentation, who, contextThread, token, target, intent,
                requestCode, options);
    }

    /**
     * @Override V20- Wrap activity from REAL to STUB
     */
    public ActivityResult execStartActivity(Context who, IBinder contextThread, IBinder token, Activity target,
                                            Intent intent, int requestCode) {
        return ReflectAccelerator.execStartActivity(sHostInstrumentation, who, contextThread, token, target, intent,
                requestCode);
    }

    @Override
    public void callActivityOnCreate(Activity activity, android.os.Bundle icicle) {
        AppUIRenderTimeUtil.uiRenderStart();
        sHostInstrumentation.callActivityOnCreate(activity, icicle);
    }

    @Override
    public void callActivityOnResume(Activity activity) {
        sHostInstrumentation.callActivityOnResume(activity);
        AppUIRenderTimeUtil.uiRenderEnd();
    }

    @Override
    public Activity newActivity(Class<?> clazz, Context context, IBinder token, Application application, Intent intent,
                                ActivityInfo info, CharSequence title, Activity parent, String id, Object lastNonConfigurationInstance)
            throws InstantiationException, IllegalAccessException {
        Activity newActivity = sHostInstrumentation.newActivity(clazz, context, token, application, intent, info, title, parent, id,
                lastNonConfigurationInstance);
        return newActivity;
    }

    @Override
    public Activity newActivity(ClassLoader cl, String className, Intent intent)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        Activity newActivity = sHostInstrumentation.newActivity(cl, className, intent);
        return newActivity;
    }

    public static class ActivityThreadHandlerCallback implements Handler.Callback {
        private static final int LAUNCH_ACTIVITY = 100;

        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what != LAUNCH_ACTIVITY) {
                return false;
            } else {
                Object/* ActivityClientRecord */ r = msg.obj;
                Intent intent = ReflectAccelerator.getIntent(r);
                ReflectAccelerator.setIntent(r, intent);
                return false;
            }
        }
    }
}
