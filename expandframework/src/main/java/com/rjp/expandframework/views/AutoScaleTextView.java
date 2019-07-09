package com.rjp.expandframework.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

public class AutoScaleTextView extends TextView {
    protected Paint textPaint;
    protected float maxTextSize;
    protected float minTextSize;
    protected static final float DEFAULT_MAX_TEXTSIZE = 16.0F;
    protected static final float DEFAULT_MIN_TEXTSIZE = 10.0F;

    public AutoScaleTextView(Context context) {
        this(context, null);
    }

    public AutoScaleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.textPaint = new Paint();
    }

    public void setMinTextSize(float minTextSize) {
        this.minTextSize = minTextSize;
    }

    public void setMaxTextSize(float maxTextSize) {
        this.maxTextSize = maxTextSize;
    }

    private void refitText(String text, int textWidth) {
        if (textWidth > 0 && text != null && text.length() != 0) {
            int targetWidth = textWidth - this.getPaddingLeft() - this.getPaddingRight();
            float threshold = 1.0F;
            this.textPaint.set(this.getPaint());

            while (this.maxTextSize - this.minTextSize > 1.0F) {
                float size = (this.maxTextSize + this.minTextSize) / 2.0F;
                this.textPaint.setTextSize(size);
                if (this.textPaint.measureText(text) >= (float) targetWidth) {
                    this.maxTextSize = size;
                } else {
                    this.minTextSize = size;
                }
            }

            this.setTextSize(minTextSize);
        }
    }

    protected void onTextChanged(CharSequence text, int start, int before, int after) {
        this.refitText(text.toString(), this.getWidth());
    }

    protected void onSizeChanged(int width, int height, int oldwidth, int oldheight) {
        if (width != oldwidth) {
            this.refitText(this.getText().toString(), width);
        }

    }

    public boolean isSelected() {
        return true;
    }
}