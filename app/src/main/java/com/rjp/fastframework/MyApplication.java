package com.rjp.fastframework;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.rjp.expandframework.interfaces.ImageLoader;
import com.rjp.expandframework.utils.AppUtil;
import com.rjp.expandframework.utils.ImageLoaderUtil;
import com.squareup.leakcanary.LeakCanary;

/**
 * author : Gimpo create on 2018/11/5 18:14
 * email  : jimbo922@163.com
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AppUtil.initApp(this);

        /**
         * 图片加载框架
         */
        ImageLoaderUtil.init(new ImageLoader() {
            @Override
            public void load(Context context, String url, ImageView imageView) {
                Glide.with(context).load(url).into(imageView);
            }

            @Override
            public void load(Context context, String url, ImageView imageView, int placeholder) {
                Glide.with(context).load(url).placeholder(placeholder).into(imageView);
            }

            @Override
            public void load(Context context, String url, final ImageView imageView, final int width, final int height) {
                Glide.with(context).load(url).asBitmap().into(new BitmapImageViewTarget(imageView){
                    @Override
                    protected void setResource(Bitmap resource) {
                        Bitmap bitmap = ThumbnailUtils.extractThumbnail(resource, width, height);
                        imageView.setImageBitmap(bitmap);
                    }
                });
            }

            @Override
            public void load(Context context, String url, final ImageView imageView, int placeholder, final int width, final int height) {
                Glide.with(context).load(url).asBitmap().placeholder(placeholder).into(new BitmapImageViewTarget(imageView){
                    @Override
                    protected void setResource(Bitmap resource) {
                        Bitmap bitmap = ThumbnailUtils.extractThumbnail(resource, width, height);
                        imageView.setImageBitmap(bitmap);
                    }
                });
            }
        });

        LeakCanary.install(this);
    }
}
