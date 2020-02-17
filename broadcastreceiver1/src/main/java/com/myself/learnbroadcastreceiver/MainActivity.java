package com.myself.learnbroadcastreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendFunc(View view) {
        Intent intent = new Intent(this, MyReceiver.class);
        intent.putExtra("data", "a message");
        sendBroadcast(intent);

        Intent intent1 = new Intent(this, MyReceiver1.class);
        intent1.putExtra("data", "a message");
        sendBroadcast(intent1);
    }

}
