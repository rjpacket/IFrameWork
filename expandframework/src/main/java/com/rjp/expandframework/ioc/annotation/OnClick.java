package com.rjp.expandframework.ioc.annotation;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(listenerSetter = "setOnClickListener", listenerType = View.OnClickListener.class, callbackListener = "onClick")
public @interface OnClick {
    int[] value();
}
