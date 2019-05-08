package com.rjp.pluginlib;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class PluginActivity extends Activity implements IPlugin {

    private int mFrom = FROM_SYSTEM;

    public Activity getContext() {
        if(mFrom == FROM_SYSTEM){
            return this;
        }
        return mProxyActivity;
    }

    //插件的上下文
    private Activity mProxyActivity;

    @Override
    public void attach(Activity proxyActivity) {
        mProxyActivity = proxyActivity;
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        if(saveInstanceState != null){
            mFrom = saveInstanceState.getInt("FROM");
        }
        if(mFrom == FROM_SYSTEM) {
            super.onCreate(saveInstanceState);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        if(mFrom == FROM_SYSTEM) {
            super.setContentView(layoutResID);
        }else{
            mProxyActivity.setContentView(layoutResID);
        }
    }

    @Override
    public void onRestart() {
        if(mFrom == FROM_SYSTEM) {
            super.onRestart();
        }
    }

    @Override
    public void onStart() {
        if(mFrom == FROM_SYSTEM) {
            super.onStart();
        }
    }

    @Override
    public void onResume() {
        if(mFrom == FROM_SYSTEM) {
            super.onResume();
        }
    }

    @Override
    public void onPause() {
        if(mFrom == FROM_SYSTEM) {
            super.onPause();
        }
    }

    @Override
    public void onStop() {
        if(mFrom == FROM_SYSTEM) {
            super.onStop();
        }
    }

    @Override
    public void onDestroy() {
        if(mFrom == FROM_SYSTEM) {
            super.onDestroy();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(mFrom == FROM_SYSTEM) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
