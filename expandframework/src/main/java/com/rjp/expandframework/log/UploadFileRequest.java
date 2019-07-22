package com.rjp.expandframework.log;

import android.text.TextUtils;

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
        //异步上传文件
        String logContent = LogFileManager.getInstance().readLogFromFile(filePath);
        if (!TextUtils.isEmpty(logContent)) {
//            FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
//            formBody.add("_logs", String.format("[%s]", logContent));
//            DLog.d(String.format("网络传输日志成功%s", logContent));
//            Thread.sleep(30 * 1000);
            if (onLogCallback != null) {
                onLogCallback.onLogSuccess(filePath);
            }
//            DollyNetworkManager.startRequest(URLConfig.BEHAVIOR_LOG, formBody, new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    DLog.d(LogManager.class.getSimpleName(), String.format("log上传失败,name=%s", filePath));
//                    DLog.e(LogManager.class.getSimpleName(), e.getMessage());
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) {
//                    try {
//                        if(onLogCallback != null){
//                            onLogCallback.onLogSuccess(filePath);
//                        }
//                    } catch (Exception e) {
//                        DLog.e(LogManager.class.getSimpleName(), e.getMessage());
//                    }
//                }
//            });
        }
    }
}
