package com.example.insphiredapp.EmployeeActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.insphiredapp.R;

public class JobDiscriptionActivity extends AppCompatActivity {
    ImageView backArrowJobDesp;
    AppCompatButton applyNowBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_discription);

        backArrowJobDesp = findViewById(R.id.backArrowJobDesp);
        applyNowBtn = findViewById(R.id.applyNowBtn);

        backArrowJobDesp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        applyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(JobDiscriptionActivity.this, "Job Applied Successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}