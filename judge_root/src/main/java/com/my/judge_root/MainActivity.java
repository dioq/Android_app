package com.my.judge_root;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tex_id_1);
    }

    @SuppressLint("SetTextI18n")
    public void judege_fun(View view) {
        boolean isRoot = CheckRoot.isSuEnable();
        if (isRoot) {
            textView.setText("系统已经root --------------> true");
        } else {
            textView.setText("系统没有root ----------> false");
        }
    }

}
