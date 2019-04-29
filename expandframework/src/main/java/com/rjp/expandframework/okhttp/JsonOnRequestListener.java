package com.rjp.expandframework.okhttp;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonOnRequestListener<T> implements OnRequestListener {

    private CallbackListener<T> callbackListener;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    public JsonOnRequestListener(CallbackListener<T> callbackListener) {
        this.callbackListener = callbackListener;
    }

    @Override
    public void onRequestSuccess(InputStream inputStream) {
        String response = getContent(inputStream);
        Gson gson = new Gson();
        final T model = gson.fromJson(response, callbackListener.getGenericityType());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callbackListener.onSuccess(model);
            }
        });
    }

    private String getContent(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (Exception e) {

        } finally {
            try {
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    @Override
    public void onRequestFailure() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callbackListener.onFailure();
            }
        });
    }
}
