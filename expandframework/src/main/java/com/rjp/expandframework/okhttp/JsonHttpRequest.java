package com.rjp.expandframework.okhttp;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonHttpRequest implements IHttpRequest {

    private String url;
    private byte[] data;
    private OnRequestListener onRequestListener;
    private HttpURLConnection urlConnection;

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public void setListener(OnRequestListener onRequestListener) {
        this.onRequestListener = onRequestListener;
    }

    @Override
    public void execute() throws Exception {
        //执行具体的网络请求
        try {
            URL url = new URL(this.url);
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setConnectTimeout(6000);
            urlConnection.setReadTimeout(3000);

            urlConnection.setUseCaches(false);
            urlConnection.setInstanceFollowRedirects(true);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

            urlConnection.connect();

            OutputStream out = urlConnection.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(out);
            bos.write(data);
            bos.flush();
            out.close();
            bos.close();

            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream in = urlConnection.getInputStream();
                onRequestListener.onRequestSuccess(in);
            }else{
                throw new Exception("请求失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("请求失败");
        } finally {
            urlConnection.disconnect();
        }
    }
}
