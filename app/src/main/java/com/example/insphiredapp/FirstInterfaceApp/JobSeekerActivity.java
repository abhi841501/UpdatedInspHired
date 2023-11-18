package com.example.insphiredapp.FirstInterfaceApp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.insphiredapp.EmployerActivity.RegisterActivity;
import com.example.insphiredapp.R;

public class JobSeekerActivity extends AppCompatActivity {
    private ImageView imgbackJobseeker;
    LinearLayout linearTempraryUnique,linearPermanentUnique;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_seeker);
        imgbackJobseeker = findViewById(R.id.imgbackJobseeker);
        linearTempraryUnique = findViewById(R.id.linearTempraryUnique);
        linearPermanentUnique = findViewById(R.id.linearPermanentUnique);


        imgbackJobseeker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        linearTempraryUnique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobSeekerActivity.this, RegisterActivity.class);
                intent.putExtra("TempEmployer","jobseeker");
                startActivity(intent);
            }
        });

        linearPermanentUnique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://itdevelopmentservices.com/insphire/"));
                startActivity(browserIntent);
            }
        });


    }
}