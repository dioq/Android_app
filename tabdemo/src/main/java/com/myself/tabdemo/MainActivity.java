package com.myself.tabdemo;

import android.app.Activity;
import android.app.TabActivity;
import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取我们的切换卡布局
//        TabHost tabHost = getTabHost();
        //构建一个选项卡
//        tabHost.newTabSpec("001").setIndicator("斗地主").setContent(R.id.page0);
    }
}
