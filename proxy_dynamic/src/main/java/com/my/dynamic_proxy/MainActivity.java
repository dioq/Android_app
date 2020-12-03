package com.my.dynamic_proxy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.my.dynamic_proxy.model.Obj;
import com.my.dynamic_proxy.model.ProxyRoot;
import com.my.dynamic_proxy.net_util.MyOkHttp;
import com.my.dynamic_proxy.net_util.NetworkUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "10001";
    private TextView show_board;
    private String ip = null;
    private String port = null;
    private List<Map<String, String>> proxy_address_resource = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show_board = findViewById(R.id.tvId);
    }

    //获取系统属性,查看当前代理地址
    public void get_system_proxy(View view) {
        String http_host = System.getProperty("http.proxyHost");
        String http_port = System.getProperty("http.proxyPort");
        String text = "当前系统代理\t" + http_host + " : " + http_port;
        showResponse(text);
    }

    //获取代理地址
    public void get_proxy_address(View view) {
        final String urlStr = "http://route.xiongmaodaili.com/xiongmao-web/api/glip?secret=e9487221405fcbd530cc5a4ccb0f33e1&orderNo=GL20201202201117G50veWgu&count=1&isTxt=0&proxyType=1";
        new Thread(new Runnable() {
            @Override
            public void run() {
                NetworkUtil networkUtil = new NetworkUtil(Proxy.NO_PROXY);//不走任何代理
                String response = networkUtil.doGet(urlStr);
                System.out.println("response:\n" + response);
                Gson gson = new Gson();
                ProxyRoot proxyRoot = gson.fromJson(response, ProxyRoot.class);
                if (proxyRoot.getCode().equals("0")) {
                    Obj obj = proxyRoot.getObj().get(0);
                    String ip_tmp = obj.getIp();
                    String port_tmp = obj.getPort();

                    if (ip != null && port != null) {
                        if (proxy_address_resource.size() >= 3) {
                            proxy_address_resource.clear();
                        }
                        //把获取的所有代理保存起来,一会测试能不能每个网络请求 都单独走一个代理
                        Map<String, String> map = new HashMap<>();
                        map.put("ip", ip_tmp);
                        map.put("port", port_tmp);
                        proxy_address_resource.add(map);
                        System.out.println("proxy size = " + proxy_address_resource.size());
                    } else {
                        ip = ip_tmp;
                        port = port_tmp;
                    }
                } else {
                    Log.e(TAG, "failture : " + proxyRoot.getMsg());
                }
            }
        }).start();
    }

    //设置系统代理
    public void replace_proxy(View view) {
        /*
         * 注意:
         * 如果动态IP错误，会以本机网络IP访问。如果端口错误，会报错。
         * 其实这里可以不设置,只要网络请求时设置的代理proxy正确,依然会通过指定代理地址发起网络请求
         * */
        System.setProperty("http.maxRedirects", "50");
        System.getProperties().setProperty("proxySet", "true");
        System.getProperties().setProperty("http.proxyHost", ip);
        System.getProperties().setProperty("http.proxyPort", port);
    }

    //访问ip111.cn 查看网络请求时所走的 代理地址
    public void check_proxy_address(View view) {
        System.out.println("设置的系统代理\t" + ip + " : " + port);
        final String urlStr = "http://www.ip111.cn";
        new Thread(new Runnable() {
            @Override
            public void run() {
                NetworkUtil networkUtil = new NetworkUtil();//会走设置的系统代理,如果自己设置的系统代理走不通,会以本机网络IP访问
                String response = networkUtil.doGet(urlStr);
//                System.out.println("response:\n" + response);
                if (response.contains(ip)) {
                    String tip = "访问ip111.cn时,走的是代理ip : " + ip;
                    showResponse(tip);
                } else {
                    showResponse("代理地址设置失败!");
                }
            }
        }).start();
    }

    public void get_func(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String urlStr = "http://www.anant.club:8848/getTest";
                NetworkUtil networkUtil = new NetworkUtil();//会走设置的系统代理,如果自己设置的系统代理走不通,会以本机网络IP访问
                String response = networkUtil.doGet(urlStr);
                System.out.println("response:\n" + response);
                showResponse(response);
            }
        }).start();
    }

    public void get_func2(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String urlStr = "http://www.anant.club:8848/getTest";
                NetworkUtil networkUtil = new NetworkUtil(Proxy.NO_PROXY);//不走一切代理,包括系统的
                String response = networkUtil.doGet(urlStr);
                System.out.println("response:\n" + response);
                showResponse(response);
            }
        }).start();
    }

    public void post_func(View view) {
        if (ip == null || port == null) {
            Toast.makeText(this, "这里代理地址不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        int port2 = Integer.parseInt(port);
        final Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port2));
        System.out.println("当前网络设定的代理地址\t" + ip + " : " + port);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String urlStr = "http://www.anant.club:8848/getPost";
                    ArrayMap<String, Object> parmas = new ArrayMap<>();
                    parmas.put("username", "Dio");
                    parmas.put("password", "13131313");
                    parmas.put("argot", "You are geat!");
                    parmas.put("num", 1111);
                    JSONObject param_json = new JSONObject(parmas);
                    String json_str = param_json.toString();
                    MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
                    RequestBody body = RequestBody.create(mediaType, json_str);
                    Request request = new Request.Builder()
                            .url(urlStr)
                            .post(body)
                            .build();
                    OkHttpClient client = new OkHttpClient
                            .Builder()
                            .connectTimeout(60 * 1000, TimeUnit.MILLISECONDS)
                            .readTimeout(5 * 60 * 1000, TimeUnit.MILLISECONDS)
                            .writeTimeout(5 * 60 * 1000, TimeUnit.MILLISECONDS)
                            .proxy(proxy)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();//处理返回的数据
                    System.out.println("response:\n" + responseData);
                    showResponse(responseData);//更新ui
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void post_func1(View view) {
        if (proxy_address_resource.size() < 1) {
            Toast.makeText(this, "数组越界", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> map = proxy_address_resource.get(0);
        final String ip2 = map.get("ip");
        final String port2 = map.get("port");
        System.out.println("当前网络设定的代理地址\t" + ip2 + " : " + port2);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String urlStr = "http://www.anant.club:8848/getTest";
                NetworkUtil networkUtil = new NetworkUtil(ip2, port2);
                String response = networkUtil.doGet(urlStr);
                System.out.println("response:\n" + response);
                showResponse(response);
            }
        }).start();
    }

    public void post_func2(View view) {
        if (proxy_address_resource.size() < 2) {
            Toast.makeText(this, "数组越界", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> map = proxy_address_resource.get(1);
        final String ip2 = map.get("ip");
        final String port2 = map.get("port");
        System.out.println("当前网络设定的代理地址\t" + ip2 + " : " + port2);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String urlStr = "http://www.anant.club:8848/getTest";
                NetworkUtil networkUtil = new NetworkUtil(ip2, port2);
                String response = networkUtil.doGet(urlStr);
                System.out.println("response:\n" + response);
                showResponse(response);
            }
        }).start();
    }

    public void post_func3(View view) {
        if (proxy_address_resource.size() < 3) {
            Toast.makeText(this, "数组越界", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> map = proxy_address_resource.get(2);
        final String ip2 = map.get("ip");
        final String port2 = map.get("port");
        System.out.println("当前网络设定的代理地址\t" + ip2 + " : " + port2);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String urlStr = "http://www.anant.club:8848/getTest";
                NetworkUtil networkUtil = new NetworkUtil(ip2, port2);
                String response = networkUtil.doGet(urlStr);
                System.out.println("response:\n" + response);
                showResponse(response);
            }
        }).start();
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
