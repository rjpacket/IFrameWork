package com.rjp.fastframework.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class RestoreView extends View {
    private RectF rect;
    private Paint mPaint;

    public RestoreView(Context context) {
        this(context, null);
    }

    public RestoreView(Context context, AttributeSet attrs) {
        super(context, attrs);

        rect = new RectF();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.GREEN);

        canvas.save();
        rect.left = 100;
        rect.top = 100;
        rect.right = 500;
        rect.bottom = 500;
        canvas.clipRect(rect);
        canvas.drawColor(Color.BLUE);

        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(100, 100, 100, mPaint);
        canvas.restore();
        mPaint.setColor(Color.YELLOW);
        canvas.drawCircle(100, 100, 150, mPaint);
    }
}
