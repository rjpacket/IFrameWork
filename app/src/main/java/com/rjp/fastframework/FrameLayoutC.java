package com.rjp.fastframework;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * author: jinpeng.ren create at 2019/11/14 11:55
 * email: jinpeng.ren@11bee.com
 */
public class FrameLayoutC extends View {
    public FrameLayoutC(Context context) {
        super(context);
    }

    public FrameLayoutC(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FrameLayoutC(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d("===>", "C dispatchTouchEvent");
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("===>", "C onTouchEvent");
        return super.onTouchEvent(event);
    }
}
