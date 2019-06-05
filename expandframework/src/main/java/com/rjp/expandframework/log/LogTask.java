package com.rjp.expandframework.log;

public class LogTask implements Runnable {

    private ILogRequest iLogRequest;

    public LogTask(ILogRequest iLogRequest, String content, OnLogCallback callback) {
        try {
            iLogRequest.setData(content);
            iLogRequest.setListener(callback);
            this.iLogRequest = iLogRequest;
        } catch (Exception e) {

        }
    }

    @Override
    public void run() {
        try {
            this.iLogRequest.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
