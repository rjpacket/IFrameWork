package com.rjp.expandframework.bitmap;

import android.content.Context;

/**
 * author: jinpeng.ren create at 2019/6/11 18:16
 * email: jinpeng.ren@11bee.com
 */
public interface IBitmapRequest<T> {

    void setContext(Context context);

    void setTarget(T t);

    void setUrl(String url);

    void setListener(OnBitmapCallback onBitmapCallback);

    void execute();
}
