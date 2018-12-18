package com.rjp.fastframework.scroll_view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.rjp.fastframework.R;

public class ScrollMoreActivity extends Activity {

    public static void trendTo(Context mContext) {
        Intent intent = new Intent(mContext, ScrollMoreActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_more);
    }
}
