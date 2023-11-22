package com.example.insphiredapp.EmployerActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.insphiredapp.R;

public class EmployerWebViewActivity extends AppCompatActivity {
    WebView EmpwebView;
    private String UserId, UserType;
    private String employeeId, slotid, totalAmount,PO_number;
    private boolean shouldLoadHomeFragOnBackPress = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_web_view);


        SharedPreferences getUserIdData = getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
        UserId = getUserIdData.getString("Id", "");
        UserType = getUserIdData.getString("userType", "");
        EmpwebView = findViewById(R.id.EmpwebView);
        employeeId = getIntent().getStringExtra("strEmpid");
        totalAmount = getIntent().getStringExtra("strAmount");
        slotid = getIntent().getStringExtra("SlotId");
        PO_number = getIntent().getStringExtra("StrGeneratePOTxt");

        WebSettings webSettings = EmpwebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        EmpwebView.setWebViewClient(new WebViewClient());
        EmpwebView.getSettings().setLoadsImagesAutomatically(true);
        EmpwebView.getSettings().setJavaScriptEnabled(true);
        EmpwebView.getSettings().setLoadWithOverviewMode(true);
        EmpwebView.getSettings().setUseWideViewPort(true);
        EmpwebView.setClickable(true);
        //zoom
        EmpwebView.getSettings().setBuiltInZoomControls(true);
        EmpwebView.getSettings().setAllowContentAccess(true);
        EmpwebView.getSettings().setDomStorageEnabled(true);

        if (UserType.equals("employer"))
        {
            String finalUrl = "https://itdevelopmentservices.com/insphire/card_payment?" + "employee_id=" + employeeId + "&employer_id=" + UserId + "&amount="+
                    totalAmount + "&time_slot_id=" + slotid + "&user_type=" + UserType;

            EmpwebView.loadUrl(finalUrl);
            Log.e("token", "finalUrl: "+ finalUrl);
        }
    }
}