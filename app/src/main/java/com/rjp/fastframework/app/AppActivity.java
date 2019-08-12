package com.rjp.fastframework.app;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.rjp.fastframework.R;
import com.rjp.fastframework.views.AppView;
import com.rjp.fastframework.views.NavigationView;
import com.rjp.fastframework.views.OnNavigationClickListener;

import java.util.ArrayList;

public class AppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        AppView appView = findViewById(R.id.app_view);
        ArrayList<NavigationView.Navigation> navigations = new ArrayList<>();
        navigations.add(new NavigationView.Navigation(this, "首页", R.drawable.icon_share, R.drawable.icon_back));
        navigations.add(new NavigationView.Navigation(this, "学习", R.drawable.icon_share, R.drawable.icon_back));
        navigations.add(new NavigationView.Navigation(this, "咨询", R.drawable.icon_share, R.drawable.icon_back));
        navigations.add(new NavigationView.Navigation(this, "风险百科", R.drawable.icon_share, R.drawable.icon_back));
        navigations.add(new NavigationView.Navigation(this, "我的", R.drawable.icon_share, R.drawable.icon_back));
        appView.setNavigations(navigations);

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(AFragment.newInstance(0));
        fragments.add(AFragment.newInstance(1));
        fragments.add(AFragment.newInstance(2));
        fragments.add(AFragment.newInstance(3));
        fragments.add(AFragment.newInstance(4));
        appView.setFragments(fragments);

        appView.setOnNavigationClickListener(new OnNavigationClickListener() {
            @Override
            public void onNavigationClick(int position) {
                Toast.makeText(AppActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
