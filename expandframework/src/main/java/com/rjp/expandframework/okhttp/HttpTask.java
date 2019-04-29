package com.rjp.expandframework.okhttp;

import com.google.gson.Gson;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class HttpTask<T> implements Runnable, Delayed {

    private IHttpRequest iHttpRequest;

    public HttpTask(String url, T requestData, IHttpRequest httpRequest, OnRequestListener onRequestListener){
        httpRequest.setUrl(url);
        httpRequest.setListener(onRequestListener);
        String content = new Gson().toJson(requestData);
        try {
            httpRequest.setData(content.getBytes("utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        iHttpRequest = httpRequest;
    }

    @Override
    public void run() {
        try {
            iHttpRequest.execute();
        }catch (Exception e){
            ThreadPoolManager.getInstance().addDelayTask(this);
        }
    }

    private long delayTime;
    private int retryCount;

    public int getRetryCount() {
        return retryCount;
    }

    public void setDelayTime(long delayTime) {
        this.delayTime = System.currentTimeMillis() + delayTime;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.delayTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return 0;
    }
}
