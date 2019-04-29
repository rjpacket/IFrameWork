package com.rjp.expandframework.okhttp;

public class IOkHttp {

    public static <T, M> void post(String url, T requestData, CallbackListener<M> listener) {
        IHttpRequest httpRequest = new JsonHttpRequest();
        OnRequestListener onRequestListener = new JsonOnRequestListener<>(listener);
        HttpTask<T> httpTask = new HttpTask<>(url, requestData, httpRequest, onRequestListener);
        ThreadPoolManager.getInstance().addTask(httpTask);
    }
}
