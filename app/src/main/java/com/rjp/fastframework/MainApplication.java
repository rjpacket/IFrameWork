package com.rjp.fastframework;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.support.multidex.MultiDex;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.rjp.commonlib.ApplicationConfig;
import com.rjp.commonlib.IAppInit;
import com.rjp.expandframework.aop.TimeLog;
import com.rjp.expandframework.interfaces.ImageLoader;
import com.rjp.expandframework.log.LogFileManager;
import com.rjp.expandframework.utils.AppUtil;
import com.rjp.expandframework.utils.ImageLoaderUtil;
import com.rjp.fastframework.keepLive.KeepLiveService;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.tinker.entry.ApplicationLike;

/**
 * author : Gimpo create on 2018/11/5 18:14
 * email  : jimbo922@163.com
 */
public class MainApplication extends Application implements IAppInit {

    public static Application INSTANCE;
    private ApplicationLike tinkerApplicationLike;

    @TimeLog(tag = "MainApplication")
    @Override
    public void onCreate() {
        super.onCreate();

        INSTANCE = this;

        init(this);

        AppUtil.initApp(this);

        LogFileManager.init(this);

        /**
         * 图片加载框架
         */
        ImageLoaderUtil.init(new ImageLoader() {
            @Override
            public void load(Context context, String url, ImageView imageView) {
                Glide.with(context).load(url).into(imageView);
            }

            @Override
            public void load(Context context, String url, ImageView imageView, int placeholder) {
                Glide.with(context).load(url).placeholder(placeholder).into(imageView);
            }

            @Override
            public void load(Context context, String url, final ImageView imageView, final int width, final int height) {
                Glide.with(context).load(url).asBitmap().into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        Bitmap bitmap = ThumbnailUtils.extractThumbnail(resource, width, height);
                        imageView.setImageBitmap(bitmap);
                    }
                });
            }

            @Override
            public void load(Context context, String url, final ImageView imageView, int placeholder, final int width, final int height) {
                Glide.with(context).load(url).asBitmap().placeholder(placeholder).into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        Bitmap bitmap = ThumbnailUtils.extractThumbnail(resource, width, height);
                        imageView.setImageBitmap(bitmap);
                    }
                });
            }
        });

        LeakCanary.install(this);

        setCustomDensity(null, this);

//        startService(new Intent(this, LocalService.class));
//        startService(new Intent(this, RemoteService.class));
        startService(new Intent(this, KeepLiveService.class));

        Bugly.init(this, "892d945f22", false);
        //是否设置为开发设备
        Bugly.setIsDevelopmentDevice(getApplicationContext(), true);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);

        // 安装tinker
        Beta.installTinker();
    }

    private static float sNoncompatDensity;
    private static float sNoncompatScaledDensity;

    public static void setCustomDensity(Activity activity, final Application application) {
        DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();

        if (sNoncompatDensity == 0) {
            sNoncompatDensity = appDisplayMetrics.density;
            sNoncompatScaledDensity = appDisplayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        sNoncompatScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }

        float targetDensity = appDisplayMetrics.widthPixels / 360;
        float targetScaledDensity = targetDensity * (sNoncompatScaledDensity / sNoncompatDensity);
        int targetDensityDpi = (int) (160 * targetDensity);

        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaledDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;

        if (activity != null) {
            DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
            activityDisplayMetrics.density = targetDensity;
            activityDisplayMetrics.scaledDensity = targetScaledDensity;
            activityDisplayMetrics.densityDpi = targetDensityDpi;
        }
    }

    @Override
    public void init(Application application) {
        for (String clazz : ApplicationConfig.APPLICATIONS) {
            try {
                Class<?> object = Class.forName(clazz);
                Object instance = object.newInstance();
                if (instance instanceof IAppInit) {
                    ((IAppInit) instance).init(this);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
