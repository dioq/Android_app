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
        MyOkHttp.getInstance().requestGet("http://103.100.211.187:8848/getTest", new MyOkHttp.OkHttpCallBack<String>() {

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
        HashMap<String, Object> parmas = new HashMap<>();
        parmas.put("username", "Dio");
        parmas.put("password", "13131313");
        parmas.put("argot", "You are geat!");
        parmas.put("num", 1111);

        MyOkHttp.getInstance().requestPost(urlStr, parmas, new MyOkHttp.OkHttpCallBack<String>() {
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

    public void post_raw_func(View view) {
        String urlStr = "https://api.haipiaopiao.com/userHome/follow";
        String requestData = "requestData=DoAY2D4a5cjX4mj6vpvavWNcg5oZWxjaEnUKIbx8Tq%2Fom8%2F4i81mv%2BNs%2BYfpZVknkY8w1rvcacRr%0AOVsaZyfLAfDaAqHRQOViAjDoRSd5FXuwhYL5Yd%2Fim2otx%2BsjXgQF239aEI8fJY4Bq6kG6SlaL9nd%0AK2qvMFwv4toPxFTuQpZoz1fGHRWXaogBkxqjYCMdBLHYvHnz5797yB2B%2F5fYKEbxBvBNljo%2FmVW5%0AARWkyR2IeR3WL0uc9nuIuUSkkSjC0LImkef6R9WQHcwdHnDqi%2BjClKb41XXtS9N6Nfd5OwH94hHv%0A93S4gE9Mz4QtxIVj7A5WFZRzdUhrs%2FkHIEsI7LD7TidiAWdB3vn8zNbI5uiEfoR%2FOl1VjyL4cDb0%0AY%2BFDARCjAn%2B0kSzR%2F34bKvcdL0vIksIRM7cWTX4W%2F%2B79Og4M0YP7VSb6hpVzxq0fU8pGgppdKleB%0AhJCHEjePJPfF4PaMZzBYuW35iQ2HWc6EWw5h8RB41YBnUqsX4e8LQJrnbAQQq2lq5%2B5NgkM3VXlh%0AmBMBRLL0FLgBUYt1XEmvXLua8b%2Fl4W3oDciXUGQLp5Ke4LEEllQTxDsZ1qd7F9c%2BJLZl4H3HOZzf%0Akp7rcAlQ%2BX36fbnsFCFrOtV5NlgNakvgDNVcII9oJIhSda2cyXufbFc6cOGr6Xr0EVGQVsRgg33P%0A58N9MU2kK%2FZ7PHbQ%2BM4D52DWNSXUvxH41HFsgMY0iOK%2FUt3PIj2l7JKqClXz7DPvfOwdta0dGEge%0ATwbe8XfvRQu1ZeyhbZUCgVmRXqGtytGB%2FZGZnhOU8wWV%2BuoFTmuBMEhRtvh6gLgeFYS8eXj5WFaX%0AO7mOjGugejnPEYehV%2B0k4RTwY4dUM3zzoNN7wRHKxmpW4AgWOTRtnYXrwp9dGvBQeHYQ1boHlLr9%0A8KJbNHz1BY9wDUYcXcojUHSrfB6tLDO12WpLIQJ3XmiUiahJIO3LlLQaYlblAUhUHzcgiQ%3D%3D%0A&i_type=1&app_type=1&access_token=62f85ab2da095d10edf445070698ed24&ctl=null&act=null";
        MyOkHttp.getInstance().doPost(urlStr, requestData, new MyOkHttp.OkHttpCallBack<String>() {
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

    public void formdata_request_func(View view) {
        String url = "http://www.anant.club:8848/testFormdata";//接口地址

        HashMap<String, String> params = new HashMap<>();
        params.put("username", "dio");
        params.put("area", "guiyang");
        params.put("age", "19");
        params.put("action", "this is a action");

        MyOkHttp.getInstance().submitFormdata(url, params, new MyOkHttp.OkHttpCallBack<String>() {
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
