package com.rjp.fastframework.update;

/**
 * author : Gimpo create on 2019/1/16 15:42
 * email  : jimbo922@163.com
 */
public interface UpdateStateCallback {
    void start();

    void pause();

    void process(int percent);

    void completed();
}
