package com.example.insphiredapp.EmployeeActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.insphiredapp.Adapter.NonNull;
import com.example.insphiredapp.Adapter.NotificationAdapter;
import com.example.insphiredapp.Api_Model.Notification;
import com.example.insphiredapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    RecyclerView recyclerNotification;
    List<Notification> notificationList = new ArrayList<>();
    NotificationAdapter notificationAdapter;
    ImageView backArrowNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        recyclerNotification = findViewById(R.id.recyclerNotification);
        backArrowNotification = findViewById(R.id.backArrowNotification);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(NotificationActivity.this,LinearLayoutManager.VERTICAL,false);
        recyclerNotification.setLayoutManager(layoutManager);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        System.out.println("Token"+token);
                    }
                });
        backArrowNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }







    }
