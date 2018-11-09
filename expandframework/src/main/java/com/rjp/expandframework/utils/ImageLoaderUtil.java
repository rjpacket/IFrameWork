package com.rjp.expandframework.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.rjp.expandframework.interfaces.ImageLoader;

/**
 *
 * 统一的图片加载框架 默认使用Glide加载
 * author : Gimpo create on 2018/11/7 11:38
 * email  : jimbo922@163.com
 */
public class ImageLoaderUtil {

    private ImageLoaderUtil() {
        throw new UnsupportedOperationException("error new instance...");
    }

    private static ImageLoader loader = new ImageLoader() {
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
    };

    /**
     * 初始化，替换加载框架
     * @param imageLoader
     */
    public static void init(ImageLoader imageLoader){
        if(imageLoader != null) {
            loader = imageLoader;
        }
    }

    public static void load(Context context, String url, ImageView imageView){
        loader.load(context, url, imageView);
    }

    public static void load(Context context, String url, ImageView imageView, int placeholder){
        loader.load(context, url, imageView, placeholder);
    }

    public static void load(Context context, String url, ImageView imageView, int width, int height){
        loader.load(context, url, imageView, width, height);
    }

    public static void load(Context context, String url, ImageView imageView, int placeholder, int width, int height){
        loader.load(context, url, imageView, placeholder, width, height);
    }
}
