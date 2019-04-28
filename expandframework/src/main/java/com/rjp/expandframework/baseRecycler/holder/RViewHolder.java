package com.rjp.expandframework.baseRecycler.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;
    private View mCurrentView;

    public RViewHolder(View itemView) {
        super(itemView);

        mCurrentView = itemView;
        mViews = new SparseArray<>();
    }

    //创建viewHolder
    public static RViewHolder createViewHolder(Context context, ViewGroup parent, int layoutId){
        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new RViewHolder(view);
    }

    public<T extends View> T getView(int viewId){
        View view = mViews.get(viewId);
        if(view == null){
            view = mCurrentView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getmCurrentView() {
        return mCurrentView;
    }
}
