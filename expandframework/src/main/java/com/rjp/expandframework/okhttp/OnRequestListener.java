package com.rjp.expandframework.okhttp;

import java.io.InputStream;

public interface OnRequestListener {

    void onRequestSuccess(InputStream inputStream);

    void onRequestFailure();
}
