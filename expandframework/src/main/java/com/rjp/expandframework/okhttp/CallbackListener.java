package com.rjp.expandframework.okhttp;

import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class CallbackListener<T> {

    public abstract void onSuccess(T model);

    public abstract void onFailure();

    protected Type resultType;

    public CallbackListener() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            this.resultType = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
        } else {
            this.resultType = Object.class;
        }
    }

    public Type getGenericityType() {
        return resultType;
    }
}
