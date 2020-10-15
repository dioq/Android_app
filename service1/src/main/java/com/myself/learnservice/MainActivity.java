package com.myself.learnservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import static java.lang.Thread.sleep;

/*
至于startservice和bindservice的使用场景，有网友这么说：
1.通过startservice开启的服务.一旦服务开启, 这个服务和开启他的调用者之间就没有任何的关系了. 调用者不可以访问 service里面的方法.
调用者如果被系统回收了或者调用了ondestroy方法, service还会继续存在。
2.通过bindService开启的服务,服务开启之后,调用者和服务之间 还存在着联系 , 一旦调用者挂掉了.service也会跟着挂掉。
注意：bindServices一定要调用unbindServices方法，否则会抛出一个serviceConnection泄露异常
* */
public class MainActivity extends AppCompatActivity implements ServiceConnection {

    static String TAG = "10001";
    private Intent intent;
    int num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(MainActivity.this, MyService.class);
    }

    //启动服务按钮的点击方法
    public void startFunc(View view) {
        Log.e(TAG, "MainActivity ----> startFunc");
        //startService启动后 Service中的onStartCommand就会调用
        startService(intent);
    }

    //停止服务按钮的点击方法
    public void stopFunc(View view) {
        Log.e(TAG, "MainActivity ---> stopFunc");
        stopService(intent);
    }

    //绑定服务的按钮方法
    public void bindFunc(View view) {
        Log.e(TAG, "MainActivity ---> bindFunc");
        bindService(intent, this, Context.BIND_AUTO_CREATE);
    }

    //解除绑定服务的按钮方法
    public void unBindFunc(View view) {
        Log.e(TAG, "MainActivity ---> unBindFunc\" + \"服务解除绑定执行");
        unbindService(this);
    }

    //服务被绑定成功时执行
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        Log.e(TAG, "服务绑定成功");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Log.e(TAG, "onServiceConnected() ----> " + num + "号==任务==正在运行... ");
                    num++;
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

    //服务被杀掉或服务崩溃时执行
    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.e(TAG, "服务被杀掉或服务崩溃");
    }
}
