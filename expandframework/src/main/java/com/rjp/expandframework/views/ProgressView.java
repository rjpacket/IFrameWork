package com.rjp.expandframework.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * author : Gimpo create on 2019/3/22 18:14
 * email  : jimbo922@163.com
 */
public class ProgressView extends View {

    private int width;
    private int height;
    private Paint bgPaint;
    private Paint progressPaint;
    private Xfermode X_MODE = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);

    private float progress = 0f;

    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setColor(Color.parseColor("#cccccc"));

        progressPaint = new Paint();
        progressPaint.setColor(Color.parseColor("#eb1c42"));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int sc = canvas.saveLayer(0, 0, width, height, bgPaint, Canvas.ALL_SAVE_FLAG);
        canvas.drawRoundRect(new RectF(0, 0, width, height), height / 2, height / 2, bgPaint);
        progressPaint.setXfermode(X_MODE);
        canvas.drawRect(0, 0, width * progress, height, progressPaint);
        progressPaint.setXfermode(null);
        canvas.restoreToCount(sc);
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }
}
