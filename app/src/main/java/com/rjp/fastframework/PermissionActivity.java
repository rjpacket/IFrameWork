package com.rjp.fastframework;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.rjp.expandframework.aop.SingleClick;
import com.rjp.expandframework.interfaces.PermissionCallback;
import com.rjp.expandframework.utils.PermissionUtil;

public class PermissionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
    }

    public void requestCamera(View view) {
        show();
    }

    @SingleClick()
    private void show() {

    }
}
