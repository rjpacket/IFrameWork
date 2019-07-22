package com.rjp.expandframework.views;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.rjp.expandframework.utils.AppUtil;

/**
 * author: jinpeng.ren create at 2019/7/16 16:36
 * email: jinpeng.ren@11bee.com
 */
public class BlurLinearLayout extends LinearLayout {

    private Paint mPaint;
    private RectF rect;
    private int radius = 12;
    private int width;
    private int height;
    private Xfermode SRC_IN = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    private Rect srcRect;
    private RectF dstRect;
    private Bitmap dstBitmap;
    private Paint shadowPaint;
    private RectF roundRect;

    public BlurLinearLayout(Context context) {
        this(context, null);
    }

    public BlurLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BlurLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
        radius = AppUtil.dp2px(8);

        shadowPaint = new Paint();
        shadowPaint.setAntiAlias(true);
        shadowPaint.setColor(Color.WHITE);
        shadowPaint.setStyle(Paint.Style.STROKE);
        shadowPaint.setShadowLayer(radius, 0, 0, Color.parseColor("#a5000000"));

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);  // 关闭硬件加速
        this.setWillNotDraw(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(new RectF(radius, radius, width - radius, height - radius), shadowPaint);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {

        super.dispatchDraw(canvas);

//        this.buildDrawingCache();
//        Bitmap bitmap = this.getDrawingCache();
//
//        Bitmap tmpBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        Canvas tmpCanvas = new Canvas(tmpBitmap);
//        int sc = tmpCanvas.saveLayer(0, 0, width, height, mPaint, Canvas.ALL_SAVE_FLAG);
//        initDstBitmap();
//        tmpCanvas.drawBitmap(dstBitmap, 0, 0, mPaint);
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
//        srcRect = new Rect(0, 0, width, height);
//        tmpCanvas.drawBitmap(bitmap, srcRect, dstRect, mPaint);
//        mPaint.setXfermode(null);
//        tmpCanvas.restoreToCount(sc);
//
//        canvas.drawColor(Color.WHITE);
//        canvas.drawBitmap(tmpBitmap, 0, 0, mPaint);
//
//        tmpBitmap.recycle();


    }

    private void initDstBitmap() {
        dstRect = new RectF(0, 0, width, height);
        roundRect = new RectF(radius, radius, width - radius, height - radius);
        dstBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(dstBitmap);
        canvas.drawRoundRect(roundRect, radius, radius, mPaint);
    }
}
