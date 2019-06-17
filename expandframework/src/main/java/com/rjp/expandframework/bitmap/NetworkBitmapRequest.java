package com.rjp.expandframework.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.rjp.expandframework.utils.FileUtil;

import static com.rjp.expandframework.bitmap.BitmapThreadManager.NETWORK_CACHE_FILE_NAME;

/**
 * author: jinpeng.ren create at 2019/6/11 18:21
 * email: jinpeng.ren@11bee.com
 */
public class NetworkBitmapRequest implements IBitmapRequest<ImageView> {

    private Context context;
    private String url;
    private OnBitmapCallback onBitmapCallback;
    private ImageView imageView;

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void setTarget(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setListener(OnBitmapCallback onBitmapCallback) {
        this.onBitmapCallback = onBitmapCallback;
    }

    @Override
    public void execute() {
        //首先从内存中找
        Log.d("===>", "首先从内存中找");
        final Bitmap cacheBitmap = BitmapThreadManager.getInstance().getCacheBitmap(url);
        if (cacheBitmap != null) {
            Log.d("===>", "首先从内存中找===找到了");
            imageView.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap(cacheBitmap);
                }
            });
        } else {
            Log.d("===>", "首先从内存中找===没找到");
            //再从文件里找
            Log.d("===>", "再从文件里找");
            String encode = Md5.encode(url);
            String filePath = BitmapThreadManager.getInstance().getFilePathByMd5(context, encode, NETWORK_CACHE_FILE_NAME);
            if (!TextUtils.isEmpty(filePath)) {
                Log.d("===>", "再从文件里找===找到了" + filePath);
                final Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                BitmapThreadManager.getInstance().put(url, bitmap);
                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageBitmap(bitmap);
                    }
                });
            } else {
                Log.d("===>", "再从文件里找===没找到");
                //最后从网络获取图片
                Log.d("===>", "最后从网络获取图片");
                final Bitmap bitmap = BitmapThreadManager.getInstance().getBitmapByUrl(url);
                if (bitmap != null) {
                    Log.d("===>", "最后从网络获取图片===获取到了");
                    imageView.post(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(bitmap);
                        }
                    });
                    BitmapThreadManager.getInstance().put(url, bitmap);
                    String savePath = FileUtil.saveBitmap(context, bitmap, encode);
                    BitmapThreadManager.getInstance().save(context, NETWORK_CACHE_FILE_NAME, encode, savePath);
                } else {
                    Log.d("===>", "最后从网络获取图片===网络里也加载失败了");
                }
            }
        }
    }


}
