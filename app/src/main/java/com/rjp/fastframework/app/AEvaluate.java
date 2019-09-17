package com.rjp.fastframework.app;

import android.animation.TypeEvaluator;
import android.graphics.Point;

/**
 * author: jinpeng.ren create at 2019/9/6 20:55
 * email: jinpeng.ren@11bee.com
 */
public class AEvaluate implements TypeEvaluator<Point> {
    @Override
    public Point evaluate(float fraction, Point startValue, Point endValue) {
        Point mid = new Point();
        mid.x = (int) (startValue.x + fraction * fraction * (endValue.x - startValue.x));
        mid.y = (int) (startValue.y + fraction * (endValue.y - startValue.y));
        return mid;
    }
}
