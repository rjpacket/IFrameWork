package com.rjp.fastframework;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.rjp.commonlib.ApplicationConfig;
import com.rjp.commonlib.IAppInit;
import com.rjp.expandframework.aop.TimeLog;
import com.rjp.expandframework.interfaces.ImageLoader;
import com.rjp.expandframework.utils.AppUtil;
import com.rjp.expandframework.utils.ImageLoaderUtil;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.tinker.entry.ApplicationLike;
import com.tinkerpatch.sdk.TinkerPatch;
import com.tinkerpatch.sdk.loader.TinkerPatchApplicationLike;

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
        initTinker();

        INSTANCE = this;

        init(this);

        AppUtil.initApp(this);

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
                Glide.with(context).load(url).asBitmap().into(new BitmapImageViewTarget(imageView){
                    @Override
                    protected void setResource(Bitmap resource) {
                        Bitmap bitmap = ThumbnailUtils.extractThumbnail(resource, width, height);
                        imageView.setImageBitmap(bitmap);
                    }
                });
            }

            @Override
            public void load(Context context, String url, final ImageView imageView, int placeholder, final int width, final int height) {
                Glide.with(context).load(url).asBitmap().placeholder(placeholder).into(new BitmapImageViewTarget(imageView){
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
    }

    private void initTinker() {
        // 我们可以从这里获得Tinker加载过程的信息
        tinkerApplicationLike = TinkerPatchApplicationLike.getTinkerPatchApplicationLike();

        // 初始化TinkerPatch SDK, 更多配置可参照API章节中的,初始化SDK
        TinkerPatch.init(tinkerApplicationLike)
                .reflectPatchLibrary()
                .setPatchRollbackOnScreenOff(true)
                .setPatchRestartOnSrceenOff(true)
                .setFetchPatchIntervalByHours(3);

        // 每隔3个小时(通过setFetchPatchIntervalByHours设置)去访问后台时候有更新,通过handler实现轮训的效果
        TinkerPatch.with().fetchPatchUpdateAndPollWithInterval();
    }

    private static float sNoncompatDensity;
    private static float sNoncompatScaledDensity;

    public static void setCustomDensity(Activity activity, final Application application){
        DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();

        if(sNoncompatDensity == 0){
            sNoncompatDensity = appDisplayMetrics.density;
            sNoncompatScaledDensity = appDisplayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if(newConfig != null && newConfig.fontScale > 0){
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

        if(activity != null) {
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
                if(instance instanceof IAppInit){
                    ((IAppInit)instance).init(this);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
