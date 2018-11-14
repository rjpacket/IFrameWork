package com.rjp.fastframework;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.rjp.expandframework.utils.ActivityUtil;

import static com.rjp.expandframework.activitys.ForResultActivity.TRANS_BUNDLE;

public class MainActivity extends AppCompatActivity {

    private TextView tvMsg;
    private Context mContext;

    public static void trendTo(Context mContext) {
        Intent intent = new Intent(mContext, MainActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        tvMsg = (TextView) findViewById(R.id.tv_msg);

        tvMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", "任金鹏");
                intent.putExtra(TRANS_BUNDLE, bundle);
                ActivityUtil.startActivityForResult(intent, 10086, new ActivityUtil.OnActivityForResultListener() {
                    @Override
                    public void onActivityResult(int requestCode, int resultCode, Intent data) {
                        if(data != null) {
                            tvMsg.setText(data.getStringExtra("age"));
                        }
                    }
                });
            }
        });

//        ImageView ivImage = findViewById(R.id.iv_image);
//        ImageLoaderUtil.load(this, "http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg", ivImage, R.drawable.ic_launcher_background);

        String packageName = getApplication().getPackageName();
        tvMsg.setText(
                "包名是：" + packageName + "\n" +
                "版本号是：" + BuildConfig.VERSION_NAME + "\n" +
                "类型是：" + BuildConfig.BUILD_TYPE + "\n"
        );
    }
}
