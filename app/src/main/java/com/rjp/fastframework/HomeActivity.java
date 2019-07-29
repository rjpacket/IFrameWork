package com.rjp.fastframework;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rjp.expandframework.apm.AppLinkStarterTimeUtil;
import com.rjp.expandframework.apm.HookWindowManagerImpl;
import com.rjp.expandframework.apm.cpu.*;
import com.rjp.expandframework.apm.cpu.MainActivity;
import com.rjp.expandframework.interfaces.PermissionCallback;
import com.rjp.expandframework.utils.FileUtil;
import com.rjp.expandframework.utils.PermissionUtil;

import java.util.List;

public class HomeActivity extends Activity {

    private Context mContext;
    private List<HomeBean> homeBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mContext = this;

        String homeJson = FileUtil.openAssets(this, "home.json");
        Gson gson = new Gson();
        homeBeans = gson.fromJson(homeJson, new TypeToken<List<HomeBean>>() {
        }.getType());

        ListView listView = findViewById(R.id.home_list_view);
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return homeBeans.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if(convertView == null){
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_home_list_view, parent, false);
                }
                TextView tvTitle = convertView.findViewById(R.id.tv_title);
                HomeBean homeBean = homeBeans.get(position);
                tvTitle.setText(homeBean.buttonTxt);
                return convertView;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    HomeBean homeBean = homeBeans.get(position);
//                    Intent intent = new Intent();
//                    intent.setClass(mContext, Class.forName(homeBean.clazz));
//                    mContext.startActivity(intent);
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                    Toast.makeText(mContext, "没有这个页面", Toast.LENGTH_SHORT).show();
//                }

//                startActivity(new Intent(mContext, MainActivity.class));

//                CpuMonitor cpuMonitor = new CpuMonitor();
//                cpuMonitor.execShellGetCpuData("com.rjp.AAAAA");
//
//                String maxCpuFreq = CpuManager.getMaxCpuFreq();
//                Log.d("===>", maxCpuFreq);

                CpuMonitor.execShellGetCpuData("com.rjp.AAAAA");
            }
        });

//        HookWindowManagerImpl.hook(this);


    }

    public class HomeBean{
        public String clazz;
        public String buttonTxt;
    }

    @Override
    protected void onRestart() {
        AppLinkStarterTimeUtil.hotStart();
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        AppLinkStarterTimeUtil.coldEnd();

        AppLinkStarterTimeUtil.hotEnd();
    }
}
