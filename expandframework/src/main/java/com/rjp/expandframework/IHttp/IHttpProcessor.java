package com.rjp.expandframework.IHttp;

import java.util.Map;

public interface IHttpProcessor {

    void post(String url, Map<String, String> params, ICallback callBack);

}
