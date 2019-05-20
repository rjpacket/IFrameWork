package com.rjp.expandframework.baseRecycler.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.rjp.expandframework.baseRecycler.holder.RViewHolder;
import com.rjp.expandframework.baseRecycler.inteface.ItemListener;
import com.rjp.expandframework.baseRecycler.inteface.RViewItem;
import com.rjp.expandframework.baseRecycler.manager.RViewItemManager;

import java.util.ArrayList;
import java.util.List;

public class RViewAdapter<T> extends RecyclerView.Adapter<RViewHolder> {

    private RViewItemManager<T> itemManager;

    private ItemListener<T> itemListener;

    private List<T> datas;

    public RViewAdapter(List<T> datas) {
        if (datas == null) {
            this.datas = new ArrayList<>();
        }
        this.datas = datas;
        itemManager = new RViewItemManager<>();
    }


    @NonNull
    @Override
    public RViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RViewItem<T> item = itemManager.getRViewItem(viewType);
        int itemLayout = item.getItemLayout();
        final RViewHolder holder = RViewHolder.createViewHolder(parent.getContext(), parent, itemLayout);

        holder.getmCurrentView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemListener != null) {
                    itemListener.onItemClick(holder.getmCurrentView(), datas.get(holder.getAdapterPosition()), holder.getAdapterPosition());
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RViewHolder holder, int position) {
        itemManager.convert(holder, datas.get(position), position);
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (hasMultiStyle()) {
            return itemManager.getRViewItemType(datas.get(position), position);
        }
        return super.getItemViewType(position);
    }

    private boolean hasMultiStyle() {
        return itemManager.getItemViewStylesCount() > 0;
    }

    public void setItemListener(ItemListener<T> itemListener) {
        this.itemListener = itemListener;
    }

    public void addDatas(List<T> datas) {
        if (datas == null) {
            return;
        }
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    public void updateDatas(List<T> datas) {
        if (datas == null) {
            return;
        }
        this.datas = datas;
        notifyDataSetChanged();
    }

    public void addItemStyles(RViewItem<T> item) {
        itemManager.addStyles(item);
    }
}
