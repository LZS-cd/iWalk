package com.example.boge.laonianbao.HealthTips;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.boge.laonianbao.R;

public class HealthWebViewActivity extends AppCompatActivity {
    private WebView webview;
    private String href;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.healthwebview);
        webview = (WebView) findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient());

        href = getIntent().getStringExtra("href");
        webview.loadUrl(href);
    }
}