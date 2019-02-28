package com.rjp.expandframework.utils.photo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rjp.expandframework.R;
import com.rjp.expandframework.mvp.view.LoginActivity;

/**
 * 拍照测试类
 */
public class PhotoTestActivity extends Activity {

    private Button btnTake;
    private Button btnTakeCrop;
    private Button btnPick;
    private Button btnPickCrop;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_test);

        mContext = this;

        btnTake = findViewById(R.id.btn_take_photo);
        btnTakeCrop = findViewById(R.id.btn_takecrop_photo);
        btnPick = findViewById(R.id.btn_pick_photo);
        btnPickCrop = findViewById(R.id.btn_pickcrop_photo);

        btnTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, LoginActivity.class));
            }
        });

        btnTakeCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoUtil.takePhoto(mContext, true, new PhotoCallback() {
                    @Override
                    public void choosePhoto(String photoPath) {
                        btnTakeCrop.setText(photoPath);
                    }
                });
            }
        });

        btnPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoUtil.pickPhoto(mContext, false, new PhotoCallback() {
                    @Override
                    public void choosePhoto(String photoPath) {
                        btnPick.setText(photoPath);
                    }
                });
            }
        });

        btnPickCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoUtil.pickPhoto(mContext, true, new PhotoCallback() {
                    @Override
                    public void choosePhoto(String photoPath) {
                        btnPickCrop.setText(photoPath);
                    }
                });
            }
        });
    }
}
