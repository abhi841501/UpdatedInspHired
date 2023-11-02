package com.example.insphiredapp.EmployerActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.insphiredapp.EmployeeActivity.DashboardActivityEmployee;
import com.example.insphiredapp.FirstInterfaceApp.FirstInterfaceAppActivity;
import com.example.insphiredapp.R;

public class SplashScreenActivity extends AppCompatActivity {
    ImageView splashscreen;
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    private String strUserId, strUserType;
    private SharedPreferences sharedPreferences;
    boolean logIn;
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";

    @SuppressLint("MissingInflatedId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        splashscreen = findViewById(R.id.splashscreen);

        SharedPreferences getUserIdData = getApplication().getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
        strUserId = getUserIdData.getString("Id", "");
        strUserType = getUserIdData.getString("userType", "");
        //startSplashTimer();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (strUserId.equals("")) {
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear(); // Clear all data in the SharedPreferences file
                    editor.apply();
                    // User is not logged in, redirect to the LoginActivity
                    callLoginActivity();
                } else {
                    // User is logged in, proceed to the appropriate dashboard
                    // place this with your method to get user type

                    if (strUserType.equals("employer")) {
                        startActivity(new Intent(SplashScreenActivity.this, DashboardActivity.class));
                    } else if (strUserType.equals("employee")) {
                        startActivity(new Intent(SplashScreenActivity.this, DashboardActivityEmployee.class));
                    }

                    finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);





    }

    private void callLoginActivity() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // Clear all data in the SharedPreferences file
        editor.apply();
        Intent intent = new Intent(SplashScreenActivity.this, FirstInterfaceAppActivity.class);
        startActivity(intent);
        finish();
    }


}
