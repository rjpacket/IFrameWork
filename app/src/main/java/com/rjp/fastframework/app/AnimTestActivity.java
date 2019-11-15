package com.rjp.fastframework.app;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.rjp.expandframework.interfaces.OnDialogClickListener;
import com.rjp.expandframework.interfaces.PermissionCallback;
import com.rjp.expandframework.utils.DialogUtil;
import com.rjp.expandframework.utils.PermissionUtil;
import com.rjp.fastframework.BuildConfig;
import com.rjp.fastframework.R;

import java.util.List;

public class AnimTestActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_test);

        context = this;
    }

    public int height(View view){
        if(view instanceof ViewGroup){
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            int max = 0;
            for (int i = 0; i < childCount; i++) {
                max = Math.max(max, height(viewGroup.getChildAt(i)));
            }
            return 1 + max;
        }
        return 1;
    }
}
