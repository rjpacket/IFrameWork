package com.rjp.expandframework.log;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.rjp.expandframework.utils.FileUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class LogFileManager {

    public static final String LOG_FILE_EXT = ".log";
    //碎片化，每个文件最大值10k
    public static final int LOG_FILE_MAX_SIZE = 10 * 1024;
    private static LogFileManager logFileManager;
    private static Context mContext;
    private File processFile;

    public static LogFileManager getInstance() {
        if (logFileManager == null) {
            synchronized (LogFileManager.class) {
                if (logFileManager == null) {
                    logFileManager = new LogFileManager();
                }
            }
        }
        return logFileManager;
    }

    public static void init(Context context) {
        mContext = context;
    }

    private LogFileManager() {

    }

    /**
     * 写日志
     *
     * @param content
     */
    public String writeLog(String content) throws Exception {
        if (TextUtils.isEmpty(content)) {
            throw new Exception("no log write error");
        }
        processFile = getProcessFile();
        if (processFile == null) {
            throw new Exception("file create error");
        }
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(processFile)));
            writer.write(content, 0, content.length());
            writer.flush();
        }catch (Exception e){
            throw new Exception("write log error");
        }finally {
            if(writer != null){
                writer.close();
            }
        }
        return processFile.getAbsolutePath();
    }

    /**
     * 上传文件日志
     */
    public void uploadLog() {
        Log.d("===uploadLog===>", "尝试上传");
        File logFile = getValidLogFile();
//        String log = readLogFromFile();
        if(logFile != null){
            Log.d("===uploadLog===>", "上传" + logFile.getAbsolutePath());
            OkLog.uploadFile(logFile.getAbsolutePath(), new LogCallback() {
                @Override
                public void onSuccess(String logFilePath) {
                    //上传成功，需要删除保存的日志文件
                    Log.d("===uploadLog===>", "上传成功" + logFilePath);
                    File logFile = new File(logFilePath);
                    if(logFile.exists()){
                        boolean b = logFile.delete();
                    }
                }

                @Override
                public void onFailure() {
                    //上传失败不处理，下次日志还从第一个文件开始读取
                }
            });
        }
    }

    /**
     * 从文件读出日志
     * @return
     */
    private String readLogFromFile() {
        File logFile = getValidLogFile();
        if (logFile != null) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logFile)));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null){
                    sb.append(line);
                }
                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取合法的log日志文件
     *
     * @return
     */
    private File getValidLogFile() {
        String path = FileUtil.getAppLogsPath(mContext);
        File dir = new File(path);
        if (dir.exists()) {
            File[] logFiles = dir.listFiles();
            if (logFiles != null && logFiles.length > 0) {
                File logFile = logFiles[0];
                if (logFile != null && logFile.getName().endsWith(LOG_FILE_EXT)) {
                    return logFile;
                }
            }
        }
        return null;
    }

    /**
     * 获取当前可以操作的文件
     *
     * @return
     */
    private File getProcessFile() {
        String path = FileUtil.getAppLogsPath(mContext);
        long currentTime = System.nanoTime();
        File file = new File(path, currentTime + LOG_FILE_EXT);
        if (!file.exists()) {
            try {
                boolean flag = file.createNewFile();
                if (!flag) {
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}
