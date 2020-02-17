package com.myself.connectservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements ServiceConnection {

    private EditText etData;
    private TextView textView;
    private MyService.Binder binder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etData = (EditText) findViewById(R.id.etData);
        textView = (TextView) findViewById(R.id.tvId);
    }

    //启动服务按钮的点击方法
    public void startFunc(View view) {
        Intent intent = new Intent(this, MyService.class);
        intent.putExtra("data", etData.getText().toString());
        startService(intent);
    }

    //停止服务按钮的点击方法
    public void stopFunc(View view) {
        stopService(new Intent(this, MyService.class));
    }

    //绑定服务的按钮方法
    public void bindFunc(View view) {
        bindService(new Intent(this, MyService.class), this, Context.BIND_AUTO_CREATE);
    }

    //解除绑定服务的按钮方法
    public void unBindFunc(View view) {
        unbindService(this);
    }

    //同步数据按钮方法
    public void sycFunc(View view) {
        if (binder != null) {
            //通过binder进行通信
            binder.setData(etData.getText().toString());
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        binder = (MyService.Binder) service;
        binder.getService().setCallback(new MyService.Callback() {
            @Override
            public void onDataChange(String data) {
                Message msg = new Message();
                Bundle b = new Bundle();
                b.putString("data", data);
                msg.setData(b);
                handler.sendMessage(msg);//在主线程更新UI
            }
        });
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            String str = msg.getData().getString("data");
            textView.setText(str);
        }
    };
}
