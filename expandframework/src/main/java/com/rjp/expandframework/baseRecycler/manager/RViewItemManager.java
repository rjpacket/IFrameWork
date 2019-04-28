package com.rjp.expandframework.baseRecycler.manager;

import android.support.v4.util.SparseArrayCompat;

import com.rjp.expandframework.baseRecycler.holder.RViewHolder;
import com.rjp.expandframework.baseRecycler.inteface.RViewItem;

public class RViewItemManager<T> {

    private SparseArrayCompat<RViewItem<T>> styles = new SparseArrayCompat<>();

    public void addStyles(RViewItem<T> item){
        if(item != null){
            styles.put(styles.size(), item);
        }
    }

    public int getItemViewStylesCount(){
        return styles.size();
    }

    public RViewItem<T> getRViewItem(int viewType) {
        return styles.get(viewType);
    }

    public int getRViewItemType(T entity, int position){
        int size = styles.size();
        for (int i = size - 1; i >= 0; i--) {
            RViewItem<T> trViewItem = styles.valueAt(i);
            if(trViewItem.isItemView(entity, position)){
                return styles.keyAt(i);
            }
        }
        return -1;
    }

    public void convert(RViewHolder holder, T entity, int position) {
        int size = styles.size();
        for (int i = size - 1; i >= 0; i--) {
            RViewItem<T> trViewItem = styles.valueAt(i);
            if(trViewItem.isItemView(entity, position)){
                trViewItem.convert(holder, entity, position);
            }
        }
    }
}
