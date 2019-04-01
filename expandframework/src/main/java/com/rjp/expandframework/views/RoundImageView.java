package com.rjp.expandframework.views;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.rjp.expandframework.R;

/**
 * author : Gimpo create on 2019/3/19 10:28
 * email  : jimbo922@163.com
 */
public class RoundImageView extends AppCompatImageView {
    private Context mContext;
    private int viewWidth;
    private int viewHeight;
    private Paint mPaint;
    private Bitmap srcBitmap;
    private Paint borderPaint;
    private float borderWidth;
    private int borderColor;
    private float radius;

    public RoundImageView(Context context) {
        super(context);
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        mContext = context;

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView);
        if (array != null) {
            borderWidth = array.getDimension(R.styleable.CircleImageView_border_width, 0);
            radius = array.getDimension(R.styleable.CircleImageView_radius, 0);
            borderColor = array.getColor(R.styleable.CircleImageView_border_color, Color.WHITE);
        }

        srcBitmap = BitmapFactory.decodeResource(context.getResources(), 0);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        borderPaint = new Paint();
        borderPaint.setAntiAlias(true);
        borderPaint.setColor(borderColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int imageWidth = MeasureSpec.getSize(widthMeasureSpec);
        int imageHeight = MeasureSpec.getSize(heightMeasureSpec);
        viewWidth = (int) (imageWidth + 2 * borderWidth);
        viewHeight = (int) (imageHeight + 2 * borderWidth);
        setMeasuredDimension(viewWidth, viewHeight);
    }


    /**
     * 设置画笔的填充
     */
    private void setPaintShader(Bitmap bitmap, Paint paint) {
        BitmapShader bShader = new BitmapShader(bitmap, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR);
        float bScale = (float) Math.max(viewWidth * 1.0 / bitmap.getWidth(), viewHeight * 1.0 / bitmap.getHeight());
        Matrix bMatrix = new Matrix();
        bMatrix.setScale(bScale, bScale);
        bShader.setLocalMatrix(bMatrix);
        paint.setShader(bShader);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //        super.onDraw(canvas);
        Drawable drawable = getDrawable();
        if (drawable != null) {
            srcBitmap = drawableToBitmap(drawable);
            setPaintShader(srcBitmap, mPaint);
            if(borderWidth > 0) {
                canvas.drawRoundRect(new RectF(0, 0, viewWidth, viewHeight), radius, radius, borderPaint);
            }
            canvas.drawRoundRect(new RectF(borderWidth, borderWidth, viewWidth - borderWidth, viewHeight - borderWidth), radius, radius, mPaint);
        }
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

    public int dp2px(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
