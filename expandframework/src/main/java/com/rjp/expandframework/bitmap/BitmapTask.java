package com.rjp.expandframework.bitmap;

/**
 * author: jinpeng.ren create at 2019/6/11 18:14
 * email: jinpeng.ren@11bee.com
 */
public class BitmapTask implements Runnable{

    private IBitmapRequest bitmapRequest;

    public <T> BitmapTask(IBitmapRequest<T> bitmapRequest) {
        this.bitmapRequest = bitmapRequest;
    }

    @Override
    public void run() {
        bitmapRequest.execute();
    }
}
