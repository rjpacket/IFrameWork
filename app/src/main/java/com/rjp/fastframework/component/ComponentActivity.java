package com.rjp.fastframework.component;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.rjp.commonlib.ServiceFactory;
import com.rjp.expandframework.baseRecycler.base.BaseRViewActivity;
import com.rjp.expandframework.baseRecycler.base.RViewAdapter;
import com.rjp.expandframework.baseRecycler.helper.RefreshHelper;
import com.rjp.expandframework.baseRecycler.holder.RViewHolder;
import com.rjp.expandframework.baseRecycler.inteface.RViewItem;
import com.rjp.expandframework.function.FunctionHasParamNoResult;
import com.rjp.expandframework.function.FunctionManager;
import com.rjp.expandframework.function.FunctionNoParamHasResult;
import com.rjp.expandframework.liveDataBus.LiveDataBus;
import com.rjp.expandframework.okhttp.CallbackListener;
import com.rjp.expandframework.okhttp.IOkHttp;
import com.rjp.expandframework.utils.FileUtil;
import com.rjp.fastframework.R;
import com.rjp.fastframework.Hello;
import com.rjp.pluginlib.PluginManager;
import com.rjp.pluginlib.ProxyActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ComponentActivity extends AppCompatActivity{

    private ArrayList<String> strings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_component);


        ScrollView scrollView = new ScrollView(this);

        PluginManager.getInstance().init(this);
    }

    public void login(View view) {
    }

    public void shop(View view) {
        startActivity(new Intent(ComponentActivity.this, NextActivity.class));
    }

    public void loadShop(View view) {
//        LiveDataBus.get().with("rjp", String.class).postValue("你好啊");
    }

    public void loadApk(View view) {
        String apkPath = FileUtil.copyAssetsToSD(this, "plugin.apk");
        PluginManager.getInstance().loadApk(apkPath);
    }

    public void skipApk(View view) {
        Intent intent = new Intent();
        intent.setClass(this, ProxyActivity.class);
        intent.putExtra("className", "com.rjp.pluginapk.ExtraActivity");
        startActivity(intent);
    }
}
