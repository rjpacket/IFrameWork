package com.rjp.expandframework.log;

public interface ILogRequest {

    void setData(String data);

    void setListener(OnLogCallback onLogCallback);

    void execute() throws Exception;
}
