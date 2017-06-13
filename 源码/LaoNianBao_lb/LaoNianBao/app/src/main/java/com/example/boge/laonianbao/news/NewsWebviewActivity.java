package com.example.boge.laonianbao.news;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.boge.laonianbao.R;

public class NewsWebviewActivity extends AppCompatActivity {
    private WebView webview;
    private String href;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newswebview);
        webview = (WebView) findViewById(R.id.webviewNews);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient());

        href = getIntent().getStringExtra("href");
        href = href.substring(23);
        webview.loadUrl(href);
    }
}
