package com.rjp.expandframework.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import com.rjp.expandframework.R;

import java.util.List;

public class LuckyNoticeView extends ViewFlipper {

    private Context mContext;

    //设置数据的bean
    private List<String> mNotices;

    public LuckyNoticeView(Context context) {
        super(context);
    }

    public LuckyNoticeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        // 轮播间隔时间为3s
        setFlipInterval(3000);
        // 内边距5dp
        setPadding(dp2px(5f), dp2px(5f), dp2px(5f), dp2px(5f));
        // 设置enter和leave动画
//        setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.bottom_in));
//        setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.notice_out));
    }

    /**
     * 添加需要轮播展示的中奖公告
     *
     * @param notices
     */
    public void addNotice(List<String> notices) {
        mNotices = notices;
        removeAllViews();
        for (int i = 0; i < mNotices.size(); i++) {
            //根布局  （这里用的item就是你上下轮播的列表样式，我这里面只有一个textview）
            LinearLayout item = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.layout_quick_tab_layout, null);
            item.setTag(i);
            LuckyNoticeView.this.addView(item);
        }
    }

    private int dp2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpValue, mContext.getResources().getDisplayMetrics());
    }

}