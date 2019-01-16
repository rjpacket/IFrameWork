//package com.rjp.fastframework.scroll_view;
//
//import android.content.Context;
//import android.os.SystemClock;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.view.NestedScrollingParent;
//import android.support.v4.view.NestedScrollingParent2;
//import android.support.v4.view.NestedScrollingParentHelper;
//import android.support.v4.view.ViewCompat;
//import android.support.v7.widget.RecyclerView;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.MotionEvent;
//import android.view.VelocityTracker;
//import android.view.View;
//import android.view.ViewConfiguration;
//import android.view.ViewGroup;
//import android.view.animation.Interpolator;
//import android.widget.Scroller;
//
///**
// * @author qudajiang
// * @date 2018/12/3
// */
//public class FlipView extends ViewGroup{
//
//    private static final String TAG = "FlipView";
//
//    private int mOverflingDistance;
//    private VelocityTracker mVelocityTracker;
//    private int mMinimumVelocity;
//    private int mMaximumVelocity;
//    private float mLastMotionY = 0;
//
//    private int mTouchSlop;
//    private boolean mIsBeingDragged;
//
//    private Scroller mScroller;
//
//    private static final Interpolator interpolater = new Interpolator() {
//        @Override
//        public float getInterpolation(float input) {
//            input -= 1.0f;
//            return input * input * input * input * input + 1.0f;
//        }
//    };
//    private IFlipPage mPageTop;
//    private IFlipPage mPageBottom;
//    private RecyclerView mPage1;
//    private MyScrollView mPage2;
//    private RecyclerView mPage3;
//
//
//    public interface IFlipPage {
//
//        /**
//         * 是否滑动到最顶端
//         */
//        boolean isFlipToTop();
//
//        /**
//         * 是否滑动到最底部
//         */
//        boolean isFlipToBottom();
//    }
//
//    public FlipView(Context context, AttributeSet attrs) {
//        this(context, attrs, 0);
//    }
//
//    public FlipView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        init();
//    }
//
//    public void setFlipView(View view1, View view2, View view3) {
//        view1.setId(1);
//        view2.setId(2);
//        view3.setId(3);
//        mPage1 = (RecyclerView) view1;
//        mPage2 = (MyScrollView) view2;
//        mPage3 = (RecyclerView) view3;
//    }
//
//    private void init() {
//        final ViewConfiguration configuration = ViewConfiguration
//                .get(getContext());
//        mTouchSlop = configuration.getScaledTouchSlop();
//        mOverflingDistance = configuration.getScaledOverflingDistance();
//        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
//        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
//        mScroller = new Scroller(getContext());
//    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        measureChildren(widthMeasureSpec, heightMeasureSpec);
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }
//
//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        int childTop = t;
//        int childCount = getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            View child = getChildAt(i);
//            if (child.getVisibility() != GONE) {
//                int height = child.getMeasuredHeight();
//                int width = child.getMeasuredWidth();
//                child.layout(l, childTop, width, childTop + height);
//                childTop += height;
//            }
//        }
//    }
//
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//        return super.dispatchTouchEvent(event);
//    }
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent event) {
//
//        return true;
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if (mVelocityTracker == null) {
//            mVelocityTracker = VelocityTracker.obtain();
//        }
//        mVelocityTracker.addMovement(event);
//        float y = event.getY();
//        int action = event.getAction();
//        int scrollY = getScrollY();
////        Log.d(TAG, "getScrollY====>" + scrollY);
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                if (!mScroller.isFinished()) {
//                    mScroller.abortAnimation();
//                }
//                mLastMotionY = y;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                int deltaY = (int) (mLastMotionY - y);
//
//                mLastMotionY = y;
//                dealScrollDelta(deltaY);
////                scrollTo(0, getScrollY() + deltaY);
//                break;
//            case MotionEvent.ACTION_UP:
//                final VelocityTracker velocityTracker = mVelocityTracker;
//                velocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
//                int velocityY = (int) velocityTracker.getYVelocity();
//                if ((Math.abs(velocityY) > mMinimumVelocity)) {
//                    smoothScroll(velocityY);
//                }
//                if (mVelocityTracker != null) {
//                    mVelocityTracker.recycle();
//                    mVelocityTracker = null;
//                }
//                break;
//            default:
//                break;
//        }
//        return true;
//    }
//
//    private int getScrollChildRemain(){
//        int remain = 0;
//        int i = getScrollY() / getHeight();
//
//        return remain;
//    }
//
//    private void dealScrollDelta(int dy){
//        if (dy == 0){
//            return;
//        }
//        int i = getScrollY() / getHeight();
//        if (getScrollY() == 0){
//            int scrollRange = mPage1.computeVerticalScrollRange();
//            int scrollOffset = mPage1.computeVerticalScrollOffset();
//            int scrollExtent = mPage1.computeVerticalScrollExtent();
//            int remain;
//            if (dy < -scrollOffset){
//                mPage1.scrollBy(0,-scrollOffset);
//                remain = dy - (-scrollOffset);
//            }else if (dy > scrollRange - scrollExtent - scrollOffset){
//                mPage1.scrollBy(0,scrollRange - scrollExtent - scrollOffset);
//                remain = dy - (scrollRange - scrollExtent - scrollOffset);
//            }else {
//                mPage1.scrollBy(0,dy);
//                remain = 0;
//            }
//            scrollParent(remain);
//        }else if (getScrollY() == getHeight()){
//            int scrollRange = mPage2.computeVerticalScrollRange();
//            int scrollOffset = mPage2.computeVerticalScrollOffset();
//            int scrollExtent = mPage2.computeVerticalScrollExtent();
//            int remain;
//            if (dy < -scrollOffset){
//                mPage2.scrollBy(0,-scrollOffset);
//                remain = dy - (-scrollOffset);
//            }else if (dy > scrollRange - scrollExtent - scrollOffset){
//                mPage2.scrollBy(0,scrollRange - scrollExtent - scrollOffset);
//                remain = dy - (scrollRange - scrollExtent - scrollOffset);
//            }else {
//                mPage2.scrollBy(0,dy);
//                remain = 0;
//            }
//            scrollParent(remain);
//        }else if (getScrollY() == getHeight()*2){
//            int scrollRange = mPage3.computeVerticalScrollRange();
//            int scrollOffset = mPage3.computeVerticalScrollOffset();
//            int scrollExtent = mPage3.computeVerticalScrollExtent();
//            int remain;
//            if (dy < -scrollOffset){
//                mPage3.scrollBy(0,-scrollOffset);
//                remain = dy - (-scrollOffset);
//            }else if (dy > scrollRange - scrollExtent - scrollOffset){
//                mPage3.scrollBy(0,scrollRange - scrollExtent - scrollOffset);
//                remain = dy - (scrollRange - scrollExtent - scrollOffset);
//            }else {
//                mPage3.scrollBy(0,dy);
//                remain = 0;
//            }
//            scrollParent(remain);
//        }else {
//            scrollParent(dy);
//        }
//    }
//
//
//    private void scrollParent(int dy) {
//        if (dy == 0){
//            return;
//        }
//        int remain;
//        int scrollY = getScrollY();
//        int height = getHeight();
//
//        int topLimit;
//        if (scrollY % height == 0){
//            topLimit = -height;
//        }else {
//            topLimit = - scrollY % height;
//        }
//
//        int bottomLimit = height - scrollY % height;
//
//        if (dy > bottomLimit) {
//            scrollBy(0, bottomLimit);
//            remain = dy - bottomLimit;
//        } else if (dy < topLimit) {
//            scrollBy(0, topLimit);
//            remain = dy - topLimit;
//        }else {
//            scrollBy(0,dy);
//            remain = 0;
//        }
//        dealScrollDelta(remain);
//    }
//
//    @Override
//    public void scrollTo(int x, int y) {
//
//        if (y < 0) {
//            y = 0;
//        }
//        if (y > getScrollRange()) {
//            y = getScrollRange();
//        }
//        super.scrollTo(x, y);
//
//    }
//
//    private void smoothScroll(int velocityY) {
//        mScroller.fling(0, 0, 0, -velocityY, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
////        mScroller.startScroll(0,0,0,-velocityY/10,2000);
//        lastCurrY = 0;
//        postInvalidate();
//    }
//    private int lastCurrY;
//
//
//    @Override
//    public void computeScroll() {
//        if (mScroller.computeScrollOffset()) {
//            int i = mScroller.getCurrY()- lastCurrY;
//            dealScrollDelta(i);
//            lastCurrY = mScroller.getCurrY();
////            dealScrollDelta(mScroller.getCurrY());
////            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
//            invalidate();
//        }
//    }
//
//    private int getScrollRange() {
//        int range = 0;
//        int childCount = getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            View child = getChildAt(i);
//            range = range + child.getMeasuredHeight();
//        }
//        return range - getHeight();
//    }
//
////    @Override
////    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
////        return true;
////    }
////
////
////    @Override
////    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
////
//////        Log.d(TAG,"comsume1---:"+consumed[0]+" comsume2---:"+consumed[1]);
//////        Log.d(TAG,"target-----"+target);
//////        Log.d(TAG,"dy---------:"+dy);
////        if (getScrollY() != 0 && getScrollY() != getHeight() && getScrollY() != getHeight() * 2){
////            if (getScrollY() > 0 && getScrollY() < getHeight()){
////                if (dy + getScrollY() < 0){
////                    scrollTo(0,0);
////                    consumed[1] = getScrollY();
//////                    target = mPage1;
////                }else if (dy + getScrollY() > getHeight()){
////                    scrollTo(0,getHeight());
////                    consumed[1] = getHeight() - getScrollY();
//////                    target = mPage2;
////                }else {
////                    scrollTo(0,dy+getScrollY());
////                    consumed[1] = dy;
////                }
////            }else if (getScrollY() > getHeight() && getScrollY() < getHeight() * 2){
////               if (dy + getScrollY() < getHeight()){
////                   scrollTo(0,getHeight());
////                   consumed[1] = getScrollY() - getHeight();
//////                   target = mPage2;
////               }else if (dy + getScrollY() > getHeight() * 2){
////                   scrollTo(0,getHeight() * 2);
////                   consumed[1] = getHeight() * 2 - getScrollY();
//////                   target = mPage3;
////               }else {
////                   scrollTo(0,dy+getScrollY());
////                   consumed[1] = dy;
////               }
////            }
////        }
////
////        super.onNestedPreScroll(target, dx, dy, consumed);
////    }
////
////    @Override
////    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
////
////
////        if (getScrollY() == 0){
////            if (target == mPage1){
////                if (dyUnconsumed > getHeight()){
////                    scrollTo(0,getScrollY()+getHeight());
////                    int parentScrollRemain = dyUnconsumed - getHeight();
////                    //交给第二屏
////                    mPage2.scrollBy(0,parentScrollRemain);
////
////                }else if (dyUnconsumed < -getHeight()){
////                    //到顶了，不需要交了
////
////                }else if (Math.abs(dyUnconsumed) < getHeight()){
////                    //不满一屏，让父view滚动，不需要移交
////                    scrollTo(0,getScrollY()+dyUnconsumed);
////                }
////            }else if (target == mPage2){
////                //在第一屏，又是第二屏向上剩余的滑动，那么需要把剩余的dyUnconsumed传给第一屏子View
////                mPage1.scrollBy(0,dyUnconsumed);
////            }
////        }else if (getScrollY() == getHeight()){
////            if (target == mPage1 || target == mPage3){
////                //在第二屏，又是第一屏剩余的滑动，那么需要把剩余的dyUnconsumed传给第二屏子View
////                mPage2.scrollBy(0,dyUnconsumed);
////            }else if (target == mPage2){
////                if (Math.abs(dyUnconsumed) < getHeight()){
////                    scrollTo(0,getScrollY()+dyUnconsumed);
////                }else if (dyUnconsumed > getHeight()){
////                    //如果第二屏剩余的滑动，并且大于一屏，让其滚动一屏后，把剩余的再交给第三屏子View
////                    scrollTo(0,getScrollY()+getHeight());
////                    int parentScrollRemain = dyUnconsumed - getHeight();
////                    mPage3.scrollBy(0,parentScrollRemain);
////                }else if (dyUnconsumed < -getHeight()){
////                    scrollTo(0,getScrollY() - getHeight());
////                    int parentScrollRemain = dyConsumed + getHeight();
////                    //交给第一屏子View
////                    mPage1.scrollBy(0,parentScrollRemain);
////                }
////            }
////        }else if (getScrollY() == getHeight() * 2){
////            if (target == mPage2){
////                //交给第三屏
////                mPage3.scrollBy(0,dyUnconsumed);
////            }else if (target == mPage3){
////                if (dyUnconsumed > getHeight()){
////                    //到底不用处理
////
////                }else if (dyUnconsumed < -getHeight()){
////                    scrollTo(0,getScrollY() - getHeight());
////                    int parentScrollRemain = dyUnconsumed - getHeight();
////                    //交给第二屏
////                    mPage2.scrollBy(0,parentScrollRemain);
////                }else if (Math.abs(dyUnconsumed) < getHeight()){
////                    //不满一屏，让父view滚动，不需要移交
////                    scrollTo(0,getScrollY()+dyUnconsumed);
////                }
////            }
////        }
////
//////        scrollTo(0,getScrollY()+dyUnconsumed);
//////        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
////    }
//
////    NestedScrollingParentHelper mParentHelper = new NestedScrollingParentHelper(this);
////
////    @Override
////    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
////        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
////    }
////
////    @Override
////    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {
////        mParentHelper.onNestedScrollAccepted(child, target, axes, type);
////    }
////
////    @Override
////    public void onStopNestedScroll(@NonNull View target, int type) {
////        mParentHelper.onStopNestedScroll(target, type);
////    }
////
////    @Override
////    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
////        Log.d(TAG, target.getClass().getSimpleName()
////                +"----dyConsumed:" +  dyConsumed
////                + " dyUnconsumed:" + dyUnconsumed
////                +" scrollY:"+getScrollY()
////                +" Height:" +getHeight()
////                + " "+(type==ViewCompat.TYPE_TOUCH));
////
////        //某个子View的滚动距离在被父View消费后，并且自己也消费后，仍然剩余的dy
////        //1.如果是当前屏幕内的子View剩余的，那么此时应该滚动外部了，只能让父View消费这个dy。消费不完的，仍要给当前屏幕子View
////        //2.如果是屏幕外的子View剩余的，那么就应该看当前屏幕内部是否要滚动，即让当前屏幕内的子View消费dy
////
////
////        if (dyUnconsumed == 0 || type == ViewCompat.TYPE_NON_TOUCH){
////            return;
////        }
////        if (getScrollY() == 0){//==0则当前在第一屏page1
////            //如果是page1剩余的，滚动父View
////            if (target == mPage1){
////                if (dyUnconsumed > getHeight()){
////                    scrollTo(0,getScrollY()+getHeight());
////                    //剩余的交给下一屏的子View
////                    mPage2.scrollBy(0,dyUnconsumed - getHeight());
////                }else if (dyUnconsumed < -getHeight()){
////                    scrollTo(0,getScrollY()-getHeight());
////
////                }else {
////                    scrollTo(0,getScrollY()+dyUnconsumed);
////                }
////            }else {//其他屏剩余的，交个当前屏子View
////                mPage1.scrollBy(0,dyUnconsumed);
////            }
////        }else if (getScrollY() == getHeight()){//在第二屏 page2
////            if (target == mPage2){
////                if (dyUnconsumed > getHeight()){//超过一屏，父View滚完交给下一屏
////                    scrollTo(0,getScrollY()+getHeight());
////                    mPage3.scrollBy(0,dyUnconsumed - getHeight());
////                }else if (dyUnconsumed < -getHeight()){//超过一屏，父View滚完交给上一屏
////                    scrollTo(0,getScrollY() - getHeight());
////                    mPage1.scrollBy(0,dyUnconsumed + getHeight());
////                }else {
////                    //如果剩余的没超过一屏。父View自己滚就行了
////                    scrollTo(0,getScrollY()+dyUnconsumed);
////                }
////            }else {//其他屏剩余的，交给当前屏子View
////                mPage2.scrollBy(0,dyUnconsumed);
////            }
////        }else if (getScrollY() == getHeight() * 2){//在第三屏 page3
////            if (target == mPage3){
////                if (dyUnconsumed > getHeight()){
////                    scrollTo(0,getScrollY()+getHeight());
////
////                }else if (dyUnconsumed < -getHeight()){
////                    scrollTo(0,getScrollY()-getHeight());
////                    //剩余的交给上一屏的子View
////                    mPage1.scrollBy(0,dyUnconsumed - getHeight());
////                }else {
////                    scrollTo(0,getScrollY()+dyUnconsumed);
////                }
////            }else {
////                mPage3.scrollBy(0,dyUnconsumed);
////            }
////        }
////
////    }
////
////    @Override
////    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
////        if (type == ViewCompat.TYPE_TOUCH) {
////            if (getScrollY() != 0 && getScrollY() != getHeight() && getScrollY() != getHeight() * 2) {
////                if (getScrollY() > 0 && getScrollY() < getHeight()) {
////                    if (dy + getScrollY() < 0) {
////                        scrollTo(0, 0);
////                        consumed[1] = getScrollY();
//////                    target = mPage1;
////                    } else if (dy + getScrollY() > getHeight()) {
////                        scrollTo(0, getHeight());
////                        consumed[1] = dy - (getHeight() - getScrollY());
//////                    target = mPage2;
////                    } else {
////                        scrollTo(0, dy + getScrollY());
////                        consumed[1] = dy;
////                    }
////                } else if (getScrollY() > getHeight() && getScrollY() < getHeight() * 2) {
////                    if (dy + getScrollY() < getHeight()) {
////                        scrollTo(0, getHeight());
////                        consumed[1] = getScrollY() - getHeight();
//////                   target = mPage2;
////                    } else if (dy + getScrollY() > getHeight() * 2) {
////                        scrollTo(0, getHeight() * 2);
////                        consumed[1] = dy - (getHeight() * 2 - getScrollY());
//////                   target = mPage3;
////                    } else {
////                        scrollTo(0, dy + getScrollY());
////                        consumed[1] = dy;
////                    }
////                }
////            }
////        }
//////        super.onNestedPreScroll(target, dx, dy, consumed);
////    }
////
////    @Override
////    public boolean onNestedPreFling(@NonNull View target, float velocityX, float velocityY) {
////        Log.d(TAG, velocityY + "总共的惯性距离");
//////        return true;
////        if (getScrollY() == 0 || getScrollY() == getHeight() || getScrollY() == getHeight()*2){
////            return false;
////        }
////        if (getScrollY() > 0 && getScrollY() < getHeight()) {
////            if (velocityY + getScrollY() < 0) {
////                smoothScroll(-getScrollY());
//////                consumed[1] = getScrollY();
//////                    target = mPage1;
////            } else if (velocityY + getScrollY() > getHeight()) {
////                smoothScroll(getHeight() - getScrollY());
//////                consumed[1] = dy - (getHeight() - getScrollY());
//////                    target = mPage2;
////            } else {
////                smoothScroll(-(int)velocityY);
//////                consumed[1] = dy;
////            }
////        } else if (getScrollY() > getHeight() && getScrollY() < getHeight() * 2) {
////            if (velocityY + getScrollY() < getHeight()) {
////                smoothScroll(-(getScrollY() - getHeight()));
//////                consumed[1] = getScrollY() - getHeight();
//////                   target = mPage2;
////            } else if (velocityY + getScrollY() > getHeight() * 2) {
////                smoothScroll(getHeight()*2 - getScrollY());
//////                consumed[1] = dy - (getHeight() * 2 - getScrollY());
//////                   target = mPage3;
////            } else {
////                smoothScroll(-(int) velocityY);
//////                consumed[1] = dy;
////            }
////        }
////
////        return true;//返回false则子View不处理
////    }
////
////
////    @Override
////    public boolean onNestedFling(@NonNull View target, float velocityX, float velocityY, boolean consumed) {
//////        smoothScroll(-(int)velocityY);
////        Log.d(TAG, velocityY + "剩余的惯性?????");
////        if (!consumed) {
////            Log.d(TAG, "子View不能滑动了");
////        }
////        return super.onNestedFling(target, velocityX, velocityY, consumed);
////    }
//}
