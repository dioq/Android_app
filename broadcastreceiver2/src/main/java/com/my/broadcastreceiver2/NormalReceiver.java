package com.my.broadcastreceiver2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NormalReceiver extends BroadcastReceiver {

    private static final String TAG = "testNormalReceiver";

    public NormalReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String msg = intent.getStringExtra("Msg");
        Log.e(TAG, msg);
    }
}
