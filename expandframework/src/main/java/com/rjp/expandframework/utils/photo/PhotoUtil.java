package com.rjp.expandframework.utils.photo;

import android.content.Context;

import static com.rjp.expandframework.utils.photo.PhotoMiddleActivity.PHOTO_PICK;
import static com.rjp.expandframework.utils.photo.PhotoMiddleActivity.PHOTO_TAKE;

/**
 * author : Gimpo create on 2019/1/17 17:17
 * email  : jimbo922@163.com
 */
public class PhotoUtil {

    public static PhotoCallback photoCallback;

    /**
     * 拍照向外暴漏的方法
     * @param context
     * @param crop
     * @param callback
     */
    public static void takePhoto(Context context, boolean crop, PhotoCallback callback){
        photoCallback = callback;
        PhotoMiddleActivity.start(context, PHOTO_TAKE, crop);
    }

    /**
     * 选择照片暴漏的方法
     * @param context
     * @param crop
     * @param callback
     */
    public static void pickPhoto(Context context, boolean crop, PhotoCallback callback){
        photoCallback = callback;
        PhotoMiddleActivity.start(context, PHOTO_PICK, crop);
    }

    /**
     * 释放资源
     */
    public static void onDestory(){
        photoCallback = null;
    }
}
