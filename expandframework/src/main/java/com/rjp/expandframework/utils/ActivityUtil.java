package com.rjp.expandframework.utils;

import android.content.Intent;
import android.os.Bundle;

import com.rjp.expandframework.activitys.ForResultActivity;
import com.rjp.expandframework.interfaces.OnActivityForResultListener;

import static com.rjp.expandframework.activitys.ForResultActivity.FOR_RESULT_BUNDLE;
import static com.rjp.expandframework.activitys.ForResultActivity.TRANS_BUNDLE;

/**
 * author : Gimpo create on 2018/11/5 18:40
 * email  : jimbo922@163.com
 */
public class ActivityUtil {

    private ActivityUtil() {
        throw new UnsupportedOperationException("error new instance...");
    }

    public static OnActivityForResultListener onActivityForResultListener;

    public static void startActivityForResult(Intent intent, int requestCode, OnActivityForResultListener l){
        onActivityForResultListener = l;
        String className = intent.getComponent().getClassName();
        Intent intentCenter = new Intent(AppUtil.getApp(), ForResultActivity.class);
        Bundle bundle = intent.getBundleExtra(TRANS_BUNDLE);
        bundle.putInt(ForResultActivity.REQUEST_CODE, requestCode);
        bundle.putString(ForResultActivity.REQUEST_CLASS, className);
        intentCenter.putExtra(FOR_RESULT_BUNDLE, bundle);
        AppUtil.getApp().startActivity(intentCenter);
    }
}
