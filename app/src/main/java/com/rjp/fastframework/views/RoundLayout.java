package com.rjp.fastframework.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
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

            array.getColor(R.styleable.RoundLayout_round_shadow_color, Color.parseColor("#999999"));
            float dimension = array.getDimension(R.styleable.RoundLayout_round_shadow_width, 10);
            array.recycle();
        }
    }
}
