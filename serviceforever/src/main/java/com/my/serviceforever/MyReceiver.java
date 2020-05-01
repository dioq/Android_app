package com.my.serviceforever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("接收到了消息, 消息的内容是:" + intent.getStringExtra("data"));
        Intent mIntent = new Intent(context, MyService.class);
        context.startService(mIntent);
    }

}
