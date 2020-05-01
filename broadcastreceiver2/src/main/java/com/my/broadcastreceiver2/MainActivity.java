package com.my.broadcastreceiver2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private final String NORMAL_ACTION = "com.my.broadcastreceiver2.NormalReceiver";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendAction(View view) {
        Intent intent = new Intent(NORMAL_ACTION);
        intent.putExtra("Msg", "this is a test message!");
        sendBroadcast(intent);
    }
}
