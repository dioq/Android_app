package com.my.linearlayout3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //util 里的 普通方法
    public void ordinaryFunc(View view) {

    }

    //util 里的 重载方法
    public void override_function(View view) {

    }

    //Student 里的 构造方法
    public void construction_func(View view) {

    }

    //MySingleton 单例里的方法
    public void single_func(View view) {

    }

    //hook 匿名内部类方法
    public void internalClassFunc(View view) {

    }

    //hook 匿名内部类方法 2
    public void internalClassFunc2(View view) {

    }

    //调用函数
    public void load_func(View view) {

    }

    //util 里的静态方法
    public void staic_func(View view) {

    }

    public void ndk_func(View view) {

    }

    public void ndk_so(View view) {

    }

    public void ndk_so2(View view) {

    }
}
