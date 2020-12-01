package com.myself.network;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.request_text);
    }


    public void post_func(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String urlStr = "http://www.anant.club:8848/getPost";
                    ArrayMap<String, Object> parmas = new ArrayMap<>();
                    parmas.put("username", "Dio");
                    parmas.put("password", "13131313");
                    parmas.put("argot", "You are geat!");
                    parmas.put("num", 1111);
                    JSONObject param_json = new JSONObject(parmas);
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
                    String urlStr = "https://api.haipiaopiao.com/userHome/follow";
                    String params_str = "requestData=DoAY2D4a5cjX4mj6vpvavWNcg5oZWxjaEnUKIbx8Tq%2Fom8%2F4i81mv%2BNs%2BYfpZVknkY8w1rvcacRr%0AOVsaZyfLAfDaAqHRQOViAjDoRSd5FXuwhYL5Yd%2Fim2otx%2BsjXgQF239aEI8fJY4Bq6kG6SlaL9nd%0AK2qvMFwv4toPxFTuQpZoz1fGHRWXaogBkxqjYCMdBLHYvHnz5797yB2B%2F5fYKEbxBvBNljo%2FmVW5%0AARWkyR2IeR3WL0uc9nuIuUSkkSjC0LImkef6R9WQHcwdHnDqi%2BjClKb41XXtS9N6Nfd5OwH94hHv%0A93S4gE9Mz4QtxIVj7A5WFZRzdUhrs%2FkHIEsI7LD7TidiAWdB3vn8zNbI5uiEfoR%2FOl1VjyL4cDb0%0AY%2BFDARCjAn%2B0kSzR%2F34bKvcdL0vIksIRM7cWTX4W%2F%2B79Og4M0YP7VSb6hpVzxq0fU8pGgppdKleB%0AhJCHEjePJPfF4PaMZzBYuW35iQ2HWc6EWw5h8RB41YBnUqsX4e8LQJrnbAQQq2lq5%2B5NgkM3VXlh%0AmBMBRLL0FLgBUYt1XEmvXLua8b%2Fl4W3oDciXUGQLp5Ke4LEEllQTxDsZ1qd7F9c%2BJLZl4H3HOZzf%0Akp7rcAlQ%2BX36fbnsFCFrOtV5NlgNakvgDNVcII9oJIhSda2cyXufbFc6cOGr6Xr0EVGQVsRgg33P%0A58N9MU2kK%2FZ7PHbQ%2BM4D52DWNSXUvxH41HFsgMY0iOK%2FUt3PIj2l7JKqClXz7DPvfOwdta0dGEge%0ATwbe8XfvRQu1ZeyhbZUCgVmRXqGtytGB%2FZGZnhOU8wWV%2BuoFTmuBMEhRtvh6gLgeFYS8eXj5WFaX%0AO7mOjGugejnPEYehV%2B0k4RTwY4dUM3zzoNN7wRHKxmpW4AgWOTRtnYXrwp9dGvBQeHYQ1boHlLr9%0A8KJbNHz1BY9wDUYcXcojUHSrfB6tLDO12WpLIQJ3XmiUiahJIO3LlLQaYlblAUhUHzcgiQ%3D%3D%0A&i_type=1&app_type=1&access_token=62f85ab2da095d10edf445070698ed24&ctl=null&act=null";
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
                    RequestBody body = RequestBody.create(mediaType, params_str);
                    Request request = new Request.Builder()
                            .url(urlStr)
                            .post(body)
                            .build();
                    OkHttpClient client = new OkHttpClient();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();//处理返回的数据
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
                    Request request = new Request.Builder().url("http://103.100.211.187:8848/getTest").build();//创建一个Request对象
                    Response response = client.newCall(request).execute();//发送请求获取返回数据
                    String responseData = response.body().string();//处理返回的数据
                    showResponse(responseData);//更新ui
                } catch (Exception e) {
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
