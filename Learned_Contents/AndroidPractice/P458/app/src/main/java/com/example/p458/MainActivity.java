package com.example.p458;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String permissions[] = {

        };
    }

    public void ckbt(View v){
        if(v.getId() == R.id.button){
            webView.loadUrl("http://m.naver.com");
        }else if(v.getId() == R.id.button2){
            webView.loadUrl("http://www.daum.net");
        }else if(v.getId() == R.id.button3){
            webView.loadUrl("http://192.168.0.8/android");
        }
    }
}