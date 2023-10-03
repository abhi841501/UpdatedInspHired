package com.example.insphiredapp.EmployeeActivity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.insphiredapp.R;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class CVWebViewActivity extends AppCompatActivity {
    WebView webViewCV;
    private String UserId,finalUrl;
    ImageView web_backArrowCSlots;
    private ValueCallback<Uri[]> afterLollipop;
    private ValueCallback<Uri> mUploadMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cvweb_view);
        webViewCV = findViewById(R.id.webViewCV);
        web_backArrowCSlots = findViewById(R.id.web_backArrowCSlots);

        SharedPreferences getUserIdData = getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
        UserId = getUserIdData.getString("Id", "");

        web_backArrowCSlots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        WebSettings webSettings = webViewCV.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webViewCV.setWebViewClient(new WebViewClient());
        webViewCV.getSettings().setLoadsImagesAutomatically(true);
        webViewCV.getSettings().setJavaScriptEnabled(true);
        webViewCV.getSettings().setLoadWithOverviewMode(true);
        webViewCV.getSettings().setUseWideViewPort(true);
        webViewCV.setClickable(true);
        //zoom
        webViewCV.getSettings().setBuiltInZoomControls(true);
        webViewCV.getSettings().setAllowContentAccess(true);
        webViewCV.getSettings().setDomStorageEnabled(true);

        webViewCV.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        webViewCV.getSettings().setAllowFileAccessFromFileURLs(true);
        webViewCV.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webViewCV.setWebChromeClient(new WebChromeClient() {
            // Handle file selection
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                // Implement file selection logic here
                return true;
            }
        });
        // webView.getSettings().setAppCacheEnabled(true);
        webViewCV.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        webViewCV.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView wv, String url) {
                if (url.startsWith("tel:") || url.startsWith("whatsapp:")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                    webViewCV.goBack();
                    return true;
                }
                return false;
            }
        });

        String Id = UserId; // Replace with the actual user ID
        byte[] data = UserId.getBytes(StandardCharsets.UTF_8);
        String stringBase = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            stringBase = Base64.getEncoder().encodeToString(data);
        }
        String url = "https://itdevelopmentservices.com/insphire/cv_upload?user_id=" + stringBase; // Replace with your URL
        webViewCV.loadUrl(url);


        webViewCV.setWebChromeClient(new WebChromeClient() {

            // For Android 3.0+ - undocumented method
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {

                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                startActivityForResult(Intent.createChooser(i, "File Chooser"), 101);
                Log.i("DEBUG", "Open file Chooser");
                mUploadMessage = uploadMsg;
            }

            // For Android > 4.1 - undocumented method
            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                mUploadMessage = uploadMsg;
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 101);

            }

            // For Android > 5.0
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                afterLollipop = filePathCallback;
                startActivityForResult(fileChooserParams.createIntent(), 101);
                return true;

            }

        });


    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case 101:
                if (resultCode == RESULT_OK) {

                    Uri result = intent == null || resultCode != RESULT_OK ? null
                            : intent.getData();
                    if (mUploadMessage != null) {
                        mUploadMessage.onReceiveValue(result);
                    } else if (afterLollipop != null) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            afterLollipop.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent));
                            afterLollipop = null;
                        }
                    }
                    mUploadMessage = null;
                }
        }

    }
}


