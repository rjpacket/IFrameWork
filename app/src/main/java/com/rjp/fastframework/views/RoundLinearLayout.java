package com.rjp.fastframework.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class RoundLinearLayout extends LinearLayout {
    private Xfermode SRC_ATOP = new PorterDuffXfermode(PorterDuff.Mode.SRC);
    private int width;
    private int height;
    private Paint mPaint;

    public RoundLinearLayout(Context context) {
        this(context, null);
    }

    public RoundLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        int sc = canvas.saveLayer(0, 0, width, height, null, Canvas.ALL_SAVE_FLAG);
        super.dispatchDraw(canvas);
        mPaint.setXfermode(SRC_ATOP);
        canvas.drawRoundRect(new RectF(0, 0, width, height), 10, 10, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(sc);
    }
}
