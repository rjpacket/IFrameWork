package com.rjp.fastframework;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rjp.expandframework.interfaces.PermissionCallback;
import com.rjp.expandframework.utils.DialogUtil;
import com.rjp.expandframework.utils.ImageLoaderUtil;
import com.rjp.expandframework.utils.PermissionUtil;

import java.util.List;

import static com.rjp.expandframework.activitys.ForResultActivity.TRANS_BUNDLE;

public class SecondActivity extends Activity {

    private TextView tvMsg;
    private Context mContext;

    public static void trendTo(Context mContext) {
        Intent intent = new Intent(mContext, SecondActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mContext = this;

        tvMsg = findViewById(R.id.tv_msg);

        Intent intent = getIntent();
        if(intent != null){
            Bundle bundleExtra = intent.getBundleExtra(TRANS_BUNDLE);
            tvMsg.setText(bundleExtra.getString("name"));
        }

        tvMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent1 = new Intent();
//                intent1.putExtra("age", "20");
//                setResult(200, intent1);
//                finish();

                new PermissionUtil.Builder().context(mContext).permission(Manifest.permission.CAMERA).build().request(new PermissionCallback() {
                    @Override
                    public void allow() {
                        new DialogUtil.Builder().context(mContext).notice("123").build().show();
                    }

                    @Override
                    public void deny(List<String> showDialog) {
                        Toast.makeText(mContext, PermissionUtil.getNotice(showDialog), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        ImageView ivImage = findViewById(R.id.iv_image);
        ImageLoaderUtil.load(this, "http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg", ivImage, R.drawable.ic_launcher_background, 600, 100);


    }
}
