package com.rjp.fastframework;

import com.rjp.expandframework.permission.listener.RequestPermission;

public class PermissionActivityProxy implements RequestPermission<PermissionActivity> {

    @Override
    public void requestPermission(PermissionActivity target, String[] permission) {

    }

    @Override
    public void onRequestPermissionsResult(PermissionActivity target, int requestCode, int[] grantResults) {

    }
}
