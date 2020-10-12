package com.rjp.fastframework.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.rjp.fastframework.MainActivity;

/**
 * author: jinpeng.ren create at 2020/3/26 20:21
 * email: jinpeng.ren@11bee.com
 */
public class DefaultCardView extends CardView {
    public DefaultCardView(Context context) {
        super(context);
    }

    public DefaultCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DefaultCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected View onCreateView(ViewGroup viewGroup) {
        View view = new View(mContext);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return view;
    }
}
