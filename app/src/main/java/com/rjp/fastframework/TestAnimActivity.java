package com.rjp.fastframework;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class TestAnimActivity extends Activity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_anim);

        mContext = this;

        ListView listView = findViewById(R.id.list_view);

        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 30;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if(convertView == null){
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_test_anim_list_view, parent, false);
                }
                ImageView animView = convertView.findViewById(R.id.anim_view);
                animView.setBackgroundResource(R.drawable.ic_launcher);
                return convertView;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View animView = view.findViewById(R.id.anim_view);
                animView.setBackgroundResource(R.drawable.test_anim);
                AnimationDrawable animationDrawable = (AnimationDrawable) animView.getBackground();
                animationDrawable.start();
            }
        });
    }
}
