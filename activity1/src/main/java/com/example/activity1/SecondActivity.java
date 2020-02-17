package com.example.activity1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends Activity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);

        Intent intent = getIntent();
        int age = intent.getIntExtra("com.example.activity1.Age", 0);
        System.out.println("the result age = " + age);

        textView = (TextView)findViewById(R.id.textView1);
        textView.setText("第二个 the result age =  + age");
    }
}
