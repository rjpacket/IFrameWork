package com.rjp.fastframework.views;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.rjp.expandframework.liveDataBus.LiveDataBus;
import com.rjp.fastframework.R;

public class EmptyView extends ConstraintLayout {
    public EmptyView(Context context) {
        this(context, null);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_empty_view, this);

        findViewById(R.id.tv_empty).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LiveDataBus.get().with("empty_click").postValue(null);
            }
        });
    }
}
