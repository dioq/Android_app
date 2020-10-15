package com.my.service_bindservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

    private static String TAG = "10002";
    private MyBinder mBinder = new MyBinder();

    /*
     *既然通过 startService 启动的 Service 和 Activity 没有建立联系，那么通过 bindService 来启动 Service，就可以和 Activity 建立联系了，
     *相当于 Service 绑定到了这个 Activity 中了。
     *通过 bindService(Intent intent ,ServiceConnection connetion,int flag) 启动 Service 后 Service 的正常的生命
     *周期是：onCreate、onBind、ibinder 会作为参数传递到 connect 中的 onServiceConnected 方法中，绑定后，再次执行 bindService Service 什么都不执行。
     *Activity 在没有 bindService 的情况下，调用 unBindService(ServiceConnection serviceConnection) 是会 crash 的。
     *无论在什么情况下，对于某个 Activity，只能够执行一次 unBindService。因为执行完一次 Service就不再注册 serviceConnection 了，
     *再次 unBinderService 就会出现错误 Service not registered:MainActivity$ServiceConnection$ 只有在单独执
     *行 bindService （执行 bindService 前后没有执行 startService）的情况下，执行 unBindService 才会正常执行 Service 的 onDestroy 方法。
     *宿主 Activity 退出，不会正常执行 Service 的 onDestroy 方法。
     * */

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() executed");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand() executed");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() executed");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind() executed");
        return mBinder;
    }

    class MyBinder extends Binder {

        public void startDownload() {
            Log.d("TAG", "startDownload() executed");
            // 执行具体的下载任务
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // 执行具体的下载任务
                    while (true) {
                        Log.e(TAG, "Target in Service ig go !");
                        try {
                            Thread.sleep(2 * 2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }

    }

}
