package com.rjp.fastframework;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.rjp.expandframework.utils.FileUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

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
                try {
                    HomeBean homeBean = homeBeans.get(position);
                    Intent intent = new Intent();
                    intent.setClass(mContext, Class.forName(homeBean.clazz));
                    mContext.startActivity(intent);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, "没有这个页面", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Observable.timer(2, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long value) {
                        System.out.println(value);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });

        Observable.interval(1, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long value) {
                        System.out.println("------" + value);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public class HomeBean{
        public String clazz;
        public String buttonTxt;
    }
}
