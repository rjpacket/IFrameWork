package com.rjp.fastframework.keepLive;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import com.rjp.fastframework.R;

/**
 * 灰色保活  提升进程优先级
 */
public class KeepLiveService extends Service {

    public static final int NOTIFICATION_ID = 0x11;

    public KeepLiveService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //API 18以下，直接发送Notification并将其置为前台
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            startForeground(NOTIFICATION_ID, new Notification());
        } else {
            //API 18以上，发送Notification并将其置为前台后，启动InnerService
            Notification.Builder builder = new Notification.Builder(this);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            startForeground(NOTIFICATION_ID, builder.build());
            startService(new Intent(this, InnerService.class));
        }
    }

    public static class InnerService extends Service {
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public void onCreate() {
            super.onCreate();
            //发送与KeepLiveService中ID相同的Notification，然后将其取消并取消自己的前台显示
//            Notification.Builder builder = new Notification.Builder(this);
//            builder.setSmallIcon(R.mipmap.ic_launcher);
//            builder.setContentTitle("app正在持续为您服务...");
            startForeground(NOTIFICATION_ID, new Notification());
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    stopForeground(true);
//                    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                    manager.cancel(NOTIFICATION_ID);
                    stopSelf();
                }
            }, 500);

        }
    }
}