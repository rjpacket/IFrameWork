package com.rjp.expandframework.log;

public interface OnLogCallback {
    void onLogSuccess(String logFilePath);

    void onLogFailure();
}
