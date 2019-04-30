package com.rjp.fastframework.component;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

public class ItemView extends android.support.v7.widget.AppCompatButton {
    private int vo;
    private int mTop;
    private Handler handler = new Handler(Looper.getMainLooper());
    private AnimatorSet set;
    private float alpha = 255;
    private OnDismissListener onDismissListener;
    private ItemView bindView;

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    public int getmTop() {
        return mTop;
    }

    public float getVo() {
        return vo;
    }

    public ItemView(Context context) {
        this(context, null);
    }

    public ItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
    }

    /**
     * 开启动画
     * @param start
     * @param end
     * @param time
     */
    public void startAnim(int start, int end, long time) {
        //初始化透明度
        alpha = 255;
        setAlpha(1.0f);

        //开启速度动画
        ValueAnimator vAnim = ValueAnimator.ofInt(10, 1);
        vAnim.setDuration(time);
        vAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                vo = (int) animation.getAnimatedValue();
            }
        });

        //开启view上浮动画
        ValueAnimator topAnim = ValueAnimator.ofInt(start, end);
        topAnim.setDuration(time);
        topAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mTop = (Integer) animation.getAnimatedValue();
                changeTop();
            }
        });

        set = new AnimatorSet();
        set.play(vAnim).with(topAnim);
        set.start();
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                handler.post(alphaThread);
                handler.post(topThread);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public Runnable alphaThread = new Runnable() {
        @Override
        public void run() {
            //透明了结束刷新
            if(alpha > 0) {
                changeAlpha();
                handler.postDelayed(alphaThread, 60);
            }
        }
    };

    public Runnable topThread = new Runnable() {
        @Override
        public void run() {
            //透明了结束刷新
            if(alpha > 0) {
                changeTop();
                handler.postDelayed(topThread, 60);
            }
        }
    };

    /**
     * 修改透明度
     */
    private void changeAlpha() {
        alpha -= vo;
        if(alpha <= 0){
            alpha = 0;
            unbind();
            handler.removeCallbacks(alphaThread);
            if(onDismissListener != null){
                onDismissListener.onDismiss(this);
            }
        }
        setAlpha(alpha / 255);
    }

    /**
     * 修改高度
     */
    public void changeTop() {
        mTop -= vo;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getLayoutParams();
        layoutParams.topMargin = mTop;
        setLayoutParams(layoutParams);
    }

    public void stopTopAnim(){
        set.cancel();
        handler.removeCallbacks(topThread);
    }

    private OnLayoutChangeListener layoutChangeListener = new OnLayoutChangeListener() {
        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            if (top < mTop + getMeasuredHeight() + 20) {
                stopTopAnim();

                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getLayoutParams();
                layoutParams.topMargin = top - getMeasuredHeight() - 20;
            }
        }
    };

    private void unbind() {
        if(bindView != null){
            bindView.removeOnLayoutChangeListener(layoutChangeListener);
        }
    }

    public void bind(ItemView currentView) {
        bindView = currentView;
        bindView.addOnLayoutChangeListener(layoutChangeListener);
    }

    public interface OnDismissListener{
        void onDismiss(ItemView view);
    }
}
