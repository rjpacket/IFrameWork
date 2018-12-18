package com.rjp.expandframework.views.moreScrollView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * author : Gimpo create on 2018/12/17 19:56
 * email  : jimbo922@163.com
 */
public class ScrollMoreView extends LinearLayout {

    private Context mContext;
    private int mCurrntIndex;
    private ScrollableScrollView beforeScrollView;
    private ScrollableScrollView afterScrollView;
    private ScrollableScrollView mCurrentScrollView;
    private int lastX;
    private int lastY;
    private boolean isDragAfter = false;
    private int childCount;


    public ScrollMoreView(Context context) {
        this(context, null);
    }

    public ScrollMoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;



    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        childCount = getChildCount();
        mCurrntIndex = 0;
        mCurrentScrollView = (ScrollableScrollView) getChildAt(mCurrntIndex);
        if(mCurrntIndex + 1 < childCount){
            afterScrollView = (ScrollableScrollView) getChildAt(mCurrntIndex + 1);
        }else{
            afterScrollView = null;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int dy = y - lastY;
                if(isDragAfter){
                    mCurrentScrollView.setTop(mCurrentScrollView.getTop() + dy);

                    if(afterScrollView != null){
                        afterScrollView.setVisibility(VISIBLE);
                        afterScrollView.setTop(afterScrollView.getTop() + dy);
                    }
                    lastY = y;
                    return false;   // 如果是拖拽自己  不分发事件
                }else {
                    if (dy < 0 && mCurrentScrollView.isScrollBottom() && afterScrollView != null) {
                        isDragAfter = true;
                    }
                    lastY = y;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isDragAfter = false;
                mCurrntIndex++;
                if(mCurrntIndex < childCount){
                    beforeScrollView = mCurrentScrollView;
                    mCurrentScrollView = (ScrollableScrollView) getChildAt(mCurrntIndex);
                    if(mCurrntIndex + 1 < childCount){
                        afterScrollView = (ScrollableScrollView) getChildAt(mCurrntIndex + 1);
                    }else{
                        afterScrollView = null;
                    }
                    beforeScrollView.setVisibility(GONE);
                    mCurrentScrollView.setVisibility(VISIBLE);
                }
                break;
        }
        return super.dispatchTouchEvent(event);
    }
}
