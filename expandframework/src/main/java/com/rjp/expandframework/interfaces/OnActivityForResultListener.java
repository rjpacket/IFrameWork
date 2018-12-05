package com.rjp.expandframework.interfaces;

import android.content.Intent;

public interface OnActivityForResultListener {
    void onActivityResult(int requestCode, int resultCode, Intent data);
}