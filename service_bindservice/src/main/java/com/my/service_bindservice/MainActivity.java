package com.my.service_bindservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "10002";

    private MyService.MyBinder myBinder;

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.w(TAG,"onServiceConnected() is go");
            myBinder = (MyService.MyBinder) service;
            myBinder.startDownload();
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void bind_func(View view) {
        /*
            第一个参数就是要绑定的 Service 的intent
            第二个参数就是 Service 和 Activity 建立联系使用的
            第三个 标志位，和启动的 Service 的优先级有关，一般就是传入：BIND_AOUT_CREATE 表示在 Activity 和 Service建立关联后自动创建 Service。
        **/
        Intent bindIntent = new Intent(this, MyService.class);
        bindService(bindIntent, connection, BIND_AUTO_CREATE);
    }

    public void unbind_func(View view) {
        unbindService(connection);
    }

}
