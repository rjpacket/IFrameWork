package com.rjp.expandframework.bitmap;

import android.graphics.Bitmap;

/**
 * author: jinpeng.ren create at 2019/6/11 18:18
 * email: jinpeng.ren@11bee.com
 */
public interface OnBitmapCallback {
    void onSuccess(Bitmap bitmap);

    void onFailure();
}
