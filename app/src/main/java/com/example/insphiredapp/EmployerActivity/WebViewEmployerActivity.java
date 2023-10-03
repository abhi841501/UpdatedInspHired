package com.example.insphiredapp.EmployerActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.insphiredapp.R;

public class WebViewEmployerActivity extends AppCompatActivity {
    WebView webView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_employer);

        webView1 = findViewById(R.id.webView1);
        String finalUrl =("https://itdevelopmentservices.com/insphire/term_condition");
        webView1.loadUrl(finalUrl);
        webView1.setWebViewClient(new WebViewClient());
        webView1.getSettings().setLoadsImagesAutomatically(true);
        webView1.getSettings().setJavaScriptEnabled(true);
        webView1.getSettings().setLoadWithOverviewMode(true);
        webView1.getSettings().setUseWideViewPort(true);
        //zoom
        webView1.getSettings().setBuiltInZoomControls(true);
        webView1.getSettings().setAllowContentAccess(true);
        webView1.getSettings().setDomStorageEnabled(true);
        // webView.getSettings().setAppCacheEnabled(true);
        webView1.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        webView1.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView wv, String url) {
                if(url.startsWith("tel:") || url.startsWith("whatsapp:")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                    webView1.goBack();
                    return true;
                }
                return false;
            }
        });


    }
    }