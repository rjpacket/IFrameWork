package com.rjp.expandframework.aop;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * author : Gimpo create on 2018/11/26 12:48
 * email  : jimbo922@163.com
 */
@Aspect
public class SingleClickAspect {
    @Pointcut("execution(@com.rjp.expandframework.aop.SingleClick * *(..))")//方法切入点
    public void methodAnnotated() {

    }

    @Around("methodAnnotated()")//在连接点进行方法替换
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String methodName = methodSignature.getName();
        Method method = methodSignature.getMethod();
        SingleClick singleClick = method.getAnnotation(SingleClick.class);
        // 判断是否快速点击
        if (!SingleClickUtil.isDoubleClick(methodName, singleClick.time())) {
            // 不是快速点击，执行原方法
            joinPoint.proceed();
        }else{
            Log.d("=======>", "方法" + methodName + "被快速执行了，舍弃一次执行");
        }
    }

}
