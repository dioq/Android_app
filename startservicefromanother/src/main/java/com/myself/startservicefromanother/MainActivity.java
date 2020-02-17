package com.myself.startservicefromanother;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(this, AppService.class));
    }

    public void startFunc(View view) {
        serviceIntent = new Intent();
        serviceIntent.setComponent(new ComponentName("com.myself.anotherservice", "com.myself.anotherservice.AppService"));
        startService(serviceIntent);
    }

    public void stopFunc(View view) {
        stopService(serviceIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, AppService.class));
    }
}
