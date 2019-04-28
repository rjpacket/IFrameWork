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
import android.widget.TextView;
import android.widget.Toast;

import com.rjp.commonlib.ServiceFactory;
import com.rjp.expandframework.baseRecycler.base.BaseRViewActivity;
import com.rjp.expandframework.baseRecycler.base.RViewAdapter;
import com.rjp.expandframework.baseRecycler.helper.RefreshHelper;
import com.rjp.expandframework.baseRecycler.holder.RViewHolder;
import com.rjp.expandframework.baseRecycler.inteface.RViewItem;
import com.rjp.expandframework.liveDataBus.LiveDataBus;
import com.rjp.fastframework.R;
import com.rjp.fastframework.Hello;

import java.util.ArrayList;
import java.util.Random;

public class ComponentActivity extends BaseRViewActivity<String> {

    private ArrayList<String> strings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_component);
    }

    public void login(View view) {
        ServiceFactory.getInstance().getLoginService().start(this);
    }

    public void shop(View view) {
        startActivity(new Intent(ComponentActivity.this, NextActivity.class));
    }

    public void loadShop(View view) {
        LiveDataBus.get().with("rjp", String.class).postValue("你好啊");
    }

    @Override
    public RefreshHelper createRefreshHelper() {
        SwipeRefreshLayout refreshLayout = findViewById(R.id.refresh_layout);
        return RefreshHelper.createRefreshHelper(refreshLayout);
    }

    @Override
    public RecyclerView createRecyclerView() {
        return findViewById(R.id.recycler_view);
    }

    @Override
    public View createEmptyView() {
        return findViewById(R.id.stub_empty_view);
    }

    @Override
    public RViewAdapter<String> createRecyclerViewAdapter() {
        strings = new ArrayList<>();
        strings.add("");
        strings.add("");

        return new RViewAdapter<>(strings, new RViewItem<String>() {
            @Override
            public int getItemLayout() {
                return R.layout.item_list_view;
            }

            @Override
            public boolean isItemView(String entity, int position) {
                return true;
            }

            @Override
            public void convert(RViewHolder holder, String entity, int position) {
                TextView tvTitle = holder.getView(R.id.tv_title);
                tvTitle.setText(entity + "|" + position + "|" + "这是第几个元素啊");
            }
        });
    }

    @Override
    public void onRefresh() {
        ArrayList<String> datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            datas.add(String.valueOf(new Random().nextInt(100)));
        }
        helper.notifyData(null);
    }

    @Override
    public void onLoadMore() {
        ArrayList<String> datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            datas.add(String.valueOf(new Random().nextInt(100)));
        }
        helper.notifyData(null);
    }
}
