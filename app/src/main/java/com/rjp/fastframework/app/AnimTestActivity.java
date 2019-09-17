package com.rjp.fastframework.app;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;
import com.rjp.expandframework.interfaces.OnDialogClickListener;
import com.rjp.expandframework.interfaces.PermissionCallback;
import com.rjp.expandframework.utils.DialogUtil;
import com.rjp.expandframework.utils.PermissionUtil;
import com.rjp.fastframework.BuildConfig;
import com.rjp.fastframework.R;

import java.util.List;

public class AnimTestActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_test);

        final TextView tvAnim = findViewById(R.id.tv_anim);
//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_translate);
//        animation.setDuration(2000);
//        animation.setFillAfter(true);
//        tvAnim.setAnimation(animation);
//        animation.start();

        context = this;

        tvAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionUtil.builder().context(context)
                        .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .build()
                        .request(new PermissionCallback() {
                            @Override
                            public void allow() {

                            }

                            @Override
                            public void deny(List<String> notAsk) {
                                if(notAsk.size() > 0) {
                                    showPicturePermissionSettingDialog();
                                }
                            }
                        });
            }
        });
    }

    private void showPicturePermissionSettingDialog() {
        PermissionUtil.gotoPermission(context, BuildConfig.APPLICATION_ID);
    }
}
