package com.rjp.expandframework.IHttp;

import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * author: jinpeng.ren create at 2019/6/17 13:50
 * email: jinpeng.ren@11bee.com
 */
public class OkHttp3Processor implements IHttpProcessor {

    public static final int TIME_OUT_CONNECT = 30_000;
    public static final int TIME_OUT_READ = 30_000;
    public static final int TIME_OUT_WRITE = 30_000;

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
                        callBack.onSuccess(body.string());
                    }
                }
            }
        });
    }
}
