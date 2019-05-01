package com.rjp.pluginlib;

import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;

import dalvik.system.DexClassLoader;

public class PluginApk {

    public PackageInfo mPackageInfo;
    public DexClassLoader mDexClassLoader;
    public AssetManager mAssetManager;
    public Resources mResources;

    public PluginApk(PackageInfo mPackageInfo, DexClassLoader mDexClassLoader, Resources mResources) {
        this.mPackageInfo = mPackageInfo;
        this.mDexClassLoader = mDexClassLoader;
        this.mResources = mResources;
        this.mAssetManager = mResources.getAssets();
    }
}
