package com.rjp.expandframework.IHttp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class ModelCallback<T> implements ICallback{

    private Handler mHandler = new Handler(Looper.getMainLooper());

    protected Type resultType;
    protected Context context;

    public ModelCallback(Context context) {
        this.context = context;
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            this.resultType = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
        } else {
            this.resultType = Object.class;
        }
    }

    @Override
    public void onSuccess(String result) {
        Gson gson = new Gson();
        BaseModel baseModel = gson.fromJson(result, BaseModel.class);
        if(baseModel.getStatus() == 0) {
            final T model = gson.fromJson(baseModel.getData().toString(), resultType);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onSuccess(model);
                }
            });
        }else{
            onFailure(baseModel.getStatus(), result);
        }
    }

    public abstract void onSuccess(T model);

    @Override
    public void onFailure(int code, String errorMsg) {
        Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
    }
}
