package com.rjp.fastframework.views;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.rjp.fastframework.R;

/**
 * author: jinpeng.ren create at 2019/8/12 11:38
 * email: jinpeng.ren@11bee.com
 */
public class SplashView extends FrameLayout {

    private Context mContext;
    private ImageView imageView;
    private OnSplashFinishedListener onSplashFinishedListener;

    public SplashView(Context context) {
        this(context, null);
    }

    public SplashView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SplashView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.layout_splash_view, this);
        imageView = findViewById(R.id.splash_image_view);
    }

    /**
     * 消失动画
     */
    private void disappearAnim(){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "alpha", 1.0f, 0.0f);
        objectAnimator.setDuration(800);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                setVisibility(GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        objectAnimator.start();
    }

    /**
     * 设置图片资源
     * @param imageResource
     */
    public void setImageResource(int imageResource){
        imageView.setImageResource(imageResource);
        startTimer();
    }

    private void startTimer() {
        CountDownTimer countDownTimer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d("===>", String.valueOf(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                disappearAnim();
                if(onSplashFinishedListener != null){
                    onSplashFinishedListener.onFinished();
                }
            }
        };
        countDownTimer.start();
    }

    public void setOnSplashFinishedListener(OnSplashFinishedListener onSplashFinishedListener) {
        this.onSplashFinishedListener = onSplashFinishedListener;
    }
}
