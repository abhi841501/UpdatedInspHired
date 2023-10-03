package com.example.insphiredapp.EmployerActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.insphiredapp.R;

public class SettingsActivity extends AppCompatActivity {

    ImageView backArrowSettings;
    LinearLayout linearChangePassword,linearLogoutEmployr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        backArrowSettings = findViewById(R.id.backArrowSettings);
        linearChangePassword = findViewById(R.id.linearChangePassword);
        linearLogoutEmployr = findViewById(R.id.linearLogoutEmployr);

        backArrowSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        linearChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this,ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        linearLogoutEmployr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog logoutDialog  = new Dialog(SettingsActivity.this);
                logoutDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                logoutDialog.setContentView(R.layout.logout_dialog_setting);
                LinearLayout noDialogSetting = logoutDialog.findViewById(R.id.noDialogSetting);
                LinearLayout yesDialogSetting = logoutDialog.findViewById(R.id.yesDialogSetting);
                logoutDialog.show();
                Window window = logoutDialog.getWindow();
                if (window == null) return;
                WindowManager.LayoutParams params = window.getAttributes();
                params.width = 850;
                params.height = 350;
                window.setAttributes(params);
                //    dialog.getWindow().setLayout(100, 100);
                logoutDialog.getWindow().setBackgroundDrawableResource(R.drawable.withdrawpopupcard);


                yesDialogSetting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("finish", true);
                        startActivity(intent);

                        Toast.makeText(SettingsActivity.this, "User logout successfully", Toast.LENGTH_SHORT).show();
                        logoutDialog.dismiss();
                    }
                });


                noDialogSetting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logoutDialog.dismiss();
                    }
                });
            }
        });
    }
}