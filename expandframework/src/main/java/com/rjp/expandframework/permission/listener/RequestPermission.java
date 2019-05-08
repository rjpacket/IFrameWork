package com.rjp.expandframework.permission.listener;

public interface RequestPermission<T> {

    void requestPermission(T target, String[] permission);

    void onRequestPermissionsResult(T target, int requestCode, int[] grantResults);
}
