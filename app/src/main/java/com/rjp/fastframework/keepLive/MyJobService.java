package com.rjp.fastframework.keepLive;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.util.List;


@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class MyJobService extends JobService {
    private JobScheduler mJobScheduler;

    @SuppressLint("WrongConstant")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("===>", "JobHandlerService  onStartCommand");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mJobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
            JobInfo.Builder builder = new JobInfo.Builder(startId++,
                    new ComponentName(getPackageName(), MyJobService.class.getName()));

            builder.setPeriodic(6);//设置间隔时间

            builder.setRequiresCharging(true);// 设置是否充电的条件,默认false

            builder.setRequiresDeviceIdle(true);// 设置手机是否空闲的条件,默认false

            builder.setPersisted(true);//设备重启之后你的任务是否还要继续执行

            if (mJobScheduler.schedule(builder.build()) <= 0) {
                Log.e("===>", "JobHandlerService  工作失败");
            } else {
                Log.e("===>", "JobHandlerService  工作成功");
            }
        }
        return START_STICKY;
    }


    @Override
    public boolean onStartJob(JobParameters params) {
        Log.e("===>", "JobHandlerService  服务启动");
        if (!isServiceRunning("com.amap.api.location.APSService")) {
            init_Aps();
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        if (!isServiceRunning("com.amap.api.location.APSService")) {
            init_Aps();
        }
        return false;
    }

    private void init_Aps() {
        Log.e("===>", "开始定位");
    }

    // 服务是否运行
    public boolean isServiceRunning(String serviceName) {
        boolean isRunning = false;
        ActivityManager am = (ActivityManager) this
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> lists = am.getRunningAppProcesses();
        // 获取运行服务再启动
        for (ActivityManager.RunningAppProcessInfo info : lists) {
            System.out.println(info.processName);
            if (info.processName.equals(serviceName)) {
                isRunning = true;
            }
        }
        return isRunning;
    }
}