package com.rjp.expandframework.apm;

import android.os.IBinder;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * author: jinpeng.ren create at 2019/7/23 16:52
 * email: jinpeng.ren@11bee.com
 */
public class WindowManagerWrapper implements WindowManager {

    private WindowManager windowManager;

    public WindowManagerWrapper(WindowManager windowManager){
        this.windowManager = windowManager;
    }

    @Override
    public void addView(View view, ViewGroup.LayoutParams params) {
        Log.d("===>", "hook addview");
        windowManager.addView(view, params);
    }

    @Override
    public void updateViewLayout(View view, ViewGroup.LayoutParams params) {
        windowManager.updateViewLayout(view, params);
    }

    @Override
    public void removeView(View view) {
        windowManager.removeView(view);
    }

    @Override
    public Display getDefaultDisplay() {
        return windowManager.getDefaultDisplay();
    }

    @Override
    public void removeViewImmediate(View view) {
        windowManager.removeViewImmediate(view);
    }
}
