package com.rjp.expandframework.baseRecycler.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rjp.expandframework.baseRecycler.helper.RViewHelper;
import com.rjp.expandframework.baseRecycler.inteface.RefreshListener;
import com.rjp.expandframework.baseRecycler.inteface.RViewCreate;

import java.util.ArrayList;

public abstract class BaseRViewActivity<T> extends AppCompatActivity implements RViewCreate<T>, RefreshListener {

    public RViewHelper<T> helper;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        helper = new RViewHelper.Builder<>(this, this).build();
    }

    @Override
    public RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(this);
    }

    @Override
    public RecyclerView.ItemDecoration createItemDecoration() {
        return new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
    }

    @Override
    public int startPageNumber() {
        return 0;
    }
}
