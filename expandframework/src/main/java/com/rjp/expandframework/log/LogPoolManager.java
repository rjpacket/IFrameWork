package com.rjp.expandframework.log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class LogPoolManager {

    public static final long LOG_UPLOAD_TIME = 10 * 1000;

    public static final long LOG_UPLOAD_MAX_TIME = 60 * 1000;

    public static long mSleepTime = LOG_UPLOAD_TIME;

    //单例log管理器
    private static LogPoolManager logPoolManager = new LogPoolManager();
    private final ExecutorService mWriterExecutor;
    private final ExecutorService mUploadExecutor;

    public static LogPoolManager getInstance() {
        return logPoolManager;
    }

    /**
     * 添加writer任务
     * @param runnable
     */
    public void addWriterTask(Runnable runnable) {
        if (runnable != null) {
            mWriterExecutor.submit(runnable);
        }
    }

    /**
     * 添加upload任务
     * @param runnable
     */
    public void addUploadTask(Runnable runnable) {
        if (runnable != null) {
            mUploadExecutor.submit(runnable);
        }
    }

    private LogPoolManager() {
        //初始化writer线程池，单线程
        mWriterExecutor = Executors.newSingleThreadExecutor();

        //初始化upload线程池，多线程
        int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
        int KEEP_ALIVE_TIME = 10;
        TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
        BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();
        mUploadExecutor = new ThreadPoolExecutor(
                NUMBER_OF_CORES,
                NUMBER_OF_CORES * 2,
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                taskQueue,
                Executors.defaultThreadFactory(),
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        addUploadTask(r);
                    }
                }
        );
        mUploadExecutor.submit(uploadFileThread);
    }

    public Runnable uploadFileThread = new Runnable() {

        @Override
        public void run() {
            while (true) {
                boolean active = LogFileManager.getInstance().uploadLog();
                //延时策略，如果有日志，则每间隔10s，如果没有则每下次间隔 * 2
                if (active) {
                    mSleepTime = LOG_UPLOAD_TIME;
                } else {
                    mSleepTime = Math.min(mSleepTime * 2, LOG_UPLOAD_MAX_TIME);
                }
                try {
                    Thread.sleep(mSleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };
}
