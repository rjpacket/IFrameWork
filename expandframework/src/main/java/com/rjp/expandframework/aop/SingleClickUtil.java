package com.rjp.expandframework.aop;

import android.view.View;

public final class SingleClickUtil {

    /**
     * 最近一次点击的时间
     */
    private static long mLastClickTime;
    /**
     * 最后一次执行的方法
     */
    private static String mLastMethodName;

    /**
     * 是否是快速点击
     *
     * @param methodName  方法名
     * @param intervalMillis  时间间期（毫秒）
     * @return  true:是，false:不是
     */
    public static boolean isDoubleClick(String methodName, long intervalMillis) {
        long time = System.currentTimeMillis();
        long timeInterval = Math.abs(time - mLastClickTime);
        if (timeInterval < intervalMillis && methodName.equals(mLastMethodName)) {
            return true;
        } else {
            mLastClickTime = time;
            mLastMethodName = methodName;
            return false;
        }
    }
}