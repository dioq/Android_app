package com.myself.network;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.request_text);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void post_func(View view) {
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
                    String json = param_json.toString();
                    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = RequestBody.create(JSON, json);
                    Request request = new Request.Builder()
                            .url(urlStr)
                            .post(body)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();//处理返回的数据
                    showResponse(responseData);//更新ui
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void request_func(View view) {
        sendRequestWithOkHttp();
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();//新建一个OKHttp的对象
                    Request request = new Request.Builder().url("http://103.100.211.187:8848/getTest").build();//创建一个Request对象
                    Response response = client.newCall(request).execute();//发送请求获取返回数据
                    String responseData = response.body().string();//处理返回的数据
                    showResponse(responseData);//更新ui
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
