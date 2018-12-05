package com.rjp.fastframework;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;

import com.rjp.expandframework.views.tagCloud.TagCloudView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Context mContext;

    public static void trendTo(Context mContext) {
        Intent intent = new Intent(mContext, MainActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.setCustomDensity(this, getApplication());
        setContentView(R.layout.activity_main);

        mContext = this;

//        Toolbar toolbar = findViewById(R.id.tool_bar);
//        toolbar.setTitleTextColor(Color.WHITE);
//        toolbar.setTitle("标题");

        CoordinatorLayout coordinatorLayout = findViewById(R.id.coordinator_layout);

        CollapsingToolbarLayout collapseLayout = findViewById(R.id.collapse_layout);

        TagCloudView tagCloudView = findViewById(R.id.tag_cloud_view);
        ArrayList<String> tags = new ArrayList<>();
        tags.add("昔人已乘黄鹤去");
        tags.add("此地空余黄鹤楼");
        tags.add("黄鹤一去不复返");
        tags.add("白云千载空悠悠");
        tagCloudView.setData(tags);
    }
}
