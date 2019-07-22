package com.rjp.expandframework.log;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LogFileManager {
    private static final String TAG_LOG = "LOG_MANAGER";

    public static final String LOGS_DIR = "logs";
    public static final String LOG_FILE_EXT = ".log";
    public static final String LOG_FILE_TMP = ".tmp";
    //碎片化，每个文件最大值30k
    public static final int LOG_SAVE_MAX_SIZE = 30 * 1024;
    //文件夹下最大的log个数
    public static final int LOG_FILE_MAX_SIZE = 500;
    //上传最大1M，安全限制
    public static final int LOG_UPLOAD_MAX_SIZE = 1024 * 1024;
    private static LogFileManager logFileManager;
    private static Context mContext;

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
     * 执行shell脚本收集日志文件
     */
    public void execShellSaveLogFile() {
        Process process = null;
        DataOutputStream os = null;
        File processFile = getProcessFile();
        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());

            os.writeBytes(String.format("logcat -d -v time -f %s\n", processFile.getAbsolutePath()));
            os.flush();
            os.writeBytes("logcat -c\n");
            os.flush();

            process.waitFor();
            Log.d("===>", "执行shell脚本文件完成");
        } catch (Exception e) {
            Log.d("===>", "执行shell脚本文件失败：" + e.getMessage());
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                process.destroy();
            } catch (Exception e) {

            }
        }
    }

    /**
     * 写日志
     *
     * @param content
     */
    public void writeLog(String content) throws Exception {
        if (TextUtils.isEmpty(content)) {
            throw new Exception("no log write error");
        }
        File processFile = getProcessFile();
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(processFile, true)));
            writer.write(content, 0, content.length());
            writer.flush();
        } catch (Exception e) {
            throw new Exception("write log error");
        } finally {
            if (writer != null) {
                writer.close();
            }
            checkFileStatusAndSize(processFile);
        }
    }

    /**
     * 检查当前操作的文件是不是已经写满了
     *
     * @param processFile
     */
    private void checkFileStatusAndSize(File processFile) {
        //文件是不是写满
        if (processFile != null && processFile.length() > LOG_SAVE_MAX_SIZE) {
            String name = processFile.getName();
            String[] split = name.split("\\.");
            boolean rename = processFile.renameTo(new File(String.format("%s/%s%s", processFile.getParentFile().getAbsolutePath(), split[0], LOG_FILE_EXT)));
            if (rename) {
                Log.d("checkFileStatusAndSize", String.format("%s已经写满了", name));
            }
        }
        //文件夹是不是满了
        String path = getAppLogsPath(mContext);
        File dir = new File(path);
        if (dir.exists()) {
            File[] files = dir.listFiles();
            if (files != null && files.length > LOG_FILE_MAX_SIZE) {
                File deleteFile = files[0];
                long minTime = deleteFile.lastModified();
                for (File file : files) {
                    long lastModified = file.lastModified();
                    if (file.getName().endsWith(LOG_FILE_EXT) && lastModified < minTime) {
                        deleteFile = file;
                        minTime = lastModified;
                    }
                }
                if (deleteFile.getName().endsWith(LOG_FILE_EXT)) {
                    boolean delete = deleteFile.delete();
                    if (delete) {
                        Log.d("===>", "日志满了，删除一个");
                    }
                }
            }
        }
    }

    /**
     * 上传文件日志，每次传cpu个数 - 1
     *
     * @return
     */
    public boolean uploadLog() {
        int uploadLogCount = 1;
        Log.d(TAG_LOG, String.format("尝试上传%s个日志", uploadLogCount));
        List<File> uploadLogFiles = getValidLogFiles(uploadLogCount);
        if (uploadLogFiles != null) {
            Log.d(TAG_LOG, String.format("实际上传%s个日志", uploadLogFiles.size()));
            for (File uploadLogFile : uploadLogFiles) {
                Log.d(TAG_LOG, String.format("上传一个日志%s", uploadLogFile.getName()));
                boolean modify = uploadLogFile.setLastModified(0);
                if (modify) {
                    Log.d(TAG_LOG, String.format("修改了日志文件%s的修改时间", uploadLogFile.getName()));
                }
                OkLog.uploadFile(uploadLogFile.getAbsolutePath(), new LogCallback() {
                    @Override
                    public void onSuccess(String logFilePath) {
                        //上传成功，需要删除保存的日志文件
                        File logFile = new File(logFilePath);
                        if (logFile.exists()) {
                            boolean b = logFile.delete();
                        }
                    }

                    @Override
                    public void onFailure() {
                        //上传失败不处理，下次日志还从第一个文件开始读取
                    }
                });
            }
            return true;
        }
        return false;
    }

    /**
     * 获取前uploadLogCount个log
     *
     * @param uploadLogCount
     */
    private List<File> getValidLogFiles(int uploadLogCount) {
        String path = getAppLogsPath(mContext);
        File dir = new File(path);
        if (dir.exists()) {
            File[] files = dir.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return pathname.getName().endsWith(LOG_FILE_EXT) && pathname.lastModified() != 0;
                }
            });
            if (files.length == 0) {
                return null;
            }
            List<File> filesList = Arrays.asList(files);
            Collections.sort(filesList, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    return (int) (o1.lastModified() - o2.lastModified());
                }
            });
            if (uploadLogCount > filesList.size()) {
                uploadLogCount = filesList.size();
            }
            return filesList.subList(0, uploadLogCount);
        }
        return null;
    }

    /**
     * 从文件读出日志
     *
     * @return
     */
    public String readLogFromFile(String filePath) {
        if (!TextUtils.isEmpty(filePath) && filePath.endsWith(LOG_FILE_EXT)) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
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
     * 获取当前可以操作的文件
     *
     * @return
     */
    private File getProcessFile() {
        String path = getAppLogsPath(mContext);
        File dir = new File(path);
        //如果文件夹下有缓存文件，直接用
        if (dir.exists()) {
            File[] logFiles = dir.listFiles();
            for (File logFile : logFiles) {
                if (logFile.getName().endsWith(LOG_FILE_TMP) && logFile.length() < LOG_SAVE_MAX_SIZE) {
                    return logFile;
                }
            }
        }
        //没有，按规则新生成一个
        long currentTime = System.nanoTime();
        return new File(path, currentTime + LOG_FILE_TMP);
    }

    public String getAppLogsPath(Context context) {
        File file = createNewDir(new File(context.getCacheDir(), LOGS_DIR));
        return file.getAbsolutePath();
    }

    public File createNewDir(File dir) {
        if (dir == null) {
            return null;
        }
        try {
            if (dir.exists()) {
                return dir;
            } else {
                dir.mkdirs();
            }
        } catch (Exception e) {
            return null;
        }
        return dir;
    }
}
