package com.rjp.expandframework.IHttp;

import android.text.TextUtils;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * author: jinpeng.ren create at 2019/6/17 13:50
 * email: jinpeng.ren@11bee.com
 */
public class OkHttp3Processor implements IHttpProcessor {

    public static final int TIME_OUT_CONNECT = 20_000;
    public static final int TIME_OUT_READ = 20_000;
    public static final int TIME_OUT_WRITE = 20_000;

    private final OkHttpClient okHttpClient;

    public OkHttp3Processor() {
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor())
                .connectTimeout(TIME_OUT_CONNECT, TimeUnit.MILLISECONDS)
                .readTimeout(TIME_OUT_READ, TimeUnit.MILLISECONDS)
                .writeTimeout(TIME_OUT_WRITE, TimeUnit.MILLISECONDS)
                .build();
    }

    @Override
    public void post(String url, Map<String, String> params, final ICallback callBack) {
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callBack != null) {
                    callBack.onFailure(0, e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                if (body != null) {
                    if (callBack != null) {
                        try {
                            callBack.onSuccess(body.string());
                        }catch (Exception e){
                            callBack.onFailure(110, e.getMessage());
                        }
                    }
                }
            }
        });
    }
}
