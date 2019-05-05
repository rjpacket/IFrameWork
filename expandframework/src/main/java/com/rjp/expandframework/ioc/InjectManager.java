package com.rjp.expandframework.ioc;

import android.app.Activity;
import android.view.View;

import com.rjp.expandframework.R;
import com.rjp.expandframework.ioc.annotation.ContentView;
import com.rjp.expandframework.ioc.annotation.EventBase;
import com.rjp.expandframework.ioc.annotation.InjectView;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class InjectManager {

    public static void inject(Activity activity) {
        injectLayout(activity);
        injectViews(activity);
        injectEvents(activity);
    }

    private static void injectLayout(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();

        ContentView contentView = clazz.getAnnotation(ContentView.class);

        if (contentView != null) {
            int layoutId = contentView.value();

            try {
                Method method = clazz.getMethod("setContentView", int.class);

                method.invoke(activity, layoutId);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private static void injectViews(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            InjectView injectView = field.getAnnotation(InjectView.class);

            if (injectView != null) {
                int viewId = injectView.value();

                try {
                    Method method = clazz.getMethod("findViewById", int.class);

                    Object view = method.invoke(activity, viewId);

                    field.setAccessible(true);
                    field.set(activity, view);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void injectEvents(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();

        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();

            for (Annotation annotation : annotations) {
                Class<? extends Annotation> annotationType = annotation.annotationType();

                if (annotationType != null) {
                    EventBase eventBase = annotationType.getAnnotation(EventBase.class);

                    if (eventBase != null) {
                        String listenerSetter = eventBase.listenerSetter();
                        Class<?> listenerType = eventBase.listenerType();
                        String callbackListener = eventBase.callbackListener();

                        try {
                            Method valueMethod = annotationType.getDeclaredMethod("value");
                            int[] viewIds = (int[]) valueMethod.invoke(annotation);

                            ListenerInvocationHandler handler = new ListenerInvocationHandler(activity);
                            handler.addMethod(callbackListener, method);

                            Object proxyInstance = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class[]{listenerType}, handler);

                            for (int viewId : viewIds) {
                                View viewById = activity.findViewById(viewId);

                                Method targetMethod = viewById.getClass().getMethod(listenerSetter, listenerType);

                                targetMethod.invoke(viewById, proxyInstance);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
