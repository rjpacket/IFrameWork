package com.rjp.pluginapk;

import android.app.Activity;
import android.os.Bundle;

import com.rjp.pluginlib.PluginActivity;

public class ExtraActivity extends PluginActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra);
    }
}
