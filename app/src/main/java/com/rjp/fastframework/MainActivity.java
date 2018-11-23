package com.rjp.fastframework;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;
import android.widget.TextView;

import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {

    private TextView tvMsg;
    private Context mContext;
    private TextView tv1;
    private TextView tv2;
    private ScrollView scrollView;

    public static void trendTo(Context mContext) {
        Intent intent = new Intent(mContext, MainActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        scrollView = findViewById(R.id.scrollView);

        tv1 = findViewById(R.id.tv_1);
        tv2 = findViewById(R.id.tv_2);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               tv1.setVisibility(VISIBLE);
               tv2.setVisibility(VISIBLE);
            }
        }, 3000);

        tv1.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(tv1.getVisibility() == VISIBLE){
                    int scrollY = scrollView.getScrollY();
                    int measuredHeight = tv1.getMeasuredHeight();
                    if(tv1.getBottom() - measuredHeight < scrollY) {
                        scrollView.scrollTo(0, scrollY + measuredHeight);
                    }
                }
            }
        });

        tv2.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(tv2.getVisibility() == VISIBLE){
                    int scrollY = scrollView.getScrollY();
                    int measuredHeight = tv2.getMeasuredHeight();
                    if(tv2.getBottom() - measuredHeight < scrollY) {
                        scrollView.scrollTo(0, scrollY + measuredHeight);
                    }
                }
            }
        });
    }

    private Handler handler = new Handler();
}
