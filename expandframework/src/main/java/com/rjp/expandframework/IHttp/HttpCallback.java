package com.rjp.expandframework.IHttp;

public abstract class HttpCallback<T> implements ICallback{
    @Override
    public void onSuccess(String result) {

    }

    public abstract void onSuccess(T model);

    @Override
    public void onFailure() {

    }
}
