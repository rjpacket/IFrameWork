package com.rjp.expandframework.apm.fps;

import android.os.Looper;
import android.util.Log;
import android.view.Choreographer;

import java.util.concurrent.TimeUnit;

/**
 * 线上监测掉帧情况
 * author: jinpeng.ren create at 2019/7/23 21:01
 * email: jinpeng.ren@11bee.com
 */
public class FrameSkipMonitor implements Choreographer.FrameCallback {
    private static final String TAG = "FPSFrameCallback";
    public static int MAX_SKIP_FRAME = 30;
    public static final float deviceRefreshRateInMs = 16.6f;
    public static long lastFrameTimeNanos = 0;
    public static long currentFrameTimeNanos = 0;

    @Override
    public void doFrame(long frameTimeNanos) {
        if (lastFrameTimeNanos == 0) {
            lastFrameTimeNanos = frameTimeNanos;
            Choreographer.getInstance().postFrameCallback(this);
            LogMonitor.getInstance().startMonitor();
            return;
        }
        LogMonitor.getInstance().removeMonitor();
        currentFrameTimeNanos = frameTimeNanos;
        long value = (currentFrameTimeNanos - lastFrameTimeNanos) / 1000_000;

        int droppedCount = droppedCount(lastFrameTimeNanos, currentFrameTimeNanos, deviceRefreshRateInMs);

        //上报跳帧数据
        if (droppedCount > MAX_SKIP_FRAME) {
            Log.d("===>", String.format("掉了%s帧", droppedCount));
        }
        lastFrameTimeNanos = currentFrameTimeNanos;
        Choreographer.getInstance().postFrameCallback(this);
        LogMonitor.getInstance().startMonitor();
    }

    public static int droppedCount(long start, long end, float deviceRefreshRateInMs) {
        int count = 0;
        long diffNs = end - start;

        long diffMs = TimeUnit.MILLISECONDS.convert(diffNs, TimeUnit.NANOSECONDS);
        long dev = Math.round(deviceRefreshRateInMs);
        if (diffMs > dev) {
            long droppedCount = (long) (diffMs * 1.0 / dev);
            count = (int) droppedCount;
        }

        return count;
    }
}
