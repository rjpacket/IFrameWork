package com.rjp.expandframework.okhttp;

public interface IHttpRequest {

    void setUrl(String url);

    void setData(byte[] data);

    void setListener(OnRequestListener onRequestListener);

    void execute() throws Exception;
}
