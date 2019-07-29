package com.rjp.expandframework.apm.cpu;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {

    public List StrToInt(List<String> str) {
        List cpu_num = new ArrayList();
        for (int i = 0; i < str.size(); i++) {
            int a;
            String b = str.get(i);
            a = Integer.parseInt(b.trim());
            cpu_num.add(a);
        }
        return cpu_num;
    }

    public double listAverage(List<Integer> list) {
        int sum = 0;
        double average;
        for (int i = 0; i < list.size(); i++) {
            sum += list.get(i);
        }
        if (list.size() == 0) {
            average = 0;

        } else {
            average = sum * 1.0 / list.size();

        }
        Log.i("ave", "======" + average);

        return average;

    }
}