package cn.my.sandbox_study;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "sandbox_files";

    TextView show_board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show_board = findViewById(R.id.tvId);
    }

    /*
    内部存储: 不需要申请读取权限
    位置
    Android 内部存储在/data/data/目录下，根据应用的包名划分出来。 每个应用都有如下几个子文件夹：
        data/data/pkgName/shared_prefs：存放该APP内的SP信息
        data/data/pkgName/databases：存放该APP的数据库信息
        data/data/pkgName/files：将APP的文件信息存放在files文件夹
        data/data/pkgName/cache：存放的是APP的缓存信息
    * */
    public void inner_store(View view) {
        Context context = MainActivity.this;
//        Object shared_prefs_file = context.getSharedPreferences("login_status",0);
//        Log.d(TAG,"getSharedPreferences:" + shared_prefs_file);

        File filesDir_file = context.getFilesDir();
        Log.d(TAG, "context.getFilesDir():" + filesDir_file.getPath());

        File cache_file = context.getCacheDir();
        Log.d(TAG, "context.getCacheDir():" + cache_file.getPath());

        File databases_file = context.getDatabasePath("msg.db");
        Log.d(TAG, "context.getDatabasePath():" + databases_file.getPath());
    }

    /*
    外部存储:必须申请权限

        Environment.getDataDirectory() = /data
        Environment.getDownloadCacheDirectory() = /cache
        Environment.getExternalStorageDirectory() = /mnt/sdcard
        Environment.getExternalStoragePublicDirectory(“test”) = /mnt/sdcard/test
        Environment.getRootDirectory() = /system
        getPackageCodePath() = /data/app/com.my.app-1.apk
        getPackageResourcePath() = /data/app/com.my.app-1.apk
        getCacheDir() = /data/data/com.my.app/cache
        getDatabasePath(“test”) = /data/data/com.my.app/databases/test
        getDir(“test”, Context.MODE_PRIVATE) = /data/data/com.my.app/app_test
        getExternalCacheDir() = /mnt/sdcard/Android/data/com.my.app/cache
        getExternalFilesDir(“test”) = /mnt/sdcard/Android/data/com.my.app/files/test
        getExternalFilesDir(null) = /mnt/sdcard/Android/data/com.my.app/files
        getFilesDir() = /data/data/com.my.app/files
    * */
    public void external_store(View view) {
        File external_file = Environment.getExternalStorageDirectory();
        Log.d(TAG, "getExternalStorageDirectory():" + external_file.getPath());

        File rootDirectory = Environment.getRootDirectory();
        Log.d(TAG, "getRootDirectory():" + rootDirectory.getPath());

        File dataDirectory = Environment.getDataDirectory();
        Log.d(TAG, "getDataDirectory():" + dataDirectory.getPath());

        Context context = MainActivity.this;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            File cacheDir = context.getExternalCacheDir();
            Log.d(TAG, "context.getExternalCacheDir():" + cacheDir.getPath());
        }
    }

    public void program_path(View view) {
        Context context = MainActivity.this;

        // 获取当前程序路径  /data/user/0/pkgname/files
        File filesDir = context.getFilesDir();
        Log.d(TAG, "getFilesDir():" + filesDir.getAbsolutePath());
        // 获取该程序的安装包路径  /data/app/xxx
        String packageResourcePath = context.getPackageResourcePath();
        Log.d(TAG, "getPackageResourcePath():" + packageResourcePath);
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