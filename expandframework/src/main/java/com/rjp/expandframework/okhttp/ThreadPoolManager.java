package com.rjp.expandframework.okhttp;

import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolManager {

    private static ThreadPoolManager threadPoolManager = new ThreadPoolManager();

    public static ThreadPoolManager getInstance(){
        return threadPoolManager;
    }

    //队列
    private LinkedBlockingQueue<Runnable> mQueue = new LinkedBlockingQueue<>();

    //失败的任务队列
    private DelayQueue<HttpTask> mDelayQueue = new DelayQueue<>();

    public void addDelayTask(HttpTask ht){
        if(ht != null){
            ht.setDelayTime(3000);
            mDelayQueue.offer(ht);
        }
    }

    //添加任务
    public void addTask(Runnable runnable){
        if(runnable != null){
            mQueue.add(runnable);
        }
    }

    //线程池
    private ThreadPoolExecutor mThreadPoolExecutor;

    private ThreadPoolManager(){
        mThreadPoolExecutor = new ThreadPoolExecutor(
                3,
                10,
                15,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(4),
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        addTask(r);
                    }
                });

        //开启循环线程
        execute(coreThread);
        //开启延迟失败线程
        execute(delayThread);
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

    //延迟线程
    public Runnable delayThread = new Runnable() {

        HttpTask delayRun = null;

        @Override
        public void run() {
            while (true){
                try {
                    delayRun = mDelayQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(delayRun.getRetryCount() < 3){
                    execute(delayRun);
                    delayRun.setRetryCount(delayRun.getRetryCount() + 1);
                    Log.d("------->", "重试");
                }else{
                    Log.d("------->", "丢弃");
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
