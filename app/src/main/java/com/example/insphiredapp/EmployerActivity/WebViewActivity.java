package com.example.insphiredapp.EmployerActivity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.insphiredapp.R;

public class WebViewActivity extends AppCompatActivity {
    WebView webView;
    private String UserId, UserType;
    private String employeeId, slotid, totalAmount,PO_number;
    private boolean shouldLoadHomeFragOnBackPress = true;
    private ValueCallback<Uri[]> afterLollipop;
    private ValueCallback<Uri> mUploadMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        SharedPreferences getUserIdData = getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
        UserId = getUserIdData.getString("Id", "");
        UserType = getUserIdData.getString("userType", "");
        webView = findViewById(R.id.webView);
        employeeId = getIntent().getStringExtra("strEmpid");
        totalAmount = getIntent().getStringExtra("strAmount");
        slotid = getIntent().getStringExtra("SlotId");
        PO_number = getIntent().getStringExtra("StrGeneratePOTxt");
        Log.e("get","UserType  "+UserType );
        Log.e("get","employeeId  "+employeeId );
        Log.e("get","totalAmount  "+totalAmount );
        Log.e("get","UserId  "+UserId );
        Log.e("get","slotid  "+slotid );
        Log.e("get","PO_number  "+PO_number );


        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setClickable(true);
        //zoom
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setDomStorageEnabled(true);

        webView.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webView.setWebChromeClient(new WebChromeClient() {
            // Handle file selection
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                // Implement file selection logic here
                return true;
            }
        });
        // webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView wv, String url) {
                if (url.startsWith("tel:") || url.startsWith("whatsapp:")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                    webView.goBack();
                    return true;
                }
                return false;
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {

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


        // String finalUrl ="http://192.168.1.17/insphire/card_payment?";
         if (UserType.equals("employer"))
         {
             String finalUrl = "https://itdevelopmentservices.com/insphire/card_payment?" + "employee_id=" + employeeId + "&employer_id=" + UserId + "&amount="+
                     totalAmount + "&time_slot_id=" + slotid + "&user_type=" + UserType;

             webView.loadUrl(finalUrl);
             Log.e("token", "finalUrl: "+ finalUrl);
         }

         else
         {
             String finalUrl = "https://itdevelopmentservices.com/insphire/invoice?"+"employee_id="+employeeId +"&employer_id="+UserId+"&time_slot_id=" + slotid+"&user_type="+UserType+"&amount="+
                 totalAmount+"&po_number="+PO_number;
             webView.loadUrl(finalUrl);

             Log.e("djsa","Url "+finalUrl );
         }




    }

    @Override
    public void onBackPressed() {
        if (shouldLoadHomeFragOnBackPress) {
            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
            startActivity(intent);
        }
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