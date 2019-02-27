package com.rjp.expandframework.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.rjp.expandframework.utils.AppUtil;

/**
 * SRC_ATOP 模式
 * <p>
 * 缺点：需要生成两张临时的缩放图片  自适应图片
 * author : Gimpo create on 2018/4/24 17:27
 * email  : jimbo922@163.com
 */
public class XImageView extends android.support.v7.widget.AppCompatImageView {
    private Context mContext;
    private int width;
    private int height;
    private Bitmap srcBitmap;
    private Paint mPaint;
    private Xfermode SRC_ATOP = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
    private Bitmap targetBitmap;
    private Bitmap dstBitmap;
    private Rect srcRect;
    private RectF dstRect;
    private int radius = 5;

    public XImageView(Context context) {
        this(context, null);
    }

    public XImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        mContext = context;
        mPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
        dstBitmap = getBitmap();

//        if (srcBitmap != null) {
//            targetBitmap = zoomBitmap(srcBitmap, width, height);
//            if (srcRect == null && targetBitmap != null) {
//                srcRect = new Rect(targetBitmap.getWidth() / 2 - width / 2, targetBitmap.getHeight() / 2 - height / 2, targetBitmap.getWidth() / 2 + width / 2, targetBitmap.getHeight() / 2 + height / 2);
//                dstRect = new RectF(0, 0, width, height);
//            }
//        }
    }

    public void setSrcBitmap(Bitmap bitmap) {
        srcBitmap = bitmap;
        if (width != 0 && height != 0) {
            targetBitmap = zoomBitmap(srcBitmap, width, height);
            if (srcRect == null && targetBitmap != null) {
                srcRect = new Rect(targetBitmap.getWidth() / 2 - width / 2, targetBitmap.getHeight() / 2 - height / 2, targetBitmap.getWidth() / 2 + width / 2, targetBitmap.getHeight() / 2 + height / 2);
                dstRect = new RectF(0, 0, width, height);
            }
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(srcBitmap == null){
            BitmapDrawable drawable = (BitmapDrawable) getDrawable();
            if(drawable == null){
                return;
            }
            setSrcBitmap(drawable.getBitmap());
        }

        if (targetBitmap != null) {
            int sc = canvas.saveLayer(0, 0, width, height, mPaint, Canvas.ALL_SAVE_FLAG);
            canvas.drawBitmap(dstBitmap, 0, 0, mPaint);
            mPaint.setXfermode(SRC_ATOP);
            canvas.drawBitmap(targetBitmap, srcRect, dstRect, mPaint);
            mPaint.setXfermode(null);
            canvas.restoreToCount(sc);
        }
    }

    /**
     * 绘制形状
     *
     * @return
     */
    public Bitmap getBitmap() {
        if (width <= 0 || height <= 0) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        canvas.drawRoundRect(new RectF(0, 0, width, height), AppUtil.dp2px(radius), AppUtil.dp2px(radius), paint);
        return bitmap;
    }

    /**
     * 放大图片   会生成新的图片，资源浪费
     *
     * @param bm
     * @param newWidth
     * @param newHeight
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap bm, int newWidth, int newHeight) {
        if(bm == null){
            return null;
        }
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        float maxScale = Math.max(scaleWidth, scaleHeight);
        matrix.postScale(maxScale, maxScale);
        // 得到新的图片
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
