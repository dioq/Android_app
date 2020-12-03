package com.my.network;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.my.network.util.ImageUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "t1";
    private TextView show_board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show_board = findViewById(R.id.tvId);
        //进入app就获取 所有的权限
        myRequetPermission();
    }

    private void myRequetPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PERMISSION_GRANTED) {
            String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
        }
    }

    public void get_request_func(View view) {
        MyOkHttp.getInstance().doGet("http://103.100.211.187:8848/getTest", new MyOkHttp.OkHttpCallBack<String>() {

            @Override
            public void requestSuccess(String s) {
                showResponse(s);//更新ui
                Log.e(TAG, "success:" + s);
            }

            @Override
            public void requestFailure(String s) {
                Log.e(TAG, "failure:" + s);
                showResponse(s);//更新ui
            }
        }, String.class);
    }

    public void post_request_func(View view) {
        String urlStr = "http://www.anant.club:8848/getPost";
        JSONObject parmas = new JSONObject();
        try {
            parmas.put("username", "Dio");
            parmas.put("password", "13131313");
            parmas.put("argot", "You are geat!");
            parmas.put("num", 1111);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String requestData = parmas.toString();
        System.out.println("requestData :\n" + requestData);
        MyOkHttp.getInstance().doPost(urlStr, requestData, new MyOkHttp.OkHttpCallBack<String>() {
            @Override
            public void requestSuccess(String s) {
                Log.e(TAG, "success:" + s);
                showResponse(s);//更新ui
            }

            @Override
            public void requestFailure(String message) {
                Log.e(TAG, "failure:" + message);
                showResponse(message);//更新ui
            }
        }, String.class);
    }

    public void submitform_func(View view) {
        String urlStr = "http://www.anant.club:8848/testFormdata";
        HashMap<String, String> params = new HashMap<>();
        params.put("username", "dio");
        params.put("area", "guiyang");
        params.put("age", "19");
        params.put("action", "this is a action");
        String requestData = MyOkHttp.getInstance().getParams(params);
        System.out.println("requestData :\n" + requestData);
        MyOkHttp.getInstance().submitForm(urlStr, requestData, new MyOkHttp.OkHttpCallBack<String>() {
            @Override
            public void requestSuccess(String s) {
                Log.e(TAG, "success:" + s);
                showResponse(s);//更新ui
            }

            @Override
            public void requestFailure(String message) {
                Log.e(TAG, "failure:" + message);
            }
        }, String.class);
    }

    public void submitform2_func(View view) {
        String url = "http://www.anant.club:8848/testFormdata";//接口地址

        HashMap<String, String> params = new HashMap<>();
        params.put("username", "dio");
        params.put("area", "guiyang");
        params.put("age", "19");
        params.put("action", "this is a action");

        MyOkHttp.getInstance().submitForm2(url, params, new MyOkHttp.OkHttpCallBack<String>() {
            @Override
            public void requestSuccess(String s) {
                Log.e(TAG, "success:" + s);
                showResponse(s);//更新ui
            }

            @Override
            public void requestFailure(String message) {
                Log.e(TAG, "failure:" + message);
                showResponse(message);//更新ui
            }
        }, String.class);
    }

    //上传图片
    public void upload_image(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PERMISSION_GRANTED) {
            myRequetPermission();
            return;
        }
        String urlStr = "http://103.100.211.187:8848/upload";
        String imgPath = new ImageUtil().getPathByImage("money.jpg");
        MyOkHttp.getInstance().uploadImage(urlStr, imgPath, new MyOkHttp.OkHttpCallBack<String>() {
            @Override
            public void requestSuccess(String s) {
                Log.e(TAG, "success:" + s);
                showResponse(s);//更新ui
            }

            @Override
            public void requestFailure(String message) {
                Log.e(TAG, "failure:" + message);
                showResponse(message);//更新ui
            }
        }, String.class);
    }

    //上传二进制文件
    public void upload_binary(View view) {
        String urlStr = "https://upload.gmugmu.com/api/v1/base/resource/image/upload?source_info=eyJhcHBpZCI6IjI2MDAwMyIsInVpZCI6IjMyNDk3MjMyIiwicGFnZSI6ImNvbS5lb21jaGF0Lm1vZHVsZS5ob21lLkhvbWVQYWdlQWN0aXZpdHkiLCJ0aW1lIjoiMTYwNjI0Mjc3MDA5NiJ9&cc=TG73257&dev_name=nubia&oaid=&cpu=[Adreno_(TM)_630][ARMv7_639_placeholder]&lc=37427d9d8660d3f7&osversion=android_22&sid=02Yok7jQeFpBTR+Uz1tDaONajE3oi6XdrdqPwUyM/joBe9tJRE6grYwCweYmrwWh&ndid=&conn=wifi&ram=3650129920&msid=363635333236303436313030303634&icc=89860081133720371180&statistics=9ad290c3317d39cc8be58b98f74c86e7&mtid=e6352e4b164246b8b0be20f9c36f5abe&atid=302e30&tourist=&ongd=302e30&mtxid=00812dc6db02&evid=3535303335643264616635632d343464382d626465332d373262342d3034366638353761&cv=GM4.6.30_Android&proto=&logid=2006201,2001902,2002202,201802,202101,204005,204401,2005402,2006102,2001701,2003301,2005601,2005502,2001501,2005202,204301,2001802,2005801,2003502,2004701,2000802,201007,2001602,2004901,2002101,2003802,2005902,204101,2006001,2004801,2001202&ua=nubiaNX629J&uid=32497232&vv=202010141815&meid=363834373537373230363631353638&smid=&aid=b35517406764bf51";
        //这里上传图片选的是相册Pictures里的图片, 可以根据需要把图片放在其他路径下,只要路径正确即可
        File dataDir = Environment.getExternalStorageDirectory();
        String path = dataDir.getPath() + File.separator + "Pictures" + File.separator;
        String imgPath = path + "006.jpg";
        MyOkHttp.getInstance().uploadBinary(urlStr, imgPath, new MyOkHttp.OkHttpCallBack<String>() {
            @Override
            public void requestSuccess(String s) {
                Log.e(TAG, "success:" + s);
                showResponse(s);//更新ui
            }

            @Override
            public void requestFailure(String message) {
                Log.e(TAG, "failure:" + message);
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
