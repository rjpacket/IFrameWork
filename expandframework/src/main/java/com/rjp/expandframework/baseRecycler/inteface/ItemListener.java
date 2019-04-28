package com.rjp.expandframework.baseRecycler.inteface;

import android.view.View;

public interface ItemListener<T> {

    void onItemClick(View view, T entity, int position);
}
