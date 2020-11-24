package com.my.judge_in_proxy;

public class CheckProxy {

    /*
     * 判断设备 是否使用代理上网
     * */
    public static boolean isWifiProxy() {
        String host = System.getProperty("http.proxyHost");
        String port = System.getProperty("http.proxyPort");
        if (host != null || port != null) {
            return true;
        }
        String host_ssl = System.getProperty("https.proxyHost");
        String port_ssl = System.getProperty("https.proxyPort");
        if (host_ssl != null || port_ssl != null) {
            return true;
        }
        return false;
    }

}
