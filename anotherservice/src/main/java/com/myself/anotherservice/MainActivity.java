package com.myself.anotherservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startFunc(View view) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.myself.anotherservice",""));
    }

    public void stopFunc(View view) {

    }
}
