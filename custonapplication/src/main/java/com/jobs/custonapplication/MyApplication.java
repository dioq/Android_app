package com.jobs.custonapplication;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

/**
 * 编写自己的Application，管理全局状态信息，比如Context
 * 要在 AndroidManifest.xml 引入android:name=".MyApplication"
 */
public class MyApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        System.out.println("go here");
    }

}
