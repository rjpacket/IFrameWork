package com.rjp.expandframework.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * author: jinpeng.ren create at 2019/6/11 18:19
 * email: jinpeng.ren@11bee.com
 */
public class OkBitmap {

    public static void load(Context context, ImageView imageView, String url){
        NetworkBitmapRequest bitmapRequest = new NetworkBitmapRequest();
        bitmapRequest.setContext(context);
        bitmapRequest.setTarget(imageView);
        bitmapRequest.setUrl(url);

        BitmapTask task = new BitmapTask(bitmapRequest);
        BitmapThreadManager.getInstance().addTask(task);
    }
}
