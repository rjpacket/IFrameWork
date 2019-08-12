package com.rjp.fastframework.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.rjp.expandframework.utils.AppUtil;
import com.rjp.fastframework.R;

import java.util.ArrayList;
import java.util.List;

/**
 * author: jinpeng.ren create at 2019/8/8 12:23
 * email: jinpeng.ren@11bee.com
 */
public class NavigationView extends View {

    private Context mContext;
    private int width;
    private int height;
    private Paint mPaint;
    private List<Navigation> navigations = new ArrayList<>();
    private int selectedIndex;
    private int cellWidth;
    private int verticalSpace = 12;
    private RectF iconRect;
    private int iconWidth = AppUtil.dp2px(20);
    private int iconMarginLeft;
    private float textHeight;
    private int iconMarginTop;
    private int povitY;
    private int distance;
    private int textSize;
    private int selectedTextSize;
    private int textColor;
    private int selectedTextColor;
    private OnNavigationClickListener onNavigationClickListener;

    public NavigationView(Context context) {
        this(context, null);
    }

    public NavigationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        processAttrs(attrs);
        initView();
    }

    private void initView() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(AppUtil.dp2px(10));
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        textHeight = fontMetrics.descent - fontMetrics.ascent;
        distance = (int) ((fontMetrics.descent + fontMetrics.ascent) / 2);

        iconRect = new RectF();
    }

    private void processAttrs(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.NavigationView);
        if (typedArray != null) {
            iconWidth = (int) typedArray.getDimension(R.styleable.NavigationView_ngv_icon_width, AppUtil.dp2px(20));
            verticalSpace = (int) typedArray.getDimension(R.styleable.NavigationView_ngv_vertical_space, AppUtil.dp2px(4));
            textSize = (int) typedArray.getDimension(R.styleable.NavigationView_ngv_text_size, AppUtil.dp2px(10));
            selectedTextSize = (int) typedArray.getDimension(R.styleable.NavigationView_ngv_selected_text_size, AppUtil.dp2px(10));
            textColor = typedArray.getColor(R.styleable.NavigationView_ngv_text_color, Color.BLACK);
            selectedTextColor = typedArray.getColor(R.styleable.NavigationView_ngv_selected_text_color, Color.BLACK);

            typedArray.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
        if (navigations.size() > 0) {
            cellWidth = width / navigations.size();
            iconMarginLeft = (cellWidth - iconWidth) / 2;
            iconMarginTop = (int) ((height - iconWidth - verticalSpace - textHeight) / 2);

            povitY = (int) (iconMarginTop + iconWidth + verticalSpace + textHeight / 2 - distance);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int size = navigations.size();
        for (int i = 0; i < size; i++) {
            boolean checked = selectedIndex == i;
            Navigation navigation = navigations.get(i);
            iconRect.left = cellWidth * i + iconMarginLeft;
            iconRect.top = iconMarginTop;
            iconRect.right = iconRect.left + iconWidth;
            iconRect.bottom = iconRect.top + iconWidth;
            canvas.drawBitmap(checked ? navigation.getSelectedIcon() : navigation.getIcon(), null, iconRect, mPaint);
            if (checked) {
                mPaint.setColor(selectedTextColor);
                mPaint.setTextSize(selectedTextSize);
            } else {
                mPaint.setColor(textColor);
                mPaint.setTextSize(textSize);
            }
            canvas.drawText(navigation.getTitle(), cellWidth * i + cellWidth / 2, povitY, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                float x = event.getX();
                float y = event.getY();
                int position = getPositionByXY(x, y);
                if (position != -1) {
                    if (onNavigationClickListener != null) {
                        onNavigationClickListener.onNavigationClick(position);
                    }
                }
                break;
        }
        return true;
    }

    private int getPositionByXY(float x, float y) {
        if (x >= 0 && x <= width && y >= 0 && y <= height) {
            return (int) (x / cellWidth);
        }
        return -1;
    }

    public void setNavigations(List<Navigation> navigations) {
        this.navigations.clear();
        this.navigations.addAll(navigations);
        invalidate();
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
        invalidate();
    }

    public void setOnNavigationClickListener(OnNavigationClickListener onNavigationClickListener) {
        this.onNavigationClickListener = onNavigationClickListener;
    }

    public static class Navigation {
        private String title;
        private Bitmap icon;
        private Bitmap selectedIcon;

        public Bitmap getSelectedIcon() {
            return selectedIcon;
        }

        public void setSelectedIcon(Bitmap selectedIcon) {
            this.selectedIcon = selectedIcon;
        }

        public Navigation(Context context, String title, int icon, int selectedIcon) {
            this.title = title;
            this.icon = BitmapFactory.decodeResource(context.getResources(), icon);
            this.selectedIcon = BitmapFactory.decodeResource(context.getResources(), selectedIcon);
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Bitmap getIcon() {
            return icon;
        }

        public void setIcon(Bitmap icon) {
            this.icon = icon;
        }
    }
}
