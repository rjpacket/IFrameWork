package com.rjp.expandframework.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class LazyFragment extends Fragment {
    /**
     * 标记已加载完成，只能加载一次
     */
    private boolean hasLoaded = false;
    /**
     * 标记Fragment是否已经onCreate
     */
    private boolean isCreated = false;
    /**
     * 界面对于用户是否可见
     */
    private boolean isVisibleToUser = false;
    private View view;
 
    public LazyFragment() {
        // Required empty public constructor
    }
 
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        init(getView(inflater, getLayoutId(), container), savedInstanceState);
        return view;
    }
 
    private View getView(LayoutInflater inflater, int layoutId, ViewGroup container) {
        return inflater.inflate(layoutId, container, false);
    }
 
 
    public void init(View view, Bundle savedInstanceState) {
        isCreated = true;
        this.view = view;
        initViews(this.view,savedInstanceState);
        lazyLoad(this.view,savedInstanceState);
    }
    /**
     * 监听界面是否展示给用户，实现懒加载
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.d("===>", "setUserVisibleHint");
        this.isVisibleToUser = isVisibleToUser;
        lazyLoad(view, null);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        Log.d("===>", "onHiddenChanged");
        super.onHiddenChanged(hidden);
        this.isVisibleToUser = !hidden;
        lazyLoad(view, null);
    }

    public abstract void initViews(View view, Bundle savedInstanceState);
 
 
    /**
     * 懒加载方法，获取数据什么的放到这边来使用，在切换到这个界面时才进行网络请求
     */
    private void lazyLoad(View view, Bundle savedInstanceState) {
 
        //如果该界面不对用户显示、已经加载、fragment还没有创建，
        //三种情况任意一种，不获取数据
        if (!isVisibleToUser || hasLoaded || !isCreated) {
            return;
        }
        lazyInit(view, savedInstanceState);
        //注：关键步骤，确保数据只加载一次
        hasLoaded = true;
    }
 
    /**
     * 子类必须实现的方法，这个方法里面的操作都是需要懒加载的
     */
    public abstract void lazyInit(View view, Bundle savedInstanceState);
 
    public abstract int getLayoutId();
 
 
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isCreated = false;
        hasLoaded = false;
    }
}