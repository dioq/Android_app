package com.myself.learnservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

public class MainActivity extends AppCompatActivity implements ServiceConnection {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(MainActivity.this, MyService.class);
    }

    //启动服务按钮的点击方法
    public void startFunc(View view) {
        System.out.println("MainActivity -- onClick");
        //startService启动后 Service中的onStartCommand就会调用
        startService(intent);
    }

    //停止服务按钮的点击方法
    public void stopFunc(View view) {
        stopService(intent);
    }

    //绑定服务的按钮方法
    public void bindFunc(View view) {
        bindService(intent, this, Context.BIND_AUTO_CREATE);
    }

    //解除绑定服务的按钮方法
    public void unBindFunc(View view) {
        unbindService(this);
    }

    //服务被绑定成功时执行
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        System.out.println("服务绑定成功");
    }

    //服务被杀掉或服务崩溃时执行
    @Override
    public void onServiceDisconnected(ComponentName name) {
        System.out.println("服务被杀掉或服务崩溃");
    }
}
