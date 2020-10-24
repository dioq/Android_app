package com.my.judge_in_proxy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "10001";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tex_id_1);

        // 如果是本机自己测试,不需要代理请求,但发到服务器上的时候需要代理请求
        // 对http开启全局代理
        //System.setProperty("http.proxyHost", proxyHost);
        //System.setProperty("http.proxyPort", proxyPort);
        // 对https开启全局代理
        //System.setProperty("https.proxyHost", proxyHost);
        //System.setProperty("https.proxyPort", proxyPort);
    }

    //设置代理(目前这种方法基本无效)
    public void set_proxy(View view) {
        //通知Java您要通过代理进行连接
        System.getProperties().put("proxySet", "true");
        Properties props = System.getProperties();
        //指定代理所在的服务器
        props.put("http.proxyHost", "103.100.211.187");
        //指定代理监听的端口
        props.put("http.proxyPort", "8848");
    }

    //判断是否有代理
    public void judge_fun(View view) {
        Properties props = System.getProperties();
        String http_host = props.getProperty("http.proxyHost");
        String http_port = props.getProperty("http.proxyPort");
        String text = http_host + " : " + http_port;
        if (Check_Proxy.isWifiProxy()) {
            text += "\t\t代理上网";
        } else {
            text += "\t\t非代理上网";
        }
        textView.setText(text);
    }

    //重新设置OkHttp走代理(这种方法只能拿来作演示)
    public void invalidate_proxy(View view) {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.1.7", 8888));

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(60 * 1000, TimeUnit.MILLISECONDS);
        builder.readTimeout(5 * 60 * 1000, TimeUnit.MILLISECONDS);
        builder.writeTimeout(5 * 60 * 1000, TimeUnit.MILLISECONDS);
//        builder.proxy(Proxy.NO_PROXY);//不使用代理,网络抓不到包
        builder.proxy(proxy);
        MyOkHttp.client = builder.build();
    }

    public void doNetwork(View view) {
        MyOkHttp.getInstance().requestGet("http://103.100.211.187:8848/getTest", new MyOkHttp.OkHttpCallBack<String>() {

            @Override
            public void requestSuccess(String s) {
                showResponse(s);
                Log.e(TAG, "success:" + s);
            }

            @Override
            public void requestFailure(String s) {
                showResponse(s);
                Log.e(TAG, "failure:" + s);
            }
        }, String.class);
    }

    public void do_native_network(View view) {
        new Thread(new Runnable() {//只能在子线程中请求
            @Override
            public void run() {
                String response = NetworkUtil.getInstance().doGet("http://www.anant.club:8848/getTest");
                System.out.println("response:\n" + response);
                showResponse(response);
            }
        }).start();
    }

    private void showResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(response);
            }
        });
    }

}
