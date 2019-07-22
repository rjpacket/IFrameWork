package com.rjp.expandframework.log;

public class OkLog {

    /**
     * 保存日志文件
     * @param content
     * @param callback
     */
    public static void save(String content, LogCallback callback){
        ILogRequest iLogRequest = new SaveLogRequest();
        LogTask logTask = new LogTask(iLogRequest, content, callback);
        LogPoolManager.getInstance().addWriterTask(logTask);
    }

    /**
     * 上传日志文件
     * @param filePath
     * @param callback
     */
    public static void uploadFile(String filePath, LogCallback callback){
        ILogRequest iLogRequest = new UploadFileRequest();
        LogTask logTask = new LogTask(iLogRequest, filePath, callback);
        LogPoolManager.getInstance().addUploadTask(logTask);
    }
}
