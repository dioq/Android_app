package com.my.json_parse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;

import com.my.json_parse.model.Login_info;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void gson_func(View view) {
        String json_str = "{\"status\":1,\"error\":\"\\u767b\\u5f55\\u6210\\u529f\",\"first_login\":0,\"user_id\":5685426,\"is_lack\":0,\"is_agree\":0,\"nick_name\":\"\\u4e0b\\u534a\\u8eab\\u6027\\u798f\",\"user_info\":{\"is_show_promo_code_box\":0,\"user_id\":\"5685426\",\"nick_name\":\"\\u4e0b\\u534a\\u8eab\\u6027\\u798f\",\"area_code\":\"86\",\"mobile\":\"17683927317\",\"head_image\":\"https:\\/\\/image.zyqp001.com\\/H116X79G5OniF\\/channel\\/8\\/base_config_icon_1.png\",\"token\":\"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ5YW4taHVhLXhpYW5nIiwiYXVkIjoieWFuLWh1YS14aWFuZyIsImlhdCI6MTYwMTQ0NTI1OSwibmJmIjoxNjAxNDQ1MjU5LCJleHAiOjE2MjczNjUyNTksInVzZXJfaW5mbyI6eyJpc19zaG93X3Byb21vX2NvZGVfYm94IjowLCJ1c2VyX2lkIjoiNTY4NTQyNiIsIm5pY2tfbmFtZSI6Ilx1NGUwYlx1NTM0YVx1OGVhYlx1NjAyN1x1Nzk4ZiIsImFyZWFfY29kZSI6Ijg2IiwibW9iaWxlIjoiMTc2ODM5MjczMTciLCJoZWFkX2ltYWdlIjoiaHR0cHM6XC9cL2ltYWdlLnp5cXAwMDEuY29tXC9IMTE2WDc5RzVPbmlGXC9jaGFubmVsXC84XC9iYXNlX2NvbmZpZ19pY29uXzEucG5nIn19.i8GKJG4Y2jf8rRsdaIzDzzjR1dC4XIgfI5rnr_bYBb4\",\"can_live\":0,\"can_video\":0,\"can_audio\":0},\"new_level\":0,\"login_send_score\":0,\"applozic_password\":\"\",\"_time_stamp\":\"1601416459\",\"act\":\"\",\"ctl\":\"\",\"code\":1,\"msg\":\"\\u767b\\u5f55\\u6210\\u529f\"}";
        Gson gson = new Gson();
        // 将json 转化成 java 对象
        //fromJson方法。参数一是json字符串。参数二是要转换的javabean
        //该javabean的字段名必须与json的key名字完全对应才能被正确解析。
        Login_info login_info = gson.fromJson(json_str, Login_info.class);
        System.out.println(login_info.toString());
    }
}
