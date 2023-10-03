package com.example.insphiredapp.EmployerActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.insphiredapp.EmployeeActivity.DashboardActivityEmployee;
import com.example.insphiredapp.R;

public class SplashScreenActivity extends AppCompatActivity {
    ImageView splashscreen;
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    private String strUserId,strUserType;
    private SharedPreferences sharedPreferences;
    @SuppressLint("MissingInflatedId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        splashscreen=findViewById(R.id.splashscreen);
        //startSplashTimer();
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

               // callActivity();
            }
        }, SPLASH_DISPLAY_LENGTH);

        SharedPreferences getUserIdData = getApplication().getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
        strUserId = getUserIdData.getString("Id", "");
        strUserType = getUserIdData.getString("userType", "");
        if (strUserType.equals("")) {

            Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else if (strUserType.equals("employer")) {
            Intent intent = new Intent(SplashScreenActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();

        }
        else if (strUserType.equals("employee"))
        {
            Intent intent = new Intent(SplashScreenActivity.this, DashboardActivityEmployee.class);
            startActivity(intent);
            finish();
        }
    }

    private void callActivity() {
        Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


}
