package com.myself.network;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.HashMap;

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
                String response = NetworkUtil.getInstance().doGet("http://www.anant.club:10004/getTest");
                showResponse(response);
            }
        }).start();
    }

    public void post_func(View view) {
        new Thread(new Runnable() {//开启线程
            @Override
            public void run() {
                String urlStr = "http://www.anant.club:10004/getPost";
                HashMap<String, String> paraMap = new HashMap<>();
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
                final String imgPath = new ImageUtil().getPathByImage("money.jpg");
                Log.e(TAG, "imgPath:\n" + imgPath);
                NetworkUtil.getInstance().uploadFile(imgPath, "http://103.100.211.187:10004/upload");
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
