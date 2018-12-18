package com.rjp.expandframework.views.moreScrollView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * author : Gimpo create on 2018/12/18 12:16
 * email  : jimbo922@163.com
 */
public class ScrollableListView extends ListView implements Scrollable {
    private boolean isScrolledToTop;
    private boolean isScrolledToBottom;

    public ScrollableListView(Context context) {
        this(context, null);
    }

    public ScrollableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    @Override
    public boolean isScrollBottom() {
        return isScrolledToBottom;
    }

    @Override
    public boolean isScrollTop() {
        return isScrolledToTop;
    }
}
