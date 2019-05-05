package com.rjp.expandframework.function;

import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class FunctionManager {

    private static FunctionManager instance = new FunctionManager();

    public static FunctionManager getInstance(){
        return instance;
    }

    private Map<String, FunctionNoParamNoResult> mNoParamNoResultMap;
    private Map<String, FunctionNoParamHasResult> mNoParamHasResultMap;
    private Map<String, FunctionHasParamNoResult> mHasParamNoResultMap;
    private Map<String, FunctionHasParamHasResult> mHasParamHasResultMap;

    private FunctionManager(){
        mNoParamNoResultMap = new HashMap<>();
        mNoParamHasResultMap = new HashMap<>();
        mHasParamNoResultMap = new HashMap<>();
        mHasParamHasResultMap = new HashMap<>();
    }

    public void addFunction(FunctionNoParamNoResult function){
        mNoParamNoResultMap.put(function.functionName, function);
    }

    public void invokeFunction(String functionName){
        if(TextUtils.isEmpty(functionName)){
            return;
        }
        if(mNoParamNoResultMap != null){
            FunctionNoParamNoResult findFunction = mNoParamNoResultMap.get(functionName);
            if(findFunction != null){
                findFunction.function();
            }else{
                Log.d("======>", "方法不存在");
            }
        }
    }

    public void addFunction(FunctionNoParamHasResult function){
        mNoParamHasResultMap.put(function.functionName, function);
    }

    public <T> T invokeFunction(String functionName, Class<T> clazz){
        if(TextUtils.isEmpty(functionName)){
            return null;
        }
        if(mNoParamHasResultMap != null){
            FunctionNoParamHasResult findFunction = mNoParamHasResultMap.get(functionName);
            if(findFunction != null){
                if(clazz != null){
                    return clazz.cast(findFunction.function());
                }
            }else{
                Log.d("======>", "方法不存在");
            }
        }
        return null;
    }

    public void addFunction(FunctionHasParamNoResult function){
        mHasParamNoResultMap.put(function.functionName, function);
    }

    public <P> void invokeFunction(String functionName, P p){
        if(TextUtils.isEmpty(functionName)){
            return;
        }
        if(mHasParamNoResultMap != null){
            FunctionHasParamNoResult findFunction = mHasParamNoResultMap.get(functionName);
            if(findFunction != null){
                findFunction.function(p);
            }else{
                Log.d("======>", "方法不存在");
            }
        }
    }

    public void addFunction(FunctionHasParamHasResult function){
        mHasParamHasResultMap.put(function.functionName, function);
    }

    public <T, P> T invokeFunction(String functionName, Class<T> t, P p){
        if(TextUtils.isEmpty(functionName)){
            return null;
        }
        if(mHasParamHasResultMap != null){
            FunctionHasParamHasResult findFunction = mHasParamHasResultMap.get(functionName);
            if(findFunction != null){
                if(t != null){
                    return t.cast(findFunction.function(p));
                }
            }else{
                Log.d("======>", "方法不存在");
            }
        }
        return null;
    }
}
