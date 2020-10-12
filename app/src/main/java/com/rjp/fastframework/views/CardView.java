package com.rjp.fastframework.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.rjp.fastframework.R;

/**
 * author: jinpeng.ren create at 2020/3/26 19:49
 * email: jinpeng.ren@11bee.com
 */
public class CardView extends LinearLayout {

    public Context mContext;
    private LayoutInflater mLayoutInflater;

    private float rate = 1.0f;
    private int leftX = 0;
    private int topY = 0;
    private int cardWidth;

    public CardView(Context context) {
        this(context, null);
    }

    public CardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        initView();
    }

    private void initView() {
        setOrientation(VERTICAL);
        setBackgroundColor(Color.WHITE);
        mLayoutInflater.inflate(R.layout.layout_card_view_title_view, this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        cardWidth = MeasureSpec.getSize(widthMeasureSpec);
        int cardHeight = MeasureSpec.getSize(heightMeasureSpec);
    }

    protected View onCreateView(ViewGroup viewGroup){
        View contentView = mLayoutInflater.inflate(R.layout.layout_card_view_content_view, null);
        return contentView;
    }

    private void addTitleView() {
        View titleView = mLayoutInflater.inflate(R.layout.layout_card_view_title_view, null);
        addView(titleView);
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
        setPivotX(0);
        setPivotY(0);
        setScaleX(rate);
        setScaleY(rate);
    }

    public int getLeftX() {
        return leftX;
    }

    public void setLeftX(int leftX) {
        this.leftX = leftX;
    }

    public int getTopY() {
        return topY;
    }

    public void setTopY(int topY) {
        this.topY = topY;
    }
}
