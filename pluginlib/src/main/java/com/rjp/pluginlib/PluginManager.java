package com.rjp.pluginlib;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class PluginManager {

    private static final PluginManager instance = new PluginManager();

    private PluginManager(){}

    public static PluginManager getInstance(){
        return instance;
    }

    private Context mContext;
    private PluginApk mPluginApk;

    public void init(Context context){
        mContext = context.getApplicationContext();
    }

    //加载插件apk
    public void loadApk(String apkPath){
        PackageInfo packageInfo = mContext.getPackageManager().getPackageArchiveInfo(
                apkPath,
                PackageManager.GET_ACTIVITIES | PackageManager.GET_SERVICES
        );
        if(packageInfo == null){
            return;
        }

        DexClassLoader classLoader = createDexClassLoader(apkPath);
        AssetManager assetManager = createAssetManager(apkPath);
        Resources resources = createResource(assetManager);
        mPluginApk = new PluginApk(packageInfo, classLoader, resources);
    }

    //创建访问插件apk的DexClassLoader
    private DexClassLoader createDexClassLoader(String apkPath) {
        File file = mContext.getDir("dex", Context.MODE_PRIVATE);
        return new DexClassLoader(apkPath, file.getAbsolutePath(), null, mContext.getClassLoader());
    }

    //创建访问插件apk资源的AssetManager
    private AssetManager createAssetManager(String apkPath) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method method = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
            method.invoke(assetManager, apkPath);
            return assetManager;
        }catch (Exception e){

        }
        return null;
    }

    //创建访问插件apk资源的Resource
    private Resources createResource(AssetManager assetManager) {
        Resources resources = mContext.getResources();
        return new Resources(assetManager, resources.getDisplayMetrics(), resources.getConfiguration());
    }

    public PluginApk getPluginApk() {
        return mPluginApk;
    }
}
