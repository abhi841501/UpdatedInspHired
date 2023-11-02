package com.example.insphiredapp.EmployerActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.example.insphiredapp.R;

public class IntroductionVideoActivity extends AppCompatActivity {
    ImageView backArrowIntroVideo;
    WebView webViewVideo;
    private boolean shouldLoadHomeFragOnBackPress = true;
     private String SeeVideoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction_video);

        SeeVideoId = getIntent().getStringExtra("Id");
        backArrowIntroVideo = findViewById(R.id.backArrowIntroVideo);
        webViewVideo = findViewById(R.id.webViewVideo);
        webViewVideo.getSettings().setJavaScriptEnabled(true);
        webViewVideo.getSettings().setPluginState(WebSettings.PluginState.ON);
        webViewVideo.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webViewVideo.getSettings().setSupportMultipleWindows(true);
        webViewVideo.getSettings().setSupportZoom(true);
        webViewVideo.getSettings().setBuiltInZoomControls(true);
        webViewVideo.getSettings().setAllowFileAccess(true);
        webViewVideo.getSettings().setAllowContentAccess(true);
        webViewVideo.getSettings().setDomStorageEnabled(true);

        backArrowIntroVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String finalUrl = "https://itdevelopmentservices.com/insphire/user_intro_web/"+SeeVideoId;

        webViewVideo.loadUrl(finalUrl);
    }
    @Override
    public void onBackPressed() {
        if (shouldLoadHomeFragOnBackPress) {
            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
            startActivity(intent);
        }
    }
}