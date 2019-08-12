package com.rjp.fastframework.views;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.rjp.fastframework.R;

import java.util.ArrayList;
import java.util.List;

/**
 * author: jinpeng.ren create at 2019/8/8 21:01
 * email: jinpeng.ren@11bee.com
 */
public class AppView extends RelativeLayout {

    private FragmentActivity mActivity;
    private List<Fragment> fragments = new ArrayList<>();
    private NavigationView navigationView;
    private OnNavigationClickListener onNavigationClickListener;

    public AppView(Context context) {
        this(context, null);
    }

    public AppView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AppView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mActivity = (FragmentActivity) context;
        initView();
    }

    private void initView() {
        LayoutInflater.from(mActivity).inflate(R.layout.layout_app_view, this);
        navigationView = findViewById(R.id.app_view_navigation_view);
        navigationView.setOnNavigationClickListener(new OnNavigationClickListener() {
            @Override
            public void onNavigationClick(int position) {
                showFragment(position);
                if(onNavigationClickListener != null){
                    onNavigationClickListener.onNavigationClick(position);
                }
            }
        });
    }

    public void showFragment(int position){
        Fragment fragment = fragments.get(position);
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for (Fragment fg : fragments) {
            if(fg.isAdded()) {
                transaction.hide(fg);
            }
        }
        if(!fragment.isAdded()){
            transaction.add(R.id.app_view_container, fragment);
        }
        transaction.show(fragment);
        transaction.commitAllowingStateLoss();

        navigationView.setSelectedIndex(position);
    }

    public void setNavigations(List<NavigationView.Navigation> navigations){
        navigationView.setNavigations(navigations);
        navigationView.setSelectedIndex(0);
    }

    public void setFragments(List<Fragment> fragments){
        this.fragments.clear();
        this.fragments.addAll(fragments);
    }

    public void setOnNavigationClickListener(OnNavigationClickListener onNavigationClickListener) {
        this.onNavigationClickListener = onNavigationClickListener;
    }
}
