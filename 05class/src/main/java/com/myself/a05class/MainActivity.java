package com.myself.a05class;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends Activity implements View.OnClickListener {

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btCalll = (Button) findViewById(R.id.bt_call);
        Button btClean = (Button) findViewById(R.id.bt_clearId);
        editText = (EditText) findViewById(R.id.editId);

        //btCall 事件源对象
        //为事件源对象注册事件监听
        //写好事件处理方法
        btCalll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹个小通知
                Toast.makeText(MainActivity.this, "呵呵，我被点到了", Toast.LENGTH_SHORT).show();

                //打电话
                //权限 --  获取手机特定功能的使用权限。在AndroidManifest.xml中添加
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.
                        permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {//android6 6.0以后需要判断是否已经授权,
                    //之前没有授权则先请求权限
                    ActivityCompat.requestPermissions(MainActivity.this, new
                            String[]{Manifest.permission.CALL_PHONE}, 1);
                } else {//之前已经给过权限
                    call();
                }
            }
        });

        btClean.setOnClickListener(this);
    }

    private void call() {
        try {
            String phoneNumber = editText.getText().toString();
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(intent);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        //弹个小通知
        editText.setText("");
        Toast.makeText(MainActivity.this, "清空所有!", Toast.LENGTH_LONG).show();
    }

    //用户处理授权后的回调方法。
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {//用户给了权限
                    call();
                } else {//没给权限
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
}
