package com.rjp.fastframework;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * @author qudajiang
 * @date 2018/12/3
 */
public class FlipView extends ViewGroup {

    private static final String TAG = "FlipView";

    private int mOverflingDistance;
    private VelocityTracker mVelocityTracker;
    private int mMinimumVelocity;
    private int mMaximumVelocity;
    private float mLastMotionY = 0;

    private int mTouchSlop;
    private boolean mIsBeingDragged;

    private Scroller mScroller;

    private static final Interpolator interpolater = new Interpolator() {
        @Override
        public float getInterpolation(float input) {
            input -= 1.0f;
            return input * input * input * input * input + 1.0f;
        }
    };

    public FlipView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlipView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        final ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mTouchSlop = configuration.getScaledTouchSlop();
        mOverflingDistance = configuration.getScaledOverflingDistance();
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
        mScroller = new Scroller(getContext());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childTop = t;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                int height = child.getMeasuredHeight();
                int width = child.getMeasuredWidth();
                child.layout(l, childTop, width, childTop + height);
                childTop += height;
            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
        float y = event.getY();
        int action = event.getAction();
        int scrollY = getScrollY();
        Log.d(TAG, "getScrollY====>" + scrollY);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                mLastMotionY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaY = (int) (mLastMotionY - y);

                mLastMotionY = y;
//                if (!mIsBeingDragged && Math.abs(deltaY) > mTouchSlop) {
                scrollTo(deltaY);
//                }
                break;
            case MotionEvent.ACTION_UP:
                final VelocityTracker velocityTracker = mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                int velocityY = (int) velocityTracker.getYVelocity();
                if ((Math.abs(velocityY) > mMinimumVelocity)) {
                    smoothScroll(velocityY);
                }
                if (mVelocityTracker != null) {
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }
                break;
            default:
                break;
        }
        return true;
    }

    private void scrollTo(int delatY) {
        int newScrollY = delatY + getScrollY();
        if (newScrollY > getScrollRange()) {
            newScrollY = getScrollRange();
        } else if (newScrollY < 0) {
            newScrollY = 0;
        }
        scrollTo(0, newScrollY);
    }

    private void smoothScroll(int velocityY) {
        mScroller.fling(0, getScrollY(), 0, -velocityY, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    private int getScrollRange() {
        int range = 0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            range = range + child.getMeasuredHeight();
        }
        return range - getHeight();
    }


}
