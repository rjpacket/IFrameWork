package com.rjp.expandframework.IHttp;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class HttpHelper implements IHttpProcessor{

    private static HttpHelper instance = new HttpHelper();

    private HttpHelper(){}

    public static HttpHelper getInstance(){
        return instance;
    }

    private static IHttpProcessor mIHttpProcessor;

    public void init(IHttpProcessor httpProcessor){
        mIHttpProcessor = httpProcessor;
    }

    @Override
    public void post(String url, Map<String, Object> params, ICallback callBack) {
//        String finalUrl = appendParams(url, params);
        mIHttpProcessor.post(url, params, callBack);
    }

    private String appendParams(String url, Map<String, Object> params) {
        if(params == null || params.isEmpty()){
            return url;
        }
        StringBuilder sb = new StringBuilder(url);
        if(sb.indexOf("?") <= 0){
            sb.append("?");
        }else{
            if(!sb.toString().endsWith("?")){
                sb.append("&");
            }
        }
        for (Map.Entry<String, Object> entry : params.entrySet()){
            sb.append("&").append(entry.getKey()).append("=").append(encode(entry.getValue().toString()));
        }
        return sb.toString();
    }

    private String encode(String value) {
        try {
            return URLEncoder.encode(value, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
