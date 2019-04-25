package com.rjp.fastframework;

public class Hello {
    static {
        System.loadLibrary("hello");
    }

    public static native String sayHi();
}
