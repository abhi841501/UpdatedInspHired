package com.example.insphiredapp.FirstInterfaceApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.insphiredapp.EmployerActivity.RegisterActivity;
import com.example.insphiredapp.R;

public class EmployerJobPostActivity extends AppCompatActivity {
    private ImageView imgbackEmployer;
    private LinearLayout linearTemporaryJobPost,linearPermanentJPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_job_post);

        imgbackEmployer = findViewById(R.id.imgbackEmployer);
        linearTemporaryJobPost = findViewById(R.id.linearTemporaryJobPost);
        linearPermanentJPost = findViewById(R.id.linearPermanentJPost);

        imgbackEmployer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        linearTemporaryJobPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployerJobPostActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        linearPermanentJPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://itdevelopmentservices.com/insphire/"));
                startActivity(browserIntent);
            }
        });

    }
}