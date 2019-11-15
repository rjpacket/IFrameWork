package com.rjp.expandframework.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * author: jinpeng.ren create at 2019/11/11 17:34
 * email: jinpeng.ren@11bee.com
 */
public class BlackBoyTextView extends View {

    private Context mContext;
    private Paint mPaint;
    private Paint mMoneyPaint;
    private Paint mDoublePaint;
    private Paint mMultiplePaint;

    public BlackBoyTextView(Context context) {
        this(context, null);
    }

    public BlackBoyTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BlackBoyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        mPaint = new Paint();

        mMoneyPaint = new Paint();
        mMoneyPaint.setTextSize(16);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
}
