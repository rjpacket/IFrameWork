package com.rjp.expandframework.log;

import android.util.Log;

public class UploadFileRequest implements ILogRequest {

    private String filePath;
    private OnLogCallback onLogCallback;

    @Override
    public void setData(String data) {
        this.filePath = data;
    }

    @Override
    public void setListener(OnLogCallback onLogCallback) {
        this.onLogCallback = onLogCallback;
    }

    @Override
    public void execute() throws Exception {
        Log.d("----->", "开始上传");
        Thread.sleep(10 * 1000);
        Log.d("----->", "上传完成");
        onLogCallback.onLogSuccess(filePath);
    }
}
