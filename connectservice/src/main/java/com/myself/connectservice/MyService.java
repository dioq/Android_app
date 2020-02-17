package com.myself.connectservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import static java.lang.Thread.sleep;

public class MyService extends Service {

    private boolean serviceRunnig = false;
    private String msg = "这是默认信息";

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
    }

    public class Binder extends android.os.Binder {
        public void setData(String data) {
            MyService.this.msg = data;
        }

        public MyService getService() {
            return MyService.this;
        }
    }

    //startService每次执行都会响应
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        msg = intent.getStringExtra("data");//获取从startService传来的信息
        return super.onStartCommand(intent, flags, startId);
    }

    //创建服务（一个服务只会创建一次）
    @Override
    public void onCreate() {
        super.onCreate();
        serviceRunnig = true;
        System.out.println("Service create");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (serviceRunnig) {
                    String str = i + ":" + msg;
                    i++;
                    System.out.println(str);
                    if (callback != null) {
                        callback.onDataChange(str);
                    }
                    try {
                        sleep(1000);
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
        System.out.println("Service destory");
        serviceRunnig = false;
    }

    private Callback callback = null;

    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public static interface Callback {
        void onDataChange(String data);
    }
}
