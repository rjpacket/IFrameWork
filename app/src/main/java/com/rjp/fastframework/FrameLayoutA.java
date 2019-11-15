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
public class FrameLayoutA extends FrameLayout {
    public FrameLayoutA(Context context) {
        super(context);
    }

    public FrameLayoutA(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FrameLayoutA(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d("===>", "A dispatchTouchEvent");
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d("===>", "A onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("===>", "A onTouchEvent");
        return super.onTouchEvent(event);
    }
}
