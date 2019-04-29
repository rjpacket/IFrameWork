package com.rjp.expandframework.baseRecycler.helper;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.rjp.expandframework.baseRecycler.base.RViewAdapter;
import com.rjp.expandframework.baseRecycler.inteface.RViewCreate;
import com.rjp.expandframework.baseRecycler.inteface.RefreshListener;

import java.util.List;

public class RViewHelper<T> {

    private RefreshHelper refreshHelper;
    private RefreshListener refreshListener;
    private RecyclerView recyclerView;
    private View emptyView;
    private RViewAdapter<T> adapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.ItemDecoration itemDecoration;
    private int startPageNumber;
    private int currentPageNumber;
    private boolean hasNoMoreData;

    public RViewHelper(Builder<T> builder) {
        refreshHelper = builder.viewCreate.createRefreshHelper();
        recyclerView = builder.viewCreate.createRecyclerView();
        emptyView = builder.viewCreate.createEmptyView();
        adapter = builder.viewCreate.createRecyclerViewAdapter();
        layoutManager = builder.viewCreate.createLayoutManager();
        itemDecoration = builder.viewCreate.createItemDecoration();
        currentPageNumber = startPageNumber = builder.viewCreate.startPageNumber();

        refreshListener = builder.refreshListener;

        init();
    }

    private void init() {
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(adapter);

        refreshHelper.setRefreshListener(new RefreshListener() {
            @Override
            public void onRefresh() {
                currentPageNumber = startPageNumber;
                if (refreshListener != null) {
                    refreshListener.onRefresh();
                }
                refreshHelper.finishRefreshing();
            }

            @Override
            public void onLoadMore() {

            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE && isSlideToBottom(recyclerView) && !hasNoMoreData) {
                    currentPageNumber++;
                    if (refreshListener != null) {
                        refreshListener.onLoadMore();
                    }
                }
            }
        });
    }

    public boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }

    public void notifyData(List<T> datas) {
        if (currentPageNumber == startPageNumber) {
            adapter.updateDatas(datas);
            if (datas == null || datas.size() == 0) {
                refreshHelper.hiddenRefreshView();
                emptyView.setVisibility(View.VISIBLE);
            } else {
                hasNoMoreData = false;
                refreshHelper.showRefreshView();
                emptyView.setVisibility(View.GONE);
            }
        } else {
            if (datas == null || datas.size() == 0) {
                hasNoMoreData = true;
                Toast.makeText(recyclerView.getContext(), "no more data", Toast.LENGTH_SHORT).show();
            }else {
                adapter.addDatas(datas);
                Toast.makeText(recyclerView.getContext(), "add", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public RViewAdapter<T> getAdapter() {
        return adapter;
    }

    public static class Builder<T> {

        private RViewCreate<T> viewCreate;
        private RefreshListener refreshListener;

        public Builder(RViewCreate<T> viewCreate, RefreshListener refreshListener) {
            this.viewCreate = viewCreate;
            this.refreshListener = refreshListener;
        }

        public RViewHelper<T> build() {
            return new RViewHelper<>(this);
        }
    }
}
