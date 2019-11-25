package com.rjp.expandframework.IHttp.responseHandle;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

/**
 * 每一次请求之后，可能需要在每一个正常请求流程里插入操作
 * author: jinpeng.ren create at 2019/11/18 11:47
 * email: jinpeng.ren@11bee.com
 */
public class AbstractResponseHandle<T> {

    private int page;
    private boolean hasNextPage;
    private List<T> models;
    private List<T> tempModels;
    private SmartRefreshLayout refreshLayout;

    public static class Builder<T>{
        private int page;
        private boolean hasNextPage;
        private List<T> models;
        private List<T> tempModels;
        private SmartRefreshLayout refreshLayout;

        public Builder<T> page(int page){
            this.page = page;
            return this;
        }

        public Builder<T> hasNextPage(boolean hasNextPage){
            this.hasNextPage = hasNextPage;
            return this;
        }

        public Builder<T> models(List<T> models){
            this.models = models;
            return this;
        }

        public Builder<T> tempModels(List<T> tempModels){
            this.tempModels = tempModels;
            return this;
        }

        public Builder<T> refreshLayout(SmartRefreshLayout refreshLayout){
            this.refreshLayout = refreshLayout;
            return this;
        }

        public AbstractResponseHandle<T> build(AbstractResponseHandle<T> handle){
            handle.page = page;
            handle.hasNextPage = hasNextPage;
            handle.models = models;
            handle.tempModels = tempModels;
            handle.refreshLayout = refreshLayout;
            return handle;
        }
    }

    public boolean isFirstPage() {
        return page == 1;
    }

    public void clearModels() {
        models.clear();
    }

    public boolean isTempModelsEmpty() {
        return tempModels == null || tempModels.size() == 0;
    }

    public void showEmptyView() {

    }

    public void firstPageEmpty() {
        showEmptyView();
        refreshLayout.setEnableLoadMore(false);
    }

    public void addFirstPageModels() {
        models.addAll(tempModels);
        if (hasNextPage) {
            refreshLayout.finishLoadMore();
        } else {
            refreshLayout.finishLoadMoreWithNoMoreData();
        }
    }

    public void otherPageEmpty() {
        refreshLayout.finishLoadMoreWithNoMoreData();
    }

    public void addOtherPageModels() {
        models.addAll(tempModels);
        if (hasNextPage) {
            refreshLayout.finishLoadMore();
        } else {
            refreshLayout.finishLoadMoreWithNoMoreData();
        }
    }

    public void handle() {
        if (isFirstPage()) {
            clearModels();
            if (isTempModelsEmpty()) {
                firstPageEmpty();
            } else {
                addFirstPageModels();
            }
        } else {
            if (isTempModelsEmpty()) {
                otherPageEmpty();
            } else {
                addOtherPageModels();
            }
        }
    }
}
