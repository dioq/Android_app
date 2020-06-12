package com.myself.network;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "t1";

    TextView show_board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show_board = (TextView) findViewById(R.id.tvId);
    }

    public void get_func(View view) {
        new Thread(new Runnable() {//只能在子线程中请求
            @Override
            public void run() {
                String response = NetworkUtil.getInstance().doGet("http://www.anant.club:8848/getTest");
                showResponse(response);
            }
        }).start();
    }

    public void post_func(View view) {
        new Thread(new Runnable() {//开启线程
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                String urlStr = "http://www.anant.club:8848/getPost";
                ArrayMap<String, String> paraMap = new ArrayMap<>();
                paraMap.put("username", "Dio");
                paraMap.put("password", "13131313");
                paraMap.put("argot", "You are geat!");
                paraMap.put("num", "1111");
                String response = NetworkUtil.getInstance().doPost(urlStr, paraMap);
                showResponse(response);
            }
        }).start();
    }

    public void uploadImage_func(View view) {
        new Thread(new Runnable() {//只能在子线程中请求
            @Override
            public void run() {
                ImageUtil.checkAndGet_permission(MainActivity.this);
                if (ImageUtil.havePermissions == false) {
                    Toast.makeText(MainActivity.this, "没有权限.请再次点击", Toast.LENGTH_LONG).show();
                }
                String imgPath = new ImageUtil().getPathByImage("money.jpg");
                Log.e(TAG, "imgPath:\n" + imgPath);
                String response = NetworkUtil.getInstance().uploadFile(imgPath, "http://103.100.211.187:8848/upload");
                showResponse(response);
            }
        }).start();
    }

    public void sumitFormdata_func(View view) {
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                String url = "http://www.anant.club:8848/testFormdata";//接口地址

                ArrayMap<String, String> params = new ArrayMap<>();
                params.put("username", "dio");
                params.put("area", "guiyang");
                params.put("age", "19");
                params.put("action", "this is a action");
                String response = NetworkUtil.getInstance().submitFormdata(url, params);
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
