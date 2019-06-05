package com.rjp.expandframework.log;

import java.io.InputStream;

public interface OnLogCallback {
    void onLogSuccess(String logFilePath);

    void onLogFailure();
}
