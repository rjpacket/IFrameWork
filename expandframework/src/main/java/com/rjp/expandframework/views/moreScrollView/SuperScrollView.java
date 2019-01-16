package com.rjp.expandframework.views.moreScrollView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.rjp.expandframework.utils.ScreenUtil;
import com.rjp.expandframework.utils.StatusBarUtil;

/**
 * author : Gimpo create on 2018/12/17 19:56
 * email  : jimbo922@163.com
 */
public class SuperScrollView extends LinearLayout {

    private Context mContext;
    private int mCurrntIndex;
    private ScrollableScrollView beforeScrollView;
    private ScrollableScrollView afterScrollView;
    private ScrollableScrollView currentScrollView;
    private int lastX;
    private int lastY;
    /**
     * 拽下来上一个page
     */
    private boolean isDragBefore = false;
    /**
     * 拽住了下一个page
     */
    private boolean isDragAfter = false;
    private int childCount;
    private int screenHeight;
    private int viewHeight;


    public SuperScrollView(Context context) {
        this(context, null);
    }

    public SuperScrollView(Context context, AttributeSet attrs) {
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
        beforeScrollView = currentScrollView = (ScrollableScrollView) getChildAt(mCurrntIndex);
        screenHeight = ScreenUtil.getScreenHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int childCount = getChildCount();
        viewHeight = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            viewHeight += childView.getMeasuredHeight();
        }
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), viewHeight);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int dy = y - lastY;
                int currentScrollY = getScrollY() - dy;

                if(dy > 0 && currentScrollView.isScrollBottom()){
                    isDragAfter = false;
                }
                if (isDragAfter) {
                    scrollTo(0, currentScrollY);
                    if(currentScrollY >= getIndexHeight(mCurrntIndex - 1)){
                        isDragAfter = false;
                    }
                    if(currentScrollY >= getIndexHeight(mCurrntIndex) - screenHeight + StatusBarUtil.getStatusBarHeight()){
                        mCurrntIndex++;
                        if(mCurrntIndex < childCount) {
                            beforeScrollView = currentScrollView;
                            currentScrollView = (ScrollableScrollView) getChildAt(mCurrntIndex);
                        }else{
                            mCurrntIndex--;
                        }
                    }
                    lastY = y;
                    return true;
                } else {
                    if (dy < 0 && beforeScrollView.isScrollBottom()) {
                        isDragAfter = true;
                    }
                }

                if(isDragBefore){
                    Log.d("------------>y", currentScrollY + "");
                    scrollTo(0, currentScrollY);
                    if(currentScrollY <= 0 || currentScrollY <= getIndexHeight(mCurrntIndex - 1) - screenHeight + StatusBarUtil.getStatusBarHeight()){
                        Log.d("------------>x", getIndexHeight(mCurrntIndex - 1) + "");
                        isDragBefore = false;
                    }
                    if(currentScrollY <= getIndexHeight(mCurrntIndex - 1) - screenHeight + StatusBarUtil.getStatusBarHeight()){
                        mCurrntIndex--;
                        if(mCurrntIndex >= 0) {
                            beforeScrollView = currentScrollView;
                            currentScrollView = (ScrollableScrollView) getChildAt(mCurrntIndex);
                        }else{
                            mCurrntIndex++;
                        }
                    }
                    lastY = y;
                    return true;
                }else{
                    if(dy > 0 && currentScrollView.isScrollTop()){
                        isDragBefore = true;
                    }
                }
                lastY = y;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
//                isDragAfter = false;
//                mCurrntIndex++;
//                if(mCurrntIndex < childCount){
//                    beforeScrollView = currentScrollView;
//                    currentScrollView = (ScrollableScrollView) getChildAt(mCurrntIndex);
//                    if(mCurrntIndex + 1 < childCount){
//                        afterScrollView = (ScrollableScrollView) getChildAt(mCurrntIndex + 1);
//                    }else{
//                        afterScrollView = null;
//                    }
//                    beforeScrollView.setVisibility(GONE);
//                    currentScrollView.setVisibility(VISIBLE);
//                }
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void scrollTo(int x, int y) {
        if(y < 0){
            y = 0;
        }
        if(y > viewHeight - screenHeight + StatusBarUtil.getStatusBarHeight()){
            y = viewHeight - screenHeight + StatusBarUtil.getStatusBarHeight();
        }
        super.scrollTo(x, y);
    }

    /**
     * 获取滑出界面的子view高度
     * @return
     */
    private int getIndexHeight(int index) {
        int viewHeight = 0;
        for (int i = 0; i <= index; i++) {
            View childView = getChildAt(i);
            viewHeight += childView.getMeasuredHeight();
        }
        return viewHeight;
    }

    /**
     * 往前翻页
     */
    private void pagePrevious() {
        mCurrntIndex--;
        if (mCurrntIndex >= 0) {
            afterScrollView = currentScrollView;
            currentScrollView = (ScrollableScrollView) getChildAt(mCurrntIndex);
            if (mCurrntIndex - 1 >= 0) {
                beforeScrollView = (ScrollableScrollView) getChildAt(mCurrntIndex - 1);
            } else {
                beforeScrollView = null;
            }
        } else {
            mCurrntIndex++;
        }
    }

    /**
     * 往后翻页
     */
    private void pageNext() {
        mCurrntIndex++;
        if (mCurrntIndex < childCount) {
            beforeScrollView = currentScrollView;
            currentScrollView = (ScrollableScrollView) getChildAt(mCurrntIndex);
            if (mCurrntIndex + 1 < childCount) {
                afterScrollView = (ScrollableScrollView) getChildAt(mCurrntIndex + 1);
            } else {
                afterScrollView = null;
            }
        } else {
            mCurrntIndex--;
        }
    }
}
