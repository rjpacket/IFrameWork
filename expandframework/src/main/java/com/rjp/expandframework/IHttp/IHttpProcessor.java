package com.rjp.expandframework.IHttp;

import java.util.Map;

public interface IHttpProcessor {

    void post(String url, Map<String, Object> params, ICallback callBack);

    void get(String url, ICallback callback);
}
