package com.rjp.expandframework.views;

import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

public class AlphaPopupWindow extends PopupWindow {

    private Activity mActivity;

    public AlphaPopupWindow(Activity mActivity, View view, int width, int height){
        this.mActivity = mActivity;
        setContentView(view);
        setWidth(width);
        setHeight(height);
    }

    @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);
        setAlpha(0.5f);
    }

    private void setAlpha(float alpha) {
        Window window = mActivity.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.alpha = alpha;
        window.setAttributes(attributes);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        setAlpha(1.0f);
    }
}
