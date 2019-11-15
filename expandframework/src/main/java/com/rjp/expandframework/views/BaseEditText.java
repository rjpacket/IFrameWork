package com.rjp.expandframework.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import java.lang.reflect.Field;

public class BaseEditText extends android.support.v7.widget.AppCompatEditText {
    private static Field mParent;

    static {
        try {
            mParent = View.class.getDeclaredField("mParent");
            mParent.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public BaseEditText(Context context) {
        super(context.getApplicationContext());
    }

    public BaseEditText(Context context, AttributeSet attrs) {
        super(context.getApplicationContext(), attrs);
    }

    public BaseEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context.getApplicationContext(), attrs, defStyleAttr);
    }

    @Override
    protected void onDetachedFromWindow() {
        try {
            if (mParent != null)
                mParent.set(this, null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        super.onDetachedFromWindow();
    }
}