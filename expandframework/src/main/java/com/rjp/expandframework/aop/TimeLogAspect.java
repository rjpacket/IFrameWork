package com.rjp.expandframework.aop;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.concurrent.TimeUnit;

/**
 * author : Gimpo create on 2018/11/26 12:48
 * email  : jimbo922@163.com
 */
@Aspect
public class TimeLogAspect {
    @Pointcut("execution(@com.rjp.expandframework.aop.TimeLog * *(..))")//方法切入点
    public void methodAnnotated() {

    }

    @Pointcut("execution(@com.rjp.expandframework.aop.TimeLog *.new(..))")
    public void constructorAnnotated(){

    }

    @Around("methodAnnotated() || constructorAnnotated()")//在连接点进行方法替换
    public Object aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
        long startTime = System.nanoTime();
        Object result = joinPoint.proceed();//执行原方法
        long endTime = System.nanoTime();

        TimeLog timeLog = methodSignature.getMethod().getAnnotation(TimeLog.class);
        Log.d(timeLog.tag(), className + "的" + methodName + "耗时 -> [" + (TimeUnit.NANOSECONDS.toMillis(endTime - startTime)) + "ms]");
        return result;
    }

}
