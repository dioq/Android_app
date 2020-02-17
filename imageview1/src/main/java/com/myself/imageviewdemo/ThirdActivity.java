package com.myself.imageviewdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ThirdActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        imageView = (ImageView) findViewById(R.id.imgV1);
    }

    public void show(View view) {
        Toast.makeText(this, "加载", Toast.LENGTH_SHORT).show();
        imageView.setImageResource(R.drawable.pic3);
    }

}
