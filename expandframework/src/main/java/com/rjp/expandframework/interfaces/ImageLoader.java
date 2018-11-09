package com.rjp.expandframework.interfaces;

import android.content.Context;
import android.widget.ImageView;

/**
 * author : Gimpo create on 2018/11/7 11:40
 * email  : jimbo922@163.com
 */
public interface ImageLoader {
    void load(Context context, String url, ImageView imageView);

    void load(Context context, String url, ImageView imageView, int placeholder);

    void load(Context context, String url, ImageView imageView, int width, int height);

    void load(Context context, String url, ImageView imageView, int placeholder, int width, int height);
}
