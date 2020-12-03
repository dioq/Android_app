package com.my.dynamic_proxy.net_util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyOkHttp {

    private static OkHttpClient client;

    private MyOkHttp() {
        client = new OkHttpClient
                .Builder()
                .connectTimeout(60 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(5 * 60 * 1000, TimeUnit.MILLISECONDS)
                .writeTimeout(5 * 60 * 1000, TimeUnit.MILLISECONDS)
                .build();
    }

    //单例
    private static class HttpOkHttpInstance {
        private final static MyOkHttp INSTANCE = new MyOkHttp();
    }

    public static MyOkHttp getInstance() {
        return HttpOkHttpInstance.INSTANCE;
    }

    /**
     * GET请求
     *
     * @param urlStr         请求地址
     * @param okHttpCallBack 请求回调
     * @param clazz          返回结果的Class
     * @param <T>            返回结果类型
     */
    public <T> void doGet(@NotNull String urlStr, @NotNull final OkHttpCallBack<T> okHttpCallBack,
                          @NotNull final Class<T> clazz) {
        Request request = new Request.Builder()
                .url(urlStr)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                okHttpCallBack.requestFailure(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    requestResult(response.body().string(), okHttpCallBack, clazz);
                } else {
                    okHttpCallBack.requestFailure(response.message());
                }
            }
        });
    }


    /**
     * POST请求               restful接口        application/json
     *
     * @param urlStr         请求地址
     * @param params         请求参数 Map<String,String> 格式
     * @param okHttpCallBack 请求回调
     * @param clazz          返回结果的class
     * @param <T>            请求返回的类型
     */
    public <T> void doPost(@NotNull String urlStr, @NotNull String params, @NotNull final OkHttpCallBack<T> okHttpCallBack,
                           @NotNull final Class<T> clazz) {
        //设置RequestBody的格式
        final MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(mediaType, params);
        Request request = new Request.Builder()
                .url(urlStr)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                okHttpCallBack.requestFailure(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    requestResult(response.body().string(), okHttpCallBack, clazz);
                } else {
                    okHttpCallBack.requestFailure(response.message());
                }
            }
        });
    }


    /**
     * POST                 提交Form表单(参数是处理后的字符串)
     *
     * @param urlStr         请求地址
     * @param params         请求参数 String 格式
     * @param okHttpCallBack 请求回调
     * @param clazz          返回结果的class
     * @param <T>            请求返回的类型
     */
    public <T> void submitForm(@NotNull String urlStr, @NotNull String params, @NotNull final OkHttpCallBack<T> okHttpCallBack,
                               @NotNull final Class<T> clazz) {
        final MediaType mediaType = MediaType.get("application/x-www-form-urlencoded;charset=UTF-8");
        RequestBody body = RequestBody.create(mediaType, params);
        Request request = new Request.Builder()
                .url(urlStr)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                okHttpCallBack.requestFailure(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    requestResult(response.body().string(), okHttpCallBack, clazz);
                } else {
                    okHttpCallBack.requestFailure(response.message());
                }
            }
        });
    }

    //将参数 处理成form表单能接收的格式
    public String getParams(HashMap<String, String> paramsMap) {
        String result = "";
        for (HashMap.Entry<String, String> entity : paramsMap.entrySet()) {
            result += "&" + entity.getKey() + "=" + entity.getValue();
        }
        return result.substring(1);
    }


    /**
     * POST                  提交Form表单(用OkHttp自带的FormBody.Builder来添加参数)
     *
     * @param urlStr         请求地址
     * @param params         请求参数 Map<String,String> 类型
     * @param okHttpCallBack 请求回调
     * @param clazz          返回结果的Class
     * @param <T>            返回结果类型
     */
    public <T> void submitForm2(@NotNull String urlStr, @NotNull HashMap<String, String> params, @NotNull final OkHttpCallBack<T> okHttpCallBack, @NotNull final Class<T> clazz) {
        //传进来的Map<String,String> 类型 prams 参数进行遍历 再 逐一添加到FormBody.Builder里 然后生成RequestBody
        FormBody.Builder builder = new FormBody.Builder();
        for (HashMap.Entry<String, String> entity : params.entrySet()) {
            builder.add(entity.getKey(), entity.getValue());
        }
        RequestBody body = builder.build();

        Request request = new Request.Builder()
                .url(urlStr)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                okHttpCallBack.requestFailure(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    requestResult(response.body().string(), okHttpCallBack, clazz);
                } else {
                    okHttpCallBack.requestFailure(response.message());
                }
            }
        });
    }


    /**
     * POST 上传图片
     *
     * @param urlStr         请求地址
     * @param filePath       请求参数 图片路径
     * @param okHttpCallBack 请求回调
     * @param clazz          返回结果的Class
     * @param <T>            返回结果类型
     */
    public <T> void uploadImage(@NotNull String urlStr, @NotNull String filePath, @NotNull final OkHttpCallBack<T> okHttpCallBack,
                                @NotNull final Class<T> clazz) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("所要上传的文件不存在,请认真检查!");
            return;
        }
        //1.创建对应的MediaType
        final MediaType mediaType = MediaType.parse("image/*");

        //2.创建RequestBody
        RequestBody fileBody = RequestBody.create(mediaType, file);

        //3.构建MultipartBody
        String name = "file";//后台服务器根据这个名取到Request
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(name, file.getName(), fileBody)
                .build();

        //4.构建请求
        Request request = new Request.Builder()
                .url(urlStr)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                okHttpCallBack.requestFailure(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    requestResult(response.body().string(), okHttpCallBack, clazz);
                } else {
                    okHttpCallBack.requestFailure(response.message());
                }
            }
        });
    }


    /**
     * POST                  上传二进制文件 Binary
     *
     * @param urlStr         请求地址
     * @param filePath       请求参数 二进制文件路径
     * @param okHttpCallBack 请求回调
     * @param clazz          返回结果的Class
     * @param <T>            返回结果类型
     */
    public <T> void uploadBinary(@NotNull String urlStr, @NotNull String filePath, @NotNull final OkHttpCallBack<T> okHttpCallBack,
                                 @NotNull final Class<T> clazz) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("所要上传的文件不存在,请认真检查!");
            return;
        }
        MediaType mediaType = MediaType.parse("application/octet-stream; charset=utf-8");
        FileInputStream fileInputStream = null;
        try {
            //把文件的二进制数据 读到输入流里
            fileInputStream = new FileInputStream(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBodyUtil.create(mediaType, fileInputStream);
        Request request = new Request.Builder()
                .url(urlStr)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                okHttpCallBack.requestFailure(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    requestResult(response.body().string(), okHttpCallBack, clazz);
                } else {
                    okHttpCallBack.requestFailure(response.message());
                }
            }
        });
    }

    private <T> void requestResult(String result, OkHttpCallBack<T> callBack, @NotNull Class<T> clazz) {
        if ("java.lang.String".equals(clazz.getName())) {
            callBack.requestSuccess((T) result);
        } else {
            Gson gson = new GsonBuilder().create();
            callBack.requestSuccess(gson.fromJson(result, clazz));
        }
    }


    public interface OkHttpCallBack<T> {
        /**
         * 请求成功回调
         *
         * @param t 回调返回成功结果输出
         */
        void requestSuccess(T t);

        /**
         * 请求失败回调
         *
         * @param message 回调返回失败消息
         */
        void requestFailure(String message);
    }

}
