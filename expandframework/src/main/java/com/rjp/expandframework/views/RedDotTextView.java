package com.rjp.expandframework.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import com.rjp.expandframework.R;
import com.rjp.expandframework.utils.AppUtil;

/**
 * author: jinpeng.ren create at 2019/7/15 16:43
 * email: jinpeng.ren@11bee.com
 */
public class RedDotTextView extends android.support.v7.widget.AppCompatTextView {

    private Context mContext;
    private float dotRadius = 10;
    private Paint mPaint;
    private int totalWidth;
    private int dotColor;
    private boolean showDot;

    public RedDotTextView(Context context) {
        this(context, null);
    }

    public RedDotTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RedDotTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        processAttrs(attrs);
        init();
    }

    private void processAttrs(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.RedDotTextView);
        if(typedArray != null){
            dotRadius = typedArray.getDimension(R.styleable.RedDotTextView_rtv_dot_radius, AppUtil.dp2px(3));
            dotColor = typedArray.getColor(R.styleable.RedDotTextView_rtv_dot_color, Color.RED);
            showDot = typedArray.getBoolean(R.styleable.RedDotTextView_rtv_visibility, true);

            typedArray.recycle();
        }
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        totalWidth = (int) (width + 3 * dotRadius);
        setMeasuredDimension(totalWidth, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (showDot) {
            mPaint.setColor(dotColor);
            canvas.drawCircle(totalWidth - dotRadius, dotRadius, dotRadius, mPaint);
        }
    }

    /**
     * 设置是否显示红点
     * @param showDot
     */
    public void setShowDot(boolean showDot){
        this.showDot = showDot;
        invalidate();
    }
}
