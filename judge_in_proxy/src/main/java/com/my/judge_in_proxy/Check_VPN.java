package com.my.judge_in_proxy;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

public class Check_VPN {

    private static String TAG = "10001";

    /*
     * 判断设备 是否使用代理上网
     * */
    public static boolean isWifiProxy(Context context) {
        String host = System.getProperty("http.proxyHost");
        String port = System.getProperty("http.proxyPort");
        Log.e("-->", host + ":" + port);
        return !TextUtils.isEmpty(host) && !TextUtils.isEmpty(port);
    }
}
