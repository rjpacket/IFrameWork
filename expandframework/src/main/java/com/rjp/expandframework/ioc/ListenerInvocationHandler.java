package com.rjp.expandframework.ioc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

public class ListenerInvocationHandler implements InvocationHandler {

    private Object target;

    public HashMap<String, Method> methodHashMap = new HashMap<>();

    public ListenerInvocationHandler(Object target){
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(target != null){
            String methodName = method.getName();

            method = methodHashMap.get(methodName);

            if(method != null){
                if(method.getGenericParameterTypes().length == 0){
                    return method.invoke(target);
                }
                return method.invoke(target, args);
            }
        }
        return null;
    }

    public void addMethod(String methodName, Method method){
        methodHashMap.put(methodName, method);
    }
}
