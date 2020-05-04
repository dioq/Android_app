package com.myself.a06class;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    private Button mBtSend;
    private EditText mEdtNum, mEdtConten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtSend = (Button) findViewById(R.id.btnSend);
        mEdtNum = (EditText) findViewById(R.id.edit_numId);
        mEdtConten = (EditText) findViewById(R.id.editContentId);

        mBtSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "hehe", Toast.LENGTH_SHORT).show();

                String num = mEdtNum.getText().toString();
                String content = mEdtConten.getText().toString();

                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(num, "110", content, null, null);
            }
        });
    }

}
