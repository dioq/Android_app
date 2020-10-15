package com.my.retrofit_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "10001";
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.request_text);
    }


    public void get_func(View view) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IUserService iUserService = retrofit.create(IUserService.class);
        Call<User> call = iUserService.getUser("Tom");
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.i(TAG, "response = " + response.body().toString());
                User user = response.body();
                showResponse(user.toString());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i(TAG, "fail : " + t);
            }
        });
    }


    public void post_func(View view) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IUserService iUserService = retrofit.create(IUserService.class);
        Call<User> call = iUserService.postUser("JOJO", "131983e");
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.i(TAG, "response = " + response.body().toString());
                User user = response.body();
                showResponse(user.toString());
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {
                Log.i(TAG, "fail : " + throwable);
            }
        });
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
