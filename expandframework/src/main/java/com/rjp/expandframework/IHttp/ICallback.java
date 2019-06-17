package com.rjp.expandframework.IHttp;

public interface ICallback {
    void onSuccess(String model);

    void onFailure(int code, String errorMsg);
}
