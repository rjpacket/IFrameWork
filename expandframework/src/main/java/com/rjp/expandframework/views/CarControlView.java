package com.rjp.expandframework.views;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;
import com.rjp.expandframework.utils.AppUtil;

/**
 * 总刻度，左右圆上各7个 剩下的均匀分布在两矩形边上
 * author: jinpeng.ren create at 2020/2/21 18:51
 * email: jinpeng.ren@11bee.com
 */
public class CarControlView extends View {

    private Paint outStrokePaint;
    private Path outStrokePath;
    private int height;
    private int outRadius;
    private RectF circleRectF;
    private Paint textPaint;
    private Paint circlePaint;
    private int outBorderRadius;

    public CarControlView(Context context) {
        super(context);
        initView(context);
    }

    public CarControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CarControlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        outBorderRadius = AppUtil.dp2px(10);

        circleRectF = new RectF();
        outStrokePath = new Path();
        outStrokePaint = new Paint();
        outStrokePaint.setPathEffect(new CornerPathEffect(outBorderRadius * 2));
        outStrokePaint.setStrokeWidth(AppUtil.dp2px(20));
        outStrokePaint.setAntiAlias(true);
        outStrokePaint.setStyle(Paint.Style.STROKE);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);

        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        outRadius = height / 2 - outBorderRadius;
        setMeasuredDimension((int) (height * 2.5), height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBackground(canvas);

        drawNumber(canvas);

        drawProgress(canvas);
    }

    private void drawProgress(Canvas canvas) {

    }

    private void drawNumber(Canvas canvas) {
        canvas.save();
        for (int i = 0; i <= 6; i++) {
            circlePaint.setColor(Color.GREEN);
            canvas.drawCircle(outRadius + outBorderRadius, outBorderRadius, AppUtil.dp2px(10), circlePaint);

            canvas.save();
            canvas.rotate(30 * i, outRadius + outBorderRadius, outBorderRadius);
            textPaint.setColor(Color.WHITE);
            textPaint.setTextSize(AppUtil.dp2px(10));
            canvas.drawText("44", outRadius + outBorderRadius, outBorderRadius, textPaint);
            canvas.restore();

            canvas.rotate(-30, outRadius + outBorderRadius, outRadius + outBorderRadius);
        }
        canvas.restore();
    }

    private void drawBackground(Canvas canvas) {
        outStrokePath.reset();
        outStrokePath.moveTo(outRadius, outBorderRadius);
        outStrokePath.lineTo(4 * outRadius, outBorderRadius);
        circleRectF.left = (float) (3 * outRadius) + outBorderRadius;
        circleRectF.top = outBorderRadius;
        circleRectF.right = (float) (5 * outRadius) - outBorderRadius;
        circleRectF.bottom = height - outBorderRadius;
        outStrokePath.arcTo(circleRectF, -90, 180);
        outStrokePath.lineTo(outRadius, height - outBorderRadius);
        circleRectF.left = outBorderRadius;
        circleRectF.top = outBorderRadius;
        circleRectF.right = height - outBorderRadius;
        circleRectF.bottom = height - outBorderRadius;
        outStrokePath.arcTo(circleRectF, 90, 180);

        outStrokePaint.setColor(Color.parseColor("#66000000"));
        canvas.drawPath(outStrokePath, outStrokePaint);
    }
}
