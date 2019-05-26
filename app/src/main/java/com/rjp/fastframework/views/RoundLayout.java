package com.rjp.fastframework.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.rjp.fastframework.R;

public class RoundLayout extends FrameLayout {

    private int mShadowColor;
    private float mShadowWidth;
    private float dx;
    private float dy;
    private float mBorderRadius;
    private int mBorderColor;
    private float mBorderWidth;
    private int mShadowSides;

    public static final int FLAG_LEFT = 1;
    public static final int FLAG_TOP = 2;
    public static final int FLAG_RIGHT = 4;
    public static final int FLAG_BOTTOM = 8;
    public static final int FLAG_ALL = 15;
    private PorterDuffXfermode mXfermode;
    private RectF mContentRect;
    private RectF mBorderRect;

    private Paint mPaint;
    private Path mPath;

    public RoundLayout(Context context) {
        this(context, null);
    }

    public RoundLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RoundLayout);
        if(array != null){

            mShadowColor = array.getColor(R.styleable.RoundLayout_round_shadow_color, Color.parseColor("#999999"));
            mShadowWidth = array.getDimension(R.styleable.RoundLayout_round_shadow_width, 10);
            dx = array.getDimension(R.styleable.RoundLayout_round_dx, 5);
            dx = array.getDimension(R.styleable.RoundLayout_round_dy, 5);
            mBorderRadius = array.getDimension(R.styleable.RoundLayout_round_border_radius, 5);
            mBorderWidth = array.getDimension(R.styleable.RoundLayout_round_border_width, 5);
            mBorderColor = array.getColor(R.styleable.RoundLayout_round_border_color, Color.parseColor("#ff0000"));
            mShadowSides = array.getInt(R.styleable.RoundLayout_round_shadow_sides, 15);

            array.recycle();
        }

        processPadding();
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);

        setWillNotDraw(false);
        mPaint = new Paint();
    }

    /**
     * 设置padding
     */
    private void processPadding() {
        float xPadding = mShadowWidth + Math.abs(dx);
        float yPadding = mShadowWidth + Math.abs(dy);

        int left = (mShadowSides & FLAG_LEFT) == FLAG_LEFT ? (int) xPadding : 0;
        int top = (mShadowSides & FLAG_TOP) == FLAG_TOP ? (int) yPadding : 0;
        int right = (mShadowSides & FLAG_RIGHT) == FLAG_RIGHT ? (int) xPadding : 0;
        int bottom = (mShadowSides & FLAG_BOTTOM) == FLAG_BOTTOM ? (int) yPadding : 0;

        if((mShadowSides & FLAG_ALL) == FLAG_ALL){
            left = (int) xPadding;
            top = (int) yPadding;
            right = (int) xPadding;
            bottom = (int) yPadding;
        }

        setPadding(left, top, right, bottom);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mContentRect = new RectF(getPaddingLeft(), getPaddingTop(), w - getPaddingRight(), h - getPaddingBottom());

        mBorderRect = new RectF(mContentRect.left, mContentRect.top, mContentRect.right, mContentRect.bottom);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        drawShadow(canvas);

        canvas.saveLayer(0, 0, canvas.getWidth(), canvas.getHeight(), mPaint, Canvas.ALL_SAVE_FLAG);

        super.dispatchDraw(canvas);
        mPath = new Path();
        mPath.addRect(mContentRect, Path.Direction.CW);
        mPath.addRoundRect(mContentRect, mBorderRadius, mBorderRadius, Path.Direction.CW);
        mPath.setFillType(Path.FillType.EVEN_ODD);
        mPaint.setXfermode(mXfermode);
        canvas.drawPath(mPath, mPaint);
        mPaint.reset();
        mPath.reset();

        canvas.restore();

        drawBorder(canvas);
    }

    private void drawBorder(Canvas canvas) {
        canvas.save();

        mPaint.setStrokeWidth(mBorderWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mBorderColor);
        canvas.drawRoundRect(mBorderRect, mBorderRadius, mBorderRadius, mPaint);
        mPaint.reset();

        canvas.restore();
    }

    private void drawShadow(Canvas canvas) {
        canvas.save();

        mPaint.setShadowLayer(mShadowWidth, dx, dy, mShadowColor);
        canvas.drawRoundRect(mContentRect, mBorderRadius, mBorderRadius, mPaint);
        mPaint.reset();

        canvas.restore();
    }
}
