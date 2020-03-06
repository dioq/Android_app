package com.myself.jnidemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void testBtn(View view) {
        String result = JniUtils.getJniString();
        Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
    }
}
