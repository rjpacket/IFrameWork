package com.rjp.expandframework.baseRecycler.helper;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.rjp.expandframework.baseRecycler.inteface.RefreshListener;

public class RefreshHelper {

    private RefreshListener refreshListener;
    private SwipeRefreshLayout refreshLayout;

    public void setRefreshListener(RefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }

    public static RefreshHelper createRefreshHelper(SwipeRefreshLayout refreshLayout){
        return new RefreshHelper(refreshLayout);
    }

    private RefreshHelper(SwipeRefreshLayout refreshLayout){
        this.refreshLayout = refreshLayout;
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(refreshListener != null){
                    refreshListener.onRefresh();
                }
            }
        });
    }

    public void finishRefreshing() {
        if(refreshLayout != null && refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
        }
    }

    public void hiddenRefreshView() {
        if(refreshLayout != null){
            refreshLayout.setVisibility(View.GONE);
        }
    }

    public void showRefreshView() {
        if(refreshLayout != null){
            refreshLayout.setVisibility(View.VISIBLE);
        }
    }
}
