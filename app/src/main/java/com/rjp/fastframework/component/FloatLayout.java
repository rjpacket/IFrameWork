package com.rjp.fastframework.component;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

public class FloatLayout extends FrameLayout {

    private int width;
    private int height;

    private Handler handler = new Handler(Looper.getMainLooper());
    private int vTemp = 0;

    private Paint paint;
    private Context mContext;
    private FloatBean currentBean;
    private ItemView currentView;
    private ItemView lastView;

    public FloatLayout(Context context) {
        this(context, null);
    }

    public FloatLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context) {
        mContext = context;

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(20);

        new Thread(floatThread).start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    /**
     * 维持一个队列
     */
    private LinkedBlockingQueue<FloatBean> mQueue = new LinkedBlockingQueue<>();

    private LinkedList<ItemView> mCache = new LinkedList<>();

    private Runnable floatThread = new Runnable() {

        @Override
        public void run() {
            while (true) {
                try {
                    currentBean = mQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.post(mainThread);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private Runnable mainThread = new Runnable() {
        @Override
        public void run() {
            addItemView();
        }
    };

    /**
     * 添加一个View
     */
    private void addItemView() {
        lastView = currentView;
        if(mCache.size() > 0){
            currentView = mCache.removeFirst();
            Log.d("------>", "取缓存View");
        }else{
            currentView = null;
        }
        if (currentView == null) {
            currentView = new ItemView(mContext);
            LayoutParams params = new LayoutParams(400, 200);
            currentView.setText(String.valueOf(new Random().nextInt()));
            currentView.setLayoutParams(params);
            addView(currentView);
            Log.d("------>", "使用的新建View");
        }else{
            LayoutParams params = (LayoutParams) currentView.getLayoutParams();
            params.topMargin = height;
            currentView.setLayoutParams(params);
            currentView.setVisibility(VISIBLE);
            Log.d("------>", "使用的缓存View");
        }
        if(lastView != null) {
            if(lastView.getmTop() < height) {
                lastView.bind(currentView);
            }
            currentView.startAnim(height, height / 2, 1800);
        }else{
            currentView.startAnim(height, height / 2, 1800);
        }
        currentView.setOnDismissListener(new ItemView.OnDismissListener() {
            @Override
            public void onDismiss(ItemView view) {
                Log.d("------>", "缓存View");
                mCache.addLast(view);
            }
        });
    }

    /**
     * 添加一个bean
     * @param bean
     */
    public void addBean(FloatBean bean) {
        if (bean != null) {
            mQueue.add(bean);
        }
    }
}
