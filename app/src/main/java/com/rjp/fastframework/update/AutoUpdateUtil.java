package com.rjp.fastframework.update;

import android.Manifest;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.rjp.expandframework.interfaces.PermissionCallback;
import com.rjp.expandframework.utils.FileUtil;
import com.rjp.expandframework.utils.PermissionUtil;

/**
 * UpdateStateCallback  需要让一个Dialog或者Notification实现这个接口实现功能
 *
 * 在下载完成或者页面结束的时候需要将 updateTask 变量置空，防止泄露
 *
 * author : Gimpo create on 2019/1/16 14:59
 * email  : jimbo922@163.com
 */
public class AutoUpdateUtil {

    public static BaseDownloadTask updateTask;

    public static void update(final Context context, final String url, final UpdateStateCallback updateCallback) {
        if (TextUtils.isEmpty(url)) {
            return;
        }

        new PermissionUtil.Builder()
                .context(context)
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .build().request(new PermissionCallback() {
            @Override
            public void allow() {
                realUpdate(context, url, updateCallback);
            }

            @Override
            public void deny() {

            }
        });
    }

    @NonNull
    private static void realUpdate(Context context, String url, final UpdateStateCallback updateCallback) {
        String appPath = FileUtil.getAutoUpdateApkPath(context);
        FileDownloader.setup(context);
        updateTask = FileDownloader.getImpl().create(url).setWifiRequired(false).setPath(appPath).setListener(new FileDownloadSampleListener() {

            @Override
            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                if(updateCallback != null) {
                    updateCallback.process((int) (soFarBytes * 1.0 / totalBytes * 100));
                }
            }

            @Override
            protected void completed(BaseDownloadTask task) {
                if(updateCallback != null) {
                    updateCallback.process(100);
                    updateCallback.completed();
                }
            }

            @Override
            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                if(updateCallback != null) {
                    updateCallback.pause();
                }
            }
        });
        updateTask.start();
        if(updateCallback != null) {
            updateCallback.start();
        }
    }
}
