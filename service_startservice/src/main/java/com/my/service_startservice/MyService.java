package com.my.service_startservice;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

@SuppressLint("Registered")
public class MyService extends Service {

    private static String TAG = "10001";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind - Thread ID = " + Thread.currentThread().getId());
        return null;
    }

    /*
     * 通过 startService() 启动 Service 会执行 onCreate()、onStartCommand()、onDestroy() 方法。
     * 注意：当调用 startService(Intent intent) 后，只执行一次 onCreate() 方法，反复多次调用 startService 后，
     * Service 只会重复执行 onStartCommand 方法。调用 stopService 后 Service 只执行一次 onDestroy 方法。
     *
     * 可以看到通过 startService() 启动 Service ，这个时候的 Service 几乎和 Activity 不能交互（不考虑全局变量的方式），
     * 在 Service 里面也没有 getIntent() 方法。
     * */

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate - Thread ID = " + Thread.currentThread().getId());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand - startId = " + startId + ", Thread ID = " + Thread.currentThread().getId());
//        MyPlayer.getInstance().play_music(MyService.this, "pinao.mp3");
        new Thread(new Runnable() {
            @Override
            public void run() {
                int num2 = 0;
                while (true) {
                    Log.w(TAG, "num2 = " + num2++);
                    try {
                        Thread.sleep(3 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy - Thread ID = " + Thread.currentThread().getId());
    }

}
