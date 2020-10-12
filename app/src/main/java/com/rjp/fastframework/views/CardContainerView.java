package com.rjp.fastframework.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.LinkedList;

/**
 * author: jinpeng.ren create at 2020/3/26 20:22
 * email: jinpeng.ren@11bee.com
 */
public class CardContainerView extends FrameLayout {

    public static final String TAG = "CardContainerView";

    private int currentMode = CardMode.CARD;

    private LinkedList<CardView> mCacheCardViews = new LinkedList<>();
    private int mWidth;
    private int mHeight;
    private float downY;

    private float rate = 0f;

    public CardContainerView(Context context) {
        this(context, null);
    }

    public CardContainerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardContainerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        initCardLocation();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int size = mCacheCardViews.size();
        for (int i = size - 1; i >= 0; i--) {
            CardView cardView = mCacheCardViews.get(i);
//            setScaleX(cardView.getScaleX() + 0.1f * rate * mWidth * i);
//            setScaleY(cardView.getScaleY() + 0.1f * rate * mWidth * i);


            if (rate < 0) {
                rate = 0;
            }
            int topY = (int) (cardView.getTopY() + rate * rate * 0.8 * mHeight * i * i);
            cardView.setTop(topY);
            if(rate > 1){
                rate = 1;
            }
            int leftX = (int) (cardView.getLeftX() - 0.1f * rate * mWidth * i);
            cardView.setLeft(leftX);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (currentMode == CardMode.PAGE) {
            return super.onTouchEvent(event);
        } else {
            int action = event.getAction();
            float curX = event.getX();
            float curY = event.getY();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    downY = curY;
                    break;
                case MotionEvent.ACTION_MOVE:
                    float dy = curY - downY;
                    modifyCardLocation(dy);
                    requestLayout();
                    downY = curY;
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    break;
            }

            return true;
        }
    }

    /**
     * 修改每一张卡片的左上角位置
     *
     * @param dy
     */
    private void modifyCardLocation(float dy) {
        Log.d(TAG, String.format("dy = %s", dy));
        float dRate = dy / (mHeight * 0.8f);
        Log.d(TAG, String.format("dRate = %s", dRate));
        rate += dRate;
    }

    public void addCard(CardView cardView) {
        mCacheCardViews.add(cardView);
        addView(cardView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    private void initCardLocation() {
        int size = mCacheCardViews.size();
        int layoutCount = 0;
        for (int i = size - 1; i >= 0; i--) {
            CardView cardView = mCacheCardViews.get(i);
            if (currentMode == CardMode.PAGE) {
                cardView.setRate(1.0f);
                cardView.setTop(0);
                cardView.setLeft(0);
            } else {
                if (layoutCount == 0) {
                    cardView.setRate(0.7f);
                    cardView.setTop((int) (mHeight * 0.125f));
                    cardView.setLeft((int) (mWidth * 0.15f));
                } else if (layoutCount == 1) {
                    cardView.setRate(0.65f);
                    cardView.setTop((int) (mHeight * 0.03125f));
                    cardView.setLeft((int) (mWidth * 0.175f));
                } else {
                    cardView.setRate(0.6f);
                    cardView.setTop(0);
                    cardView.setLeft((int) (mWidth * 0.2f));
                }
                layoutCount++;
            }
        }
    }

    public static class CardMode {
        public static final int CARD = 23;
        public static final int PAGE = 24;
    }
}
