package com.rjp.expandframework.baseRecycler.inteface;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rjp.expandframework.baseRecycler.base.RViewAdapter;
import com.rjp.expandframework.baseRecycler.helper.RefreshHelper;

public interface RViewCreate<T> {

    RefreshHelper createRefreshHelper();

    RecyclerView createRecyclerView();

    RViewAdapter<T> createRecyclerViewAdapter();

    RecyclerView.LayoutManager createLayoutManager();

    RecyclerView.ItemDecoration createItemDecoration();

    int startPageNumber();

    View createEmptyView();
}
