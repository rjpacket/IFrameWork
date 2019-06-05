package com.rjp.expandframework.log;

public class SaveLogRequest implements ILogRequest {

    private String data;
    private OnLogCallback onLogCallback;

    @Override
    public void setData(String data) {
        this.data = data;
    }

    @Override
    public void setListener(OnLogCallback onLogCallback) {
        this.onLogCallback = onLogCallback;
    }

    @Override
    public void execute() throws Exception {
        String logFilePath = null;
        try {
            logFilePath = LogFileManager.getInstance().writeLog(data);
        }catch (Exception e){
            e.printStackTrace();
            onLogCallback.onLogFailure();
            return;
        }
        onLogCallback.onLogSuccess(logFilePath);
    }
}
