package com.my.webview01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webview_id);
    }

    public void load_webview(View view) {
        webView.loadUrl("https://www.baidu.com/");
    }

    public void refresh_webview(View view) {
        webView.reload();
    }
}
