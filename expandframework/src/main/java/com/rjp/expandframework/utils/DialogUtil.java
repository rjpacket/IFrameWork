package com.rjp.expandframework.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.rjp.expandframework.R;
import com.rjp.expandframework.interfaces.OnDialogClickListener;

/**
 * author : Gimpo create on 2018/5/23 10:39
 * email  : jimbo922@163.com
 */
public class DialogUtil {

    private Dialog dialog;

    public static class Builder {
        private Context context;
        private View layout;
        private int width;
        private int gravity;
        private int anim;
        private String title = "提示";
        private String notice = "";
        private String confirm = "确定";
        private String cancel = "取消";
        private boolean outsideCancel = false;
        private OnDialogClickListener onDialogClickListener;

        public Builder context(Context context) {
            this.context = context;
            return this;
        }

        public Builder layout(View layout) {
            this.layout = layout;
            return this;
        }

        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public Builder gravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public Builder anim(int anim) {
            this.anim = anim;
            return this;
        }

        public Builder outsideCancel(boolean outsideCancel) {
            this.outsideCancel = outsideCancel;
            return this;
        }

        public Builder onClickListener(OnDialogClickListener onClickListener) {
            this.onDialogClickListener = onClickListener;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder notice(String notice) {
            this.notice = notice;
            return this;
        }

        public Builder confirm(String confirm) {
            this.confirm = confirm;
            return this;
        }

        public Builder cancel(String cancel) {
            this.cancel = cancel;
            return this;
        }

        public DialogUtil build() {
            DialogUtil DialogUtil = new DialogUtil();
            if (context == null) {
                throw new IllegalArgumentException("context must be not null");
            }
            DialogUtil.context = this.context;
            if (layout == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                this.layout = inflater.inflate(R.layout.dialog_common_dialog_layout, null);
                DialogUtil.isUseDefaultLayout = true;
            }
            DialogUtil.layout = this.layout;
            if (width == 0) {
                width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.8);
            }
            DialogUtil.width = this.width;
            if (gravity == 0) {
                gravity = Gravity.CENTER;
            }
            DialogUtil.gravity = this.gravity;
            if(anim == 0){
                anim = R.style.dialog_default_anim;
            }
            DialogUtil.anim = this.anim;
            DialogUtil.title = this.title;
            DialogUtil.notice = this.notice;
            DialogUtil.confirm = this.confirm;
            DialogUtil.cancel = this.cancel;
            DialogUtil.outsideCancel = this.outsideCancel;
            DialogUtil.onClickListener = this.onDialogClickListener;
            return DialogUtil;
        }
    }

    private Context context;
    private View layout;
    private int width;
    private int gravity;
    private int anim;
    private String title;
    private String notice;
    private String confirm;
    private String cancel;
    private boolean outsideCancel;
    private OnDialogClickListener onClickListener;
    //是否使用默认的布局
    private boolean isUseDefaultLayout;

    public Dialog show() {
        dialog = new Dialog(context, R.style.loading_dialog);
        dialog.setContentView(layout);
        dialog.setCanceledOnTouchOutside(outsideCancel);
        Window window = dialog.getWindow();
        if (window != null) {
            window.getAttributes().width = width;
            window.setGravity(gravity);
            window.setWindowAnimations(anim);
        }
        if (isUseDefaultLayout) {
            TextView tvTitle = layout.findViewById(R.id.tv_title);
            TextView tvNotice = layout.findViewById(R.id.tv_notice);
            TextView tvCancel = layout.findViewById(R.id.tv_cancel);
            TextView tvConfirm = layout.findViewById(R.id.tv_confirm);
            tvTitle.setText(title);
            tvNotice.setText(notice);
            tvCancel.setText(cancel);
            tvConfirm.setText(confirm);
            tvConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if(onClickListener != null){
                        onClickListener.onConfirm();
                    }
                }
            });
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if(onClickListener != null){
                        onClickListener.onCancel();
                    }
                }
            });
        }
        dialog.show();
        return dialog;
    }
}
