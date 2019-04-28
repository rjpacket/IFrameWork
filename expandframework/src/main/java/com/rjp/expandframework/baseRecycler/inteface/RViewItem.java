package com.rjp.expandframework.baseRecycler.inteface;

import com.rjp.expandframework.baseRecycler.holder.RViewHolder;

public interface RViewItem<T> {
    int getItemLayout();

    boolean isItemView(T entity, int position);

    void convert(RViewHolder holder, T entity, int position);
}
