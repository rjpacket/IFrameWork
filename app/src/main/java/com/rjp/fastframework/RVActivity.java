package com.rjp.fastframework;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.rjp.expandframework.baseRecycler.base.BaseRViewActivity;
import com.rjp.expandframework.baseRecycler.base.RViewAdapter;
import com.rjp.expandframework.baseRecycler.helper.RefreshHelper;
import com.rjp.expandframework.baseRecycler.holder.RViewHolder;
import com.rjp.expandframework.baseRecycler.inteface.RViewItem;
import com.rjp.expandframework.liveDataBus.LiveDataBus;

import java.util.ArrayList;
import java.util.List;

public class RVActivity extends BaseRViewActivity<String> {

    private RViewAdapter<String> rViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv);
    }

    @Override
    public RefreshHelper createRefreshHelper() {
        return RefreshHelper.createRefreshHelper((SwipeRefreshLayout) findViewById(R.id.refresh_layout));
    }

    @Override
    public RecyclerView createRecyclerView() {
        return findViewById(R.id.recycler_view);
    }

    @Override
    public RViewAdapter<String> createRecyclerViewAdapter() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("1");
        strings.add("1");
        strings.add("1");
        strings.add("1");
        strings.add("2");
        strings.add("2");
        strings.add("2");
        strings.add("1");
        strings.add("1");
        strings.add("1");
        strings.add("1");
        strings.add("1");
        strings.add("1");
        strings.add("2");
        strings.add("2");
        strings.add("2");
        strings.add("2");
        strings.add("2");
        strings.add("2");
        strings.add("2");
        strings.add("1");
        strings.add("1");
        strings.add("1");
        RViewItem<String> item1 = new RViewItem<String>() {
            @Override
            public int getItemLayout() {
                return R.layout.item_list_view;
            }

            @Override
            public boolean isItemView(String entity, int position) {
                return "1".equals(entity);
            }

            @Override
            public void convert(RViewHolder holder, String entity, int position) {

            }
        };
        RViewItem<String> item2 = new RViewItem<String>() {
            @Override
            public int getItemLayout() {
                return R.layout.item_home_list_view;
            }

            @Override
            public boolean isItemView(String entity, int position) {
                return "2".equals(entity);
            }

            @Override
            public void convert(RViewHolder holder, String entity, int position) {

            }
        };
        rViewAdapter = new RViewAdapter<>(strings);
        rViewAdapter.addItemStyles(item1);
        rViewAdapter.addItemStyles(item2);
        return rViewAdapter;
    }

    @Override
    public View createEmptyView() {
        View stubEmpty = findViewById(R.id.stub_empty_view);
        LiveDataBus.get().with("empty_click").observe(this, new Observer<Object>() {
            @Override
            public void onChanged(@Nullable Object o) {
                ArrayList<String> datas = new ArrayList<>();
                datas.add("1");
                datas.add("2");
                helper.notifyData(datas);
            }
        });
        return stubEmpty;
    }

    @Override
    public void onRefresh() {
        helper.notifyData(null);
    }

    @Override
    public void onLoadMore() {

    }
}
