package com.rjp.fastframework.mvvm;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

public class MvvmViewModel extends ViewModel {

    private MutableLiveData<User> mDataHolder = new MutableLiveData<>();

    private LiveData<String> mDataMap = Transformations.map(mDataHolder, new Function<User, String>() {
        @Override
        public String apply(User input) {
            return "map化" + input.toString();
        }
    });

    private LiveData<User> mDataSwitchMap = Transformations.switchMap(mDataHolder, new Function<User, LiveData<User>>() {
        @Override
        public LiveData<User> apply(User input) {
            MutableLiveData<User> switchData = new MutableLiveData<>();
            input.setName(input.getName() + "switch化");
            input.setAge(input.getAge());
            switchData.setValue(input);
            return switchData;
        }
    });

    public void mockData(){
        User model = new User();
        model.setName("rjp");
        model.setAge(27);
        mDataHolder.setValue(model);
    }

    public MutableLiveData<User> getmDataHolder() {
        return mDataHolder;
    }

    public LiveData<String> getmDataMap() {
        return mDataMap;
    }

    public LiveData<User> getmDataSwitchMap() {
        return mDataSwitchMap;
    }
}
