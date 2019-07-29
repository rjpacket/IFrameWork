package com.rjp.expandframework.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

    public Context mContext;

    boolean isCreateView = false;//表示Fragment此时是否完成onCreateView
    boolean isVisible = false;//表示Fragment此时是否显示在前台，取决于getUserVisibleHint();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mContext = getContext();
        isCreateView = true;
        if (isVisible && isCreateView) {
            onVisible();
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        isCreateView = false;
        super.onDestroyView();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            if (isVisible && isCreateView) {
                onVisible();
            }
        } else {
            isVisible = false;
            onInVisible();
        }
    }

    /**
     * 页面可见
     */
    public abstract void onVisible();

    /**
     * 页面不可见
     */
    public abstract void onInVisible();

}
