package com.myself.learnservice;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

import static java.lang.Thread.sleep;

/*
 * Service 是一个后台线程
 * */
public class MyService extends Service {

    static String TAG = "10001";
    static int num = 0;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "MyService -- onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    //创建服务（一个服务只会创建一次）
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "service 创建成功!");
//        MyPlayer.getInstance().play_music(this, "pinao.mp3");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Log.e(TAG, "myservice target is go " + num++);
                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    //销毁服务
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "Service destory");
    }
}
