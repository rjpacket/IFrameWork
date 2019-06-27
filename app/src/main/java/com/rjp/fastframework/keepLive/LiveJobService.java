package com.rjp.fastframework.keepLive;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.text.TextUtils;
import android.util.Log;

import com.rjp.fastframework.HomeActivity;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class LiveJobService extends JobIntentService {


    public static String serviceName = "CLASSNAME";
    private String liveService;
    private Timer timer = new Timer();

    private long count;
    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            count++;
            if (TextUtils.isEmpty(liveService)) return;
            boolean serviceWork = isServiceRunning(liveService);
            if (!serviceWork) {
                Log.e("LiveJobService", "服务" + liveService + "被干掉了" + count);
                count = 0;
                try {
                    Class<Service> serviceClass = (Class<Service>) Class.forName(liveService);
                    //尝试拉起该服务
                    if (Build.VERSION.SDK_INT >= 26) {
                        getApplication().startActivity(new Intent(getApplication(), HomeActivity.class));
                    } else {
                        startService(new Intent(getApplicationContext(), serviceClass));
                    }

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("LiveJobService", "服务" + liveService + "正在运行" + count);
            }
        }
    };

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


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("LiveJobService", "进程被杀掉了");
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        liveService = intent.getStringExtra(serviceName);
        //LiveJobService使用的是队列的数据结构
        //我们搞一个定时任务来拉活传过来的服务
        if (timer != null && timerTask != null) {
            timer.schedule(timerTask, 1000, 1000);
        }
    }
}