package com.example.insphiredapp.EmployeeActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.insphiredapp.Adapter.EmployeeHistoryAdapter;
import com.example.insphiredapp.Adapter.NonNull;
import com.example.insphiredapp.Adapter.NotificationAdapter;
import com.example.insphiredapp.Api_Model.EmployeeHistoryModel;
import com.example.insphiredapp.Api_Model.Notification;
import com.example.insphiredapp.Api_Model.NotificationModel;
import com.example.insphiredapp.Api_Model.NotificationModelData;
import com.example.insphiredapp.R;
import com.example.insphiredapp.retrofit.Api;
import com.example.insphiredapp.retrofit.Api_Client;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {

    RecyclerView recyclerNotification;
    List<NotificationModelData> notificationModelDataList = new ArrayList<>();
    NotificationAdapter notificationAdapter;
    ImageView backArrowNotification;
    private String UserId,UserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        recyclerNotification = findViewById(R.id.recyclerNotification);
        backArrowNotification = findViewById(R.id.backArrowNotification);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(NotificationActivity.this,LinearLayoutManager.VERTICAL,false);
        recyclerNotification.setLayoutManager(layoutManager);

        SharedPreferences getUserIdData = getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
        UserId= getUserIdData.getString("Id", "");
        UserType= getUserIdData.getString("userType", "");
        Log.e("feedback", "change" + UserId);
        getNotificationApi();

/*

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
                });*/
        backArrowNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void  getNotificationApi() {
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<NotificationModel> call = service.NOTIFICATION_MODEL_CALL("notification?user_id="+UserId+"&user_type="+UserType);

        call.enqueue(new Callback<NotificationModel>() {
            @Override
            public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        NotificationModel notificationModel = response.body();
                        String success = notificationModel.getSuccess();
                        String msg =notificationModel.getMessage();
                        Log.e("hello", "success: " +success );


                        if (success.equals("true")|| (success.equals("True"))) {
                            notificationModelDataList = notificationModel.getData();
                            notificationAdapter = new NotificationAdapter(NotificationActivity.this,notificationModelDataList);
                            recyclerNotification.setAdapter(notificationAdapter);

                           // Toast.makeText(NotificationActivity.this, msg, Toast.LENGTH_SHORT).show();


                            //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            // Calling another activity

                        } else {
                            Toast.makeText(NotificationActivity.this, msg, Toast.LENGTH_LONG).show();
                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(NotificationActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(NotificationActivity.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(NotificationActivity.this ,"Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(NotificationActivity.this,"The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(NotificationActivity.this,"Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(NotificationActivity.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(NotificationActivity.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(NotificationActivity.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(NotificationActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(NotificationActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<NotificationModel> call, Throwable t) {
                Log.e("conversion issue", t.getMessage());

                if (t instanceof IOException) {
                    Toast.makeText(NotificationActivity.this, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(NotificationActivity.this, "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


}
