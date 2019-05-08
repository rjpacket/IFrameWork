package com.rjp.pluginlib;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class ProxyActivity extends Activity {

    private String mClassName;
    private PluginApk mPluginApk;
    private IPlugin mIPlugin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mClassName = getIntent().getStringExtra("className");
        mPluginApk = PluginManager.getInstance().getPluginApk();

        launchPluginActivity();
    }

    private void launchPluginActivity() {
        if(mPluginApk == null){
            Log.d("====>", "加载插件失败");
            Toast.makeText(this, "加载插件失败", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        try {
            Class<?> clazz = mPluginApk.mDexClassLoader.loadClass(mClassName);
            Object object = clazz.newInstance();
            if(object instanceof IPlugin){
                mIPlugin = (IPlugin) object;
                mIPlugin.attach(this);
                Bundle bundle = new Bundle();
                bundle.putInt("FROM", IPlugin.FROM_APP);
                mIPlugin.onCreate(bundle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Resources getResources() {
        return mPluginApk != null ? mPluginApk.mResources : super.getResources();
    }

    @Override
    public AssetManager getAssets() {
        return mPluginApk != null ? mPluginApk.mAssetManager : super.getAssets();
    }

    @Override
    public ClassLoader getClassLoader() {
        return mPluginApk != null ? mPluginApk.mDexClassLoader : super.getClassLoader();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mIPlugin != null) {
            mIPlugin.onStart();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(mIPlugin != null) {
            mIPlugin.onRestart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mIPlugin != null) {
            mIPlugin.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mIPlugin != null) {
            mIPlugin.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mIPlugin != null) {
            mIPlugin.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mIPlugin != null) {
            mIPlugin.onDestroy();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(mIPlugin != null) {
            mIPlugin.onActivityResult(requestCode, resultCode, data);
        }
    }
}
