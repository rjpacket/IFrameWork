package com.rjp.expandframework.apm;

import android.util.Log;

/**
 * author: jinpeng.ren create at 2019/7/25 13:46
 * email: jinpeng.ren@11bee.com
 */
public class AppUIRenderTimeUtil {

    public static boolean uiRenderStart = false;
    public static long uiRenderStartTime = 0L;

    /**
     * app页面渲染的时候调用
     */
    public static void uiRenderStart(){
        uiRenderStart = true;
        uiRenderStartTime = System.nanoTime();
    }

    /**
     * 页面渲染结束
     */
    public static void uiRenderEnd(){
        if(uiRenderStart){
            uiRenderStart = false;
            long coldStartDiff = System.nanoTime() - uiRenderStartTime;
            Log.d("===>", String.format("页面渲染耗时%sms", coldStartDiff * 1.0 / 1000_000));
        }
    }
}
