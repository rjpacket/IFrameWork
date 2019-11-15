package com.rjp.fastframework;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * author: jinpeng.ren create at 2019/11/13 16:25
 * email: jinpeng.ren@11bee.com
 */
public class FrameLayoutB extends FrameLayout {
    public FrameLayoutB(Context context) {
        super(context);
    }

    public FrameLayoutB(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FrameLayoutB(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d("===>", "B dispatchTouchEvent");
//        super.dispatchTouchEvent(event);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d("===>", "B onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("===>", "B onTouchEvent");
        return super.onTouchEvent(event);
    }
}
