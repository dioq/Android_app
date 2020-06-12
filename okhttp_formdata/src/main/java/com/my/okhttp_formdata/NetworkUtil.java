package com.my.okhttp_formdata;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkUtil {

    private static String TAG = "t1";

    public static void submitFormdata() {
        String url = "http://www.anant.club:8848/testFormdata";//接口地址

        String username = "dio";
        String area = "guiyang";
        String age = "19";
        String action = "this is a action";

        /**
         * 设置超时时间
         */
        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .connectTimeout(60 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(5 * 60 * 1000, TimeUnit.MILLISECONDS)
                .writeTimeout(5 * 60 * 1000, TimeUnit.MILLISECONDS)
                .build();

        //表单数据参数填入
        RequestBody body = new FormBody.Builder()
                .add("username", username)
                .add("area", area)
                .add("age", age)
                .add("action", action)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Call call = okHttpClient.newCall(request);

        try {
            Response response = call.execute();
            String result = response.body().string();//得到数据
            Log.e(TAG, "response:" + result);
        } catch (IOException e) {
            Log.e(TAG, "IOException:" + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            Log.e(TAG, "Exception:" + e.getMessage());
            e.printStackTrace();
        }
    }
}
