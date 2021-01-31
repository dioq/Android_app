package com.myself.network;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.myself.network.SSL.CertificateManagers;
import com.myself.network.SSL.MyHostnameVerifier;
import com.myself.network.SSL.MyTrustManager;
import com.myself.network.SSL.SSLTrustWhich;
import com.myself.okhttpdemo.R;

import org.json.JSONObject;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.request_text);
        myRequetPermission();//给权限
    }

    private void myRequetPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PERMISSION_GRANTED) {
            String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
        }
    }

    public void post_func(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String urlStr = "http://www.anant.club:8081/postdata";
                    JSONObject param_json = new JSONObject();
                    param_json.put("name", "JOJO");
                    param_json.put("age", 30);
                    String json_str = param_json.toString();
                    MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
                    RequestBody body = RequestBody.create(mediaType, json_str);
                    Request request = new Request.Builder()
                            .url(urlStr)
                            .post(body)
                            .build();
                    OkHttpClient client = new OkHttpClient();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();//处理返回的数据
                    System.out.println("responseData : \n" + responseData);
                    showResponse(responseData);//更新ui
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void post_raw_func(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String urlStr = "http://www.anant.club:8081/formdata";
                    String params_str = "nameDio&age=20";
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
                    RequestBody body = RequestBody.create(mediaType, params_str);
                    Request request = new Request.Builder()
                            .url(urlStr)
                            .post(body)
                            .build();
                    OkHttpClient client = new OkHttpClient();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();//处理返回的数据
                    System.out.println("responseData : \n" + responseData);
                    showResponse(responseData);//更新ui
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void request_func(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();//新建一个OKHttp的对象
                    Request request = new Request.Builder().url("http://www.anant.club:8081/getdata").build();//创建一个Request对象
                    Response response = client.newCall(request).execute();//发送请求获取返回数据
                    String responseData = response.body().string();//处理返回的数据
                    System.out.println("responseData : \n" + responseData);
                    showResponse(responseData);//更新ui
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void get_tls_twoway_func(View view) {
        new Thread(new Runnable() {//只能在子线程中请求
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder().url("https://www.anant.club:8083/getdata").build();//创建一个Request对象

                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    SSLContext sslContext = CertificateManagers.getSSLContext(SSLTrustWhich.TrustMeTwoway);
                    MyTrustManager myTrustManager = new MyTrustManager();
                    //添加证书工厂
                    builder.sslSocketFactory(sslContext.getSocketFactory(), myTrustManager);
                    //添加域名验证
                    builder.hostnameVerifier(new MyHostnameVerifier());

                    OkHttpClient client = builder.build();
                    Response response = client.newCall(request).execute();//发送请求获取返回数据
                    String responseData = response.body().string();//处理返回的数据
                    System.out.println("responseData : \n" + responseData);
                    showResponse(responseData);//更新ui
                } catch (IOException | CertificateException | NoSuchAlgorithmException | UnrecoverableKeyException | KeyStoreException | KeyManagementException e) {
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
