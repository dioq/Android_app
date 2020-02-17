package com.example.handler1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button)findViewById(R.id.buttonId);
        button.setOnClickListener(new ButtonListener());

        handler = new FirstHandler();
    }

    class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Message msg = handler.obtainMessage();
            msg.what = 2;
            handler.sendMessage(msg);
        }
    }

    class FirstHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            System.out.println("what: " + msg.what);
        }
    }

}
