package com.my.network_tool;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "t1";
    private TextView show_board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show_board = (TextView) findViewById(R.id.tvId);
    }

    public void get_request_func(View view) {
        MyOkHttp.getInstance().requestGet("http://www.anant.club:10004/getTest", new MyOkHttp.OkHttpCallBack<String>() {

            @Override
            public void requestSuccess(String s) {
                showResponse(s);//更新ui
                Log.e(TAG, "success:" + s);
            }

            @Override
            public void requestFailure(String s) {
                Log.e(TAG, "fail:" + s);
                showResponse(s);//更新ui
            }
        }, String.class);
    }

    public void post_request_func(View view) {
        String urlStr = "http://www.anant.club:10004/getPost";
        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("username", "Dio");
        paraMap.put("password", "13131313");
        paraMap.put("argot", "You are geat!");
        paraMap.put("num", 1111);

        JSONObject param_json = new JSONObject(paraMap);
        String parmas = param_json.toString();
        System.out.println(param_json.toString());

        MyOkHttp.getInstance().requestPost(urlStr, parmas, new MyOkHttp.OkHttpCallBack<String>() {
            @Override
            public void requestSuccess(String s) {
                Log.e(TAG, "success:" + s);
                showResponse(s);//更新ui
            }

            @Override
            public void requestFailure(String message) {
                Log.e(TAG, "failre:" + message);
                showResponse(message);//更新ui
            }
        }, String.class);
    }

    public void formdata_request_func(View view) {
        String url = "http://www.anant.club:10004/testFormdata";//接口地址

        String username = "dio";
        String area = "guiyang";
        String age = "19";
        String action = "this is a action";

        //表单数据参数填入
        RequestBody body = new FormBody.Builder()
                .add("username", username)
                .add("area", area)
                .add("age", age)
                .add("action", action)
                .build();
        MyOkHttp.getInstance().submitFormdata(url, body, new MyOkHttp.OkHttpCallBack<String>() {
            @Override
            public void requestSuccess(String s) {
                Log.e(TAG, "success:" + s);
                showResponse(s);//更新ui
            }

            @Override
            public void requestFailure(String message) {
                Log.e(TAG, "failre:" + message);
                showResponse(message);//更新ui
            }
        }, String.class);
    }

    private void showResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                show_board.setText(response);
            }
        });
    }
}
