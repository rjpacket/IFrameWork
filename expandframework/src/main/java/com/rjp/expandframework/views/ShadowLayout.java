package com.rjp.expandframework.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RelativeLayout;
import com.rjp.expandframework.R;

public class ShadowLayout extends RelativeLayout {

    public static final int ALL = 0x1111;

    public static final int LEFT = 0x0001;

    public static final int TOP = 0x0010;

    public static final int RIGHT = 0x0100;

    public static final int BOTTOM = 0x1000;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private RectF mRectF = new RectF();

    /**
     * 阴影的颜色
     */
    private int mShadowColor = Color.TRANSPARENT;

    /**
     * 阴影的大小范围
     */
    private float mShadowRadius = 0;

    /**
     * 阴影 x 轴的偏移量
     */
    private float mShadowDx = 0;

    /**
     * 阴影 y 轴的偏移量
     */
    private float mShadowDy = 0;

    /**
     * 阴影显示的边界
     */
    private int mShadowSide = ALL;
    private int padding;

    public ShadowLayout(Context context) {
        this(context, null);
    }

    public ShadowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShadowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    /**
     * 读取设置的阴影的属性
     *
     * @param attrs 从其中获取设置的值
     */
    private void init(AttributeSet attrs) {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);  // 关闭硬件加速
        this.setWillNotDraw(false);                    // 调用此方法后，才会执行 onDraw(Canvas) 方法

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ShadowLayout);
        if (typedArray != null) {
            mShadowColor = typedArray.getColor(R.styleable.ShadowLayout_sl_shadowColor,
                    ContextCompat.getColor(getContext(), android.R.color.black));
            mShadowRadius = typedArray.getDimension(R.styleable.ShadowLayout_sl_shadowRadius, dip2px(0));
            mShadowDx = typedArray.getDimension(R.styleable.ShadowLayout_sl_shadowDx, dip2px(0));
            mShadowDy = typedArray.getDimension(R.styleable.ShadowLayout_sl_shadowDy, dip2px(0));
            mShadowSide = typedArray.getInt(R.styleable.ShadowLayout_sl_shadowSide, ALL);
            typedArray.recycle();
        }
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.TRANSPARENT);
        mPaint.setShadowLayer(mShadowRadius, mShadowDx, mShadowDy, mShadowColor);

        padding = (int) (mShadowRadius + dip2px(4));
        int paddingRight = padding;
        int paddingBottom = padding;
        if (mShadowDy != 0.0f) {
            paddingBottom = (int) (paddingBottom + mShadowDy);
        }
        if (mShadowDx != 0.0f) {
            paddingRight = (int) (paddingRight + mShadowDx);
        }
        this.setPadding(padding, padding, paddingRight, paddingBottom);
    }

    /**
     * 获取绘制阴影的位置，并为 ShadowLayout 设置 Padding 以为显示阴影留出空间
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (((mShadowSide & LEFT) == LEFT)) {
            mRectF.left = padding;
        }
        if (((mShadowSide & TOP) == TOP)) {
            mRectF.top = padding;
        }
        if (((mShadowSide & RIGHT) == RIGHT)) {
            mRectF.right = getWidth() - padding;
        }
        if (((mShadowSide & BOTTOM) == BOTTOM)) {
            mRectF.bottom = getHeight() - padding;
        }
    }

    /**
     * 真正绘制阴影的方法
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(mRectF, mPaint);
    }

    /**
     * dip2px dp 值转 px 值
     *
     * @param dpValue dp 值
     * @return px 值
     */
    private float dip2px(float dpValue) {
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        float scale = dm.density;
        return (dpValue * scale + 0.5F);
    }
}