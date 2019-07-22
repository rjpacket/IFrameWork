package com.rjp.expandframework.views.titlebar;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.*;

import android.widget.*;
import com.rjp.expandframework.R;

/**
 * author: jinpeng.ren create at 2019/7/15 10:49
 * email: jinpeng.ren@11bee.com
 */
public class TitleBarView extends FrameLayout {

    private Context mContext;
    //属性
    private int leftIcon;
    private String title;
    private String rightText;
    private boolean addExtraHead;
    private int background;
    private float height;
    private int titleColor;
    private float titleSize;
    private int rightColor;
    private float rightSize;
    //控件
    private LinearLayout llRightContainer;
    private TextView tvRightText;
    private TextView tvTitle;

    public TitleBarView(Context context) {
        this(context, null);
    }

    public TitleBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        processAttrs(attrs);
        initView();
    }

    /**
     * 初始化标题栏布局
     */
    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.view_title_bar, this);
        if (addExtraHead) {
            transparentStatusBar();
            setPadding(0, getStatusBarHeight(), 0, 0);
        }
        RelativeLayout rlTitleBar = findViewById(R.id.rl_title_bar);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) rlTitleBar.getLayoutParams();
        params.height = (int) height;
        setLayoutParams(params);

        if (background != 0) {
            rlTitleBar.setBackgroundResource(background);
        }

        if (leftIcon != 0) {
            ImageView ivLeftIcon = findViewById(R.id.iv_left_icon);
            ivLeftIcon.setImageResource(leftIcon);
        }

        if (!TextUtils.isEmpty(title)) {
            tvTitle = findViewById(R.id.tv_title);
            tvTitle.setText(title);
            if (titleColor != 0) {
                tvTitle.setTextColor(titleColor);
            }
            tvTitle.setTextSize(titleSize);
        }

        if (!TextUtils.isEmpty(rightText)) {
            tvRightText = findViewById(R.id.tv_right_text);
            tvRightText.setText(rightText);
            if (rightColor != 0) {
                tvRightText.setTextColor(rightColor);
            }
            tvRightText.setTextSize(rightSize);
        }

        llRightContainer = findViewById(R.id.ll_right_container);
    }

    public int getStatusBarHeight() {
        Resources resources = Resources.getSystem();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    /**
     * 透明状态栏
     */
    private void transparentStatusBar() {
        if (mContext instanceof Activity) {
            Window window = ((Activity) mContext).getWindow();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
    }

    /**
     * 处理属性集合
     *
     * @param attrs
     */
    private void processAttrs(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.TitleBarView);
        if (typedArray != null) {
            addExtraHead = typedArray.getBoolean(R.styleable.TitleBarView_tbv_extra_head, false);
            height = typedArray.getDimension(R.styleable.TitleBarView_tbv_height, dp2px(48));
            background = typedArray.getResourceId(R.styleable.TitleBarView_tbv_background, 0);
            leftIcon = typedArray.getResourceId(R.styleable.TitleBarView_tbv_left_icon, 0);
            title = typedArray.getString(R.styleable.TitleBarView_tbv_title);
            titleColor = typedArray.getResourceId(R.styleable.TitleBarView_tbv_title_color, 0);
            titleSize = typedArray.getDimension(R.styleable.TitleBarView_tbv_title_size, dp2px(16));
            rightText = typedArray.getString(R.styleable.TitleBarView_tbv_right_text);
            rightColor = typedArray.getResourceId(R.styleable.TitleBarView_tbv_right_color, 0);
            rightSize = typedArray.getDimension(R.styleable.TitleBarView_tbv_right_size, dp2px(16));
            //回收防泄漏
            typedArray.recycle();
        }
    }

    public int dp2px(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public void addRightIcon(int resourceId) {
        ImageView rightIcon = new ImageView(mContext);
        rightIcon.setTag(resourceId);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dp2px(24), dp2px(24));
        rightIcon.setImageResource(resourceId);
        params.setMargins(dp2px(10), 0, 0, 0);
        rightIcon.setLayoutParams(params);
        //添加进容器
        addRightIconView(rightIcon);
    }

    public void addRightIconView(View rightIconView) {
        llRightContainer.addView(rightIconView);
    }

    /**
     * 右边icon点击事件
     *
     * @param onRightIconClickListener
     */
    public void setOnRightIconClickListener(OnClickListener onRightIconClickListener) {
        int childCount = llRightContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = llRightContainer.getChildAt(i);
            childAt.setOnClickListener(onRightIconClickListener);
        }
    }

    /**
     * 右边文字点击事件
     *
     * @param onRightTextClickListener
     */
    public void setOnRightTextClickListener(OnClickListener onRightTextClickListener) {
        tvRightText.setOnClickListener(onRightTextClickListener);
    }

    /**
     * 获取标题view，方便扩展
     * @return
     */
    public View getTitleView(){
        return tvTitle;
    }
}
