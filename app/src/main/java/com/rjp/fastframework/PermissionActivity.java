package com.rjp.fastframework;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.rjp.annotation.NeedsPermission;
import com.rjp.annotation.OnNeverAskAgain;
import com.rjp.annotation.OnPermissionDenied;
import com.rjp.annotation.OnShowRationale;
import com.rjp.annotation.Route;
import com.rjp.expandframework.aop.SingleClick;
import com.rjp.expandframework.interfaces.PermissionCallback;
import com.rjp.expandframework.permission.PermissionManager;
import com.rjp.expandframework.permission.listener.PermissionRequest;
import com.rjp.expandframework.permission.listener.PermissionSetting;
import com.rjp.expandframework.utils.PermissionUtil;

@Route(path = "/main/permission")
public class PermissionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

         WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        PermissionManager.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    public void requestCamera(View view) {
        PermissionManager.request(this, new String[]{Manifest.permission.CAMERA});
    }

    public void requestFile(View view) {
        PermissionManager.request(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
    }

    @NeedsPermission(permission = Manifest.permission.CAMERA)
    public void camera() {
        Toast.makeText(this, "打开相机", Toast.LENGTH_SHORT).show();
    }

    @NeedsPermission(permission = Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void openFile() {
        Toast.makeText(this, "打开文件", Toast.LENGTH_SHORT).show();
    }

    @OnShowRationale
    public void showWhy(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("这个权限真的很重要")
                .setNegativeButton("1", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setPositiveButton("2", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    @OnNeverAskAgain
    public void neverAsk(final PermissionSetting setting) {
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("不再询问这个权限将无法使用")
                .setNegativeButton("1", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("2", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setting.setting(100);
                    }
                })
                .show();
    }

    @OnPermissionDenied
    public void denyCamera() {
        Toast.makeText(this, "拒绝了这个权限", Toast.LENGTH_SHORT).show();
    }
}
