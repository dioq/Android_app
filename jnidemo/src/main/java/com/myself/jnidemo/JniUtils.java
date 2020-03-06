package com.myself.jnidemo;

public class JniUtils {
    static {
        System.loadLibrary("JNISample");
    }

    public static native String getJniString();
}
