package com.rjp.pluginlib;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public interface IPlugin {

    int FROM_SYSTEM = 0;
    int FROM_APP = 1;

    void attach(Activity proxyActivity);

    void onCreate(Bundle saveInstanceState);

    void onRestart();

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void onActivityResult(int requestCode, int resultCode, Intent data);
}
