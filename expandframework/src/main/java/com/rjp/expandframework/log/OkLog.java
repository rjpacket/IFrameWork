package com.rjp.expandframework.log;

public class OkLog {

    /**
     * 网络良好的时候直接上传日志文件
     * @param content
     * @param callback
     */
    public static void upload(String content, LogCallback callback){
        ILogRequest iLogRequest = new UploadLogRequest();
        LogTask logTask = new LogTask(iLogRequest, content, callback);
        LogPoolManager.getInstance().addTask(logTask);
    }

    /**
     * 请求失败的情况下先保存日志
     * @param content
     * @param callback
     */
    public static void save(String content, LogCallback callback){
        ILogRequest iLogRequest = new SaveLogRequest();
        LogTask logTask = new LogTask(iLogRequest, content, callback);
        LogPoolManager.getInstance().addTask(logTask);
    }

    /**
     * 上传文件日志
     * @param filePath
     * @param callback
     */
    public static void uploadFile(String filePath, LogCallback callback){
        ILogRequest iLogRequest = new UploadFileRequest();
        LogTask logTask = new LogTask(iLogRequest, filePath, callback);
        LogPoolManager.getInstance().addTask(logTask);
    }
}
