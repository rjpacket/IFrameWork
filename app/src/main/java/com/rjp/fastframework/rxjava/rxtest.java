package com.rjp.fastframework.rxjava;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class rxtest {
    public static void main(String[] args) {
//        Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> e) throws Exception {
//                e.onNext("1");
//                e.onNext("2");
//                e.onNext("3");
//            }
//        }).subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                System.out.println(s);
//            }
//        });
//
//        Integer[] array = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12};
//        Observable.fromArray(array)
//                .subscribe(new Observer<Integer>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Integer value) {
//                        System.out.println(value);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//
//        List<String> list = new ArrayList<>();
//        list.add("q");
//        list.add("w");
//        list.add("e");
//        list.add("r");
//        Observable.fromIterable(list)
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String s) throws Exception {
//                        System.out.println(s);
//                    }
//                });


    }
}
