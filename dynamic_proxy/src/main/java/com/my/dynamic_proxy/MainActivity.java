package com.my.dynamic_proxy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.my.dynamic_proxy.model.Obj;
import com.my.dynamic_proxy.model.ProxyRoot;
import com.my.dynamic_proxy.net_util.MyOkHttp;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "10001";
    TextView show_board;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show_board = findViewById(R.id.tvId);
        webView = findViewById(R.id.webview_id);
        String url_str = "http://www.ip111.cn/";
        webView.loadUrl(url_str);
    }

    public void test_func(View view) {
        String urlStr = "http://www.ip111.cn";
        MyOkHttp.getInstance().requestGet(urlStr, new MyOkHttp.OkHttpCallBack<String>() {

            @Override
            public void requestSuccess(String s) {
                Log.e(TAG, "success:" + s);
            }

            @Override
            public void requestFailure(String s) {
                Log.e(TAG, "failure:" + s);
            }
        }, String.class);
    }

    public void get_current_proxy(View view) {
        String http_host = System.getProperty("http.proxyHost");
        String http_port = System.getProperty("http.proxyPort");
        String text = http_host + " : " + http_port;
        showResponse(text);
    }

    public void get_internet_proxy(View view) {
        webView.reload();
        String urlStr = "http://www.ip111.cn";
        MyOkHttp.getInstance().requestGet(urlStr, new MyOkHttp.OkHttpCallBack<String>() {

            @Override
            public void requestSuccess(String s) {
                Log.e(TAG, "success:" + s);
            }

            @Override
            public void requestFailure(String s) {
                Log.e(TAG, "failure:" + s);
            }
        }, String.class);
    }

    public void replace_proxy(View view) {
        System.setProperty("http.maxRedirects", "50");
        // 如果不设置，只要代理IP和代理端口正确,此项不设置也可以
        System.getProperties().setProperty("proxySet", "true");
        String ip = "171.114.211.126";
        String port = "38180";
        System.getProperties().setProperty("http.proxyHost", ip);
        System.getProperties().setProperty("http.proxyPort", port);
    }

    //动态获取代理地址
    private void get_dynamic_proxy() {
        String urlStr = "http://route.xiongmaodaili.com/xiongmao-web/api/glip?secret=0669093de4b211ea65933ba3ca79bcbe&orderNo=GL20201126004658vSwZe7jx&count=1&isTxt=0&proxyType=1";
        MyOkHttp.getInstance().requestGet(urlStr, new MyOkHttp.OkHttpCallBack<String>() {

            @Override
            public void requestSuccess(String s) {
                Log.e(TAG, "success:" + s);
                Gson gson = new Gson();
                ProxyRoot proxyRoot = gson.fromJson(s, ProxyRoot.class);
                if (proxyRoot.getCode().equals("0")) {
                    Obj obj = proxyRoot.getObj().get(0);
                    System.out.println(obj.getIp() + " : " + obj.getPort());

                    System.setProperty("http.maxRedirects", "50");
                    // 如果不设置，只要代理IP和代理端口正确,此项不设置也可以
                    System.getProperties().setProperty("proxySet", "true");

                    String ip = obj.getIp();
                    String port = obj.getPort();
                    System.getProperties().setProperty("http.proxyHost", ip);
                    System.getProperties().setProperty("http.proxyPort", port);
                } else {
                    Log.e(TAG, "failture : " + proxyRoot.getMsg());
                }
            }

            @Override
            public void requestFailure(String s) {
                Log.e(TAG, "failure:" + s);
            }
        }, String.class);
    }

    public void replace_proxy_dynamic(View view) {
        get_dynamic_proxy();
    }

    public void post_func(View view) {
        String urlStr = "http://103.100.211.187:8848/getPost";
        HashMap<String, Object> parmas = new HashMap<>();
        parmas.put("username", "Dio");
        parmas.put("password", "13131313");
        parmas.put("argot", "You are geat!");
        parmas.put("num", 1111);

        MyOkHttp.getInstance().requestPost(urlStr, parmas, new MyOkHttp.OkHttpCallBack<String>() {
            @Override
            public void requestSuccess(String s) {
                Log.e(TAG, "success:" + s);
            }

            @Override
            public void requestFailure(String message) {
                Log.e(TAG, "failure:" + message);
            }
        }, String.class);
    }

    private void showResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                show_board.setText(response);//设置TextView的内容
            }
        });
    }
}
