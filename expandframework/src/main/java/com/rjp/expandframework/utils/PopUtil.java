package com.rjp.expandframework.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.rjp.expandframework.R;
import com.rjp.expandframework.adapters.CommonAdapter;
import com.rjp.expandframework.adapters.ViewHolder;
import com.rjp.expandframework.interfaces.OnPopupBindDataListener;
import com.rjp.expandframework.views.AlphaPopupWindow;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * author : Gimpo create on 2018/7/20 10:43
 * email  : jimbo922@163.com
 */
public class PopUtil<T> {
    private AlphaPopupWindow popupWindow;

    public static class Builder<T> {
        private Context context;
        private int layoutId;
        private List<T> models;
        private int width;
        private int anim;
        private OnPopupBindDataListener<T> onPopupBindDataListener;

        public PopUtil.Builder<T> context(Context context) {
            this.context = context;
            return this;
        }

        public PopUtil.Builder<T> layoutId(int layoutId) {
            this.layoutId = layoutId;
            return this;
        }

        public PopUtil.Builder<T> models(List<T> models) {
            this.models = models;
            return this;
        }

        public PopUtil.Builder<T> bindData(OnPopupBindDataListener<T> onPopupBindDataListener) {
            this.onPopupBindDataListener = onPopupBindDataListener;
            return this;
        }

        public PopUtil.Builder<T> width(int width) {
            this.width = AppUtil.dp2px(width);
            return this;
        }

        public PopUtil.Builder<T> anim(int anim) {
            this.anim = anim;
            return this;
        }

        public PopUtil<T> build() {
            PopUtil<T> dialogUtils = new PopUtil<>();
            if (context == null) {
                throw new IllegalArgumentException("context must be not null");
            }
            dialogUtils.context = this.context;
            if (layoutId == 0) {
                this.layoutId = R.layout.item_common_popup_layout;
            }
            dialogUtils.layoutId = this.layoutId;
            if (width == 0) {
                width = context.getResources().getDisplayMetrics().widthPixels;
            }
            dialogUtils.width = this.width;
            if(anim == 0){
                anim = R.style.dialog_default_anim;
            }
            dialogUtils.anim = this.anim;
            dialogUtils.models = this.models;
            dialogUtils.onPopupBindDataListener = this.onPopupBindDataListener;
            return dialogUtils;
        }
    }

    private Context context;
    private int layoutId;
    private int width;
    private int anim;
    private List<T> models;
    private OnPopupBindDataListener<T> onPopupBindDataListener;

    public AlphaPopupWindow show() {
        View view;
        if(models != null && models.size() > 0){
            view = LayoutInflater.from(context).inflate(R.layout.popup_common_list_view, null);
            ListView listView = view.findViewById(R.id.popup_list_view);
            listView.setAdapter(new CommonAdapter<T>(context, layoutId, models) {
                @Override
                protected void convert(ViewHolder viewHolder, T item, int position) {
                    if(onPopupBindDataListener != null){
                        onPopupBindDataListener.convert(viewHolder, item, position);
                    }
                }
            });
        }else{
            view = LayoutInflater.from(context).inflate(layoutId, null);
        }
        popupWindow = new AlphaPopupWindow((Activity) context, view, width, WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setAnimationStyle(anim);
        return popupWindow;
    }
}
