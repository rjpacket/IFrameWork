package com.rjp.expandframework.views.moreScrollView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * author : Gimpo create on 2018/12/18 12:01
 * email  : jimbo922@163.com
 */
public class ScrollableScrollView extends ScrollView implements Scrollable {
    private boolean isScrolledToTop;
    private boolean isScrolledToBottom;

    public ScrollableScrollView(Context context) {
        this(context, null);
    }

    public ScrollableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if (scrollY == 0) {
            isScrolledToTop = clampedY;
            isScrolledToBottom = false;
        } else {
            isScrolledToTop = false;
            isScrolledToBottom = clampedY;
        }
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
