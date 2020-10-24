package com.my.judge_in_proxy;

import android.text.TextUtils;
import android.util.Log;

public class Check_Proxy {

    private static String TAG = "10001";

    /*
     * 判断设备 是否使用代理上网
     * */
    public static boolean isWifiProxy() {
        String host = System.getProperty("http.proxyHost");
        String port = System.getProperty("http.proxyPort");
        Log.e(TAG, "代理  --->  " + host + ":" + port);
        return !TextUtils.isEmpty(host) && !TextUtils.isEmpty(port);
    }
}
