package com.rjp.expandframework.liveDataBus;

import android.arch.lifecycle.GenericLifecycleObserver;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 事件总线
 */
public class LiveDataBus {
    private final Map<String, MutableLiveData<Object>> bus;

    private LiveDataBus(){
        bus = new HashMap<>();
    }

    private static class SingletonHolder{
        public static final LiveDataBus instance = new LiveDataBus();
    }

    public static LiveDataBus get(){
        return SingletonHolder.instance;
    }

    public<T> MutableLiveData<T> with(String tag, Class<T> type){
        if(!bus.containsKey(tag)){
            bus.put(tag, new BusMutableLiveData<>());
        }
        return (MutableLiveData<T>) bus.get(tag);
    }

    public MutableLiveData<Object> with(String tag) {
        return with(tag, Object.class);
    }

    public class BusMutableLiveData<T> extends MutableLiveData<T>{

        private Map<Observer, Observer> foreverObserversMap = new HashMap<>();

        @Override
        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<T> observer) {
            super.observe(owner, observer);

            try {
                hookChangeObserverVersion(observer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void observeForever(@NonNull Observer<T> observer) {
            if(!foreverObserversMap.containsKey(observer)){
                foreverObserversMap.put(observer, new ForeverObserverWrapper<>(observer));
            }
            super.observeForever(foreverObserversMap.get(observer));
        }

        @Override
        public void removeObserver(@NonNull Observer<T> observer) {
            if(foreverObserversMap.containsKey(observer)){
                Observer realObserver = foreverObserversMap.remove(observer);
                super.removeObserver(realObserver);
            }else {
                super.removeObserver(observer);
            }
        }

        /**
         * 修改新建的Observer版本和LiveData保持一致
         * @param observer
         */
        private void hookChangeObserverVersion(Observer<T> observer) throws Exception {
            Class<LiveData> classLiveData = LiveData.class;
            Field mObservers = classLiveData.getDeclaredField("mObservers");
            mObservers.setAccessible(true);
            //获取当前BusMutableLiveData的mObservers值
            Object objectObservers = mObservers.get(this);
            //获取mObservers的类型，应该是SafeIterableMap类型
            Class<?> objectObserversClass = objectObservers.getClass();
            //拿到SafeIterableMap的get方法
            Method methodGet = objectObserversClass.getDeclaredMethod("get", Object.class);
            methodGet.setAccessible(true);
            Object observerWrapperEntry = methodGet.invoke(objectObservers, observer);
            Object observerWrapper = null;
            if(observerWrapperEntry instanceof Map.Entry){
                observerWrapper = ((Map.Entry)observerWrapperEntry).getValue();
            }
            if(observerWrapper == null){
                throw new NullPointerException("Wrapper can not be bull!");
            }

            Class<?> classObserver = observerWrapper.getClass().getSuperclass();
            Field mLastVersion = classObserver.getDeclaredField("mLastVersion");
            mLastVersion.setAccessible(true);
            //获取当前LiveData的版本
            Field mVersion = classLiveData.getDeclaredField("mVersion");
            mVersion.setAccessible(true);
            Object objectVersion = mVersion.get(this);
            mLastVersion.set(observerWrapper, objectVersion);
        }
    }

    public class ForeverObserverWrapper<T> implements Observer<T> {
        public Observer<T> mObserverWrapper;

        public ForeverObserverWrapper(Observer<T> mObserver){
            mObserverWrapper = mObserver;
        }

        @Override
        public void onChanged(@Nullable T t) {
            if(mObserverWrapper != null){
                if(isCallOnObserverForever()){
                    return;
                }
                mObserverWrapper.onChanged(t);
            }
        }

        private boolean isCallOnObserverForever() {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            if(stackTrace != null && stackTrace.length > 0){
                for (StackTraceElement stackTraceElement : stackTrace) {
                    if("android.arch.lifecycle.LiveData".equals(stackTraceElement.getClassName())
                            && "observeForever".equals(stackTraceElement.getMethodName())){
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
