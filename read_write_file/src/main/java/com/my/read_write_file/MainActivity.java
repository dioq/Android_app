package com.my.read_write_file;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity {

    TextView show_board;
    FileUtils fileUtils = null;
    public static final int PERMISSION_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fileUtils = new FileUtils();
        show_board = findViewById(R.id.tvId);

        myRequetPermission();
    }

    // 获取权限
    private void myRequetPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_EXTERNAL_STORAGE);
        }
    }

    public void read_txt(View view) {
        String path = "Pictures/text_001.txt";
        String result = fileUtils.readFromSDFile(path);
        show_board.setText(result);
    }

    public void write_txt(View view) {
        System.out.println("------> 2 0");
        System.out.println("------> 2");
        String token = "13U1KJDKAHDU13891HQWEJQDALSDA==";
        String path = "Pictures/text_001.txt";
        fileUtils.write_to_localFile(token, path);
    }

    //往文件尾部追加数据
    public void write_txt_toEnd(View view) {
        String token = "13U1KJDKAHDU13891HQWEJQDALSDA==";
        String path = "Pictures/text_001.txt";
        fileUtils.write_toEnd_localFile(token, path);
    }

    public void read_selected_line(View view) {
        String path = "Pictures/latlon.txt";
        int totalNum = fileUtils.getTotalLines(path);
        System.out.println("totalNum : " + totalNum);
        //读取最后一行(读第一行 传1 而不是0)
        String line = fileUtils.readLineByIndex(path, totalNum);
        System.out.println("line : " + line);
    }
}
