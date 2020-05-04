package com.myself.a07class;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void show1(View view) {
        Toast.makeText(this, "打印日志", Toast.LENGTH_SHORT).show();

        //日志有5个级别，细化了输出信息分类
        /*
         * verbose:描述
         * debug:调试
         * info:普通系统信息
         * warning:警告
         * error:错误
         **/
        Log.v("xxxx", "======== 描述 =========");
        Log.d("xxxx", "======== 调试 =========");
        Log.i("xxxx", "======== 普通系统信息 =========");
        Log.w("xxxx", "======== 警告 =========");
        Log.e("xxxx", "======== 错误 =========");

        /*
         *
         * */
    }

    /*
    * 日志可以统一管理:
    * 程序在开发阶段会打印很多的日志帮我们做调试，发布以后就不再需要了
    * 所以我们需要，在开发阶段能够打印日志，但是发布后就不能再去打印
    * */
    public void show2(View view) {
        Toast.makeText(this, "开启日志", Toast.LENGTH_SHORT).show();
        LogUtil.isDebugMode = true;
    }

    public void show3(View view) {
        Toast.makeText(this, "关闭日志", Toast.LENGTH_SHORT).show();
        LogUtil.isDebugMode = false;
    }

    public void show4(View view) {
        Toast.makeText(this, "测试日志", Toast.LENGTH_SHORT).show();
        LogUtil.logI("hehe","abcd1");
        LogUtil.logI("hehe","abcd2");
        LogUtil.logI("hehe","abcd3");
        LogUtil.logI("hehe","abcd4");
        LogUtil.logI("hehe","abcd5");
    }

}
