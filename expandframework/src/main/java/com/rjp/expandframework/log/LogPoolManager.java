package com.rjp.expandframework.log;

import com.rjp.expandframework.utils.FileUtil;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class LogPoolManager {

    public static final long LOG_UPLOAD_TIME = 10 * 1000;

    //一个app只有一个log管理器
    private static LogPoolManager logPoolManager = new LogPoolManager();

    public static LogPoolManager getInstance(){
        return logPoolManager;
    }

    //队列
    private LinkedBlockingQueue<Runnable> mQueue = new LinkedBlockingQueue<>();

    //添加任务
    public void addTask(Runnable runnable){
        if(runnable != null){
            mQueue.add(runnable);
        }
    }

    //线程池
    private ExecutorService mThreadPoolExecutor;

    private LogPoolManager(){
//        mThreadPoolExecutor = new ThreadPoolExecutor(
//                1,
//                3,
//                60,
//                TimeUnit.SECONDS,
//                new ArrayBlockingQueue<Runnable>(4),
//                new RejectedExecutionHandler() {
//                    @Override
//                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
//                        addTask(r);
//                    }
//                });

        int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
        int KEEP_ALIVE_TIME = 10;
        TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
        BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<Runnable>();
        mThreadPoolExecutor = new ThreadPoolExecutor(
                NUMBER_OF_CORES,
                NUMBER_OF_CORES * 2,
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                taskQueue,
                Executors.defaultThreadFactory(),
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        addTask(r);
                    }
                }
        );

        //开启循环线程
        execute(coreThread);

        //开启写日志的线程
        execute(uploadFileThread);
    }

    //循环线程，不断的从队列取Task执行
    public Runnable coreThread = new Runnable() {

        Runnable currentRun = null;

        @Override
        public void run() {
            while (true){
                try {
                    currentRun = mQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                execute(currentRun);
            }
        }
    };

    //循环线程，不断的从队列取Task执行
    public Runnable uploadFileThread = new Runnable() {

        @Override
        public void run() {
            while (true){
                LogFileManager.getInstance().uploadLog();
                try {
                    Thread.sleep(LOG_UPLOAD_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    /**
     * 执行任务
     * @param runnable
     */
    public void execute(Runnable runnable){
        mThreadPoolExecutor.execute(runnable);
    }
}
