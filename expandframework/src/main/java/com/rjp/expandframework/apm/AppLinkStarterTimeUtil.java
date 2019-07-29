package com.rjp.expandframework.apm;

import android.util.Log;

/**
 * author: jinpeng.ren create at 2019/7/25 13:46
 * email: jinpeng.ren@11bee.com
 */
public class AppLinkStarterTimeUtil {

    public static boolean coldStart = false;
    public static long coldStartTime = 0L;

    public static boolean hotStart = false;
    public static long hotStartTime = 0L;

    /**
     * app冷启动的时候调用
     */
    public static void coldStart(){
        coldStart = true;
        coldStartTime = System.nanoTime();
    }

    /**
     * 冷启动结束
     */
    public static void coldEnd(){
        if(coldStart){
            coldStart = false;
            long coldStartDiff = System.nanoTime() - coldStartTime;
            Log.d("===>", String.format("冷启动耗时%sms", coldStartDiff * 1.0 / 1000_000));
        }
    }

    /**
     * app热启动的时候调用
     */
    public static void hotStart(){
        hotStart = true;
        hotStartTime = System.nanoTime();
    }

    /**
     * 热启动结束
     */
    public static void hotEnd(){
        if(hotStart){
            hotStart = false;
            long coldStartDiff = System.nanoTime() - hotStartTime;
            Log.d("===>", String.format("热启动耗时%sms", coldStartDiff * 1.0 / 1000_000));
        }
    }
}
