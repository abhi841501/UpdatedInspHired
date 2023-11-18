package com.example.insphiredapp.EmployeeActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.insphiredapp.Api_Model.AcceptNotificationModel;
import com.example.insphiredapp.Api_Model.ExternalNotificationModel;
import com.example.insphiredapp.Api_Model.ExternalNotificationModelData;
import com.example.insphiredapp.Api_Model.NotificationCData;
import com.example.insphiredapp.Api_Model.RejectModel;
import com.example.insphiredapp.Api_Model.RejectModelData;
import com.example.insphiredapp.R;
import com.example.insphiredapp.databinding.ActivityNotificationConfirmationBinding;
import com.example.insphiredapp.retrofit.Api;
import com.example.insphiredapp.retrofit.Api_Client;

import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

;

public class NotificationConfirmationActivity extends AppCompatActivity {
    ActivityNotificationConfirmationBinding binding;
    private String NotificationId,type;
    NotificationCData notificationCData;
    private String StrstartDate,StrendDate,imgg,UserId,UserType,StremployerName,StrMessage,StrSubject;
    RejectModelData rejectModelData;
    ExternalNotificationModelData externalNotificationModelData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationConfirmationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NotificationId = getIntent().getStringExtra("notificationId");
        type = getIntent().getStringExtra("type");


        SharedPreferences getUserIdData = getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
        UserId= getUserIdData.getString("Id", "");
        UserType= getUserIdData.getString("userType", "");
        Log.e("feedback", "change" + UserId);

        externalNotiApi();
        //imgg = getIntent().getStringExtra("img");

        //Glide.with(getApplicationContext()).load(Api_Client.BASE_URL_IMAGES+imgg).placeholder(R.drawable.employee).into(binding.imgCpny);


        binding.acceptBtnNotificationnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             AcceptNotificationApi();
            }
        });


        binding.RejectBtnNotificationn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RejectNotificationApi();
            }
        });
    }


    private void  externalNotiApi() {

        final ProgressDialog pd = new ProgressDialog(NotificationConfirmationActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<ExternalNotificationModel> call = service.EXTERNAL_NOTIFICATION_MODEL_CALL("external_notification?noti_id="+NotificationId);

        call.enqueue(new Callback<ExternalNotificationModel>() {
            @Override
            public void onResponse(Call<ExternalNotificationModel> call, Response<ExternalNotificationModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String success = (response.body().getSuccess());
                        String msg = (response.body().getMessage());

                        if (success.equals("true")|| (success.equals("True"))) {
                             externalNotificationModelData = response.body().getData();

                            StremployerName = externalNotificationModelData.getCompanyName();
                            StrstartDate = externalNotificationModelData.getStartDate();
                            StrendDate = String.valueOf(externalNotificationModelData.getEndDate());
                            Glide.with(NotificationConfirmationActivity.this).load(Api_Client.BASE_URL_IMAGES1 + externalNotificationModelData.getCompanyImage()).placeholder(R.drawable.employee).into(binding.imgCpny);
                            binding.title.setText(StremployerName);
                            binding.startDateCNotii.setText(StrstartDate);
                            binding.endDateGetCNoti.setText(StrendDate);


                            Log.e("hello", "getData: " );
                            // Id  = profileGetData.getId();


                        } else {
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(getApplicationContext(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(getApplicationContext(), "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(getApplicationContext(), "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(getApplicationContext(), "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(getApplicationContext(), "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(getApplicationContext(), "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(getApplicationContext(), "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(getApplicationContext(), "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(getApplicationContext(), "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ExternalNotificationModel> call, Throwable t) {
                Log.e("conversion issue", t.getMessage());

                if (t instanceof IOException) {
                    Toast.makeText(getApplicationContext(), "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(getApplicationContext(), "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }
    private void  RejectNotificationApi() {
        final ProgressDialog pd = new ProgressDialog(NotificationConfirmationActivity.this);
        pd.setCancelable(false);
        pd.show();
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<RejectModel> call = service.REJECT_MODEL_CALL(NotificationId);

        call.enqueue(new Callback<RejectModel>() {
            @Override
            public void onResponse(retrofit2.Call<RejectModel> call, Response<RejectModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        RejectModel rejectModel = response.body();
                        String success = rejectModel.getSuccess();
                        String msg = rejectModel.getMessage();
                        Log.e("hello", "success: " +success );

                        if (success.equals("true") || success.equals("True")) {
                            rejectModelData = rejectModel.getData();
                            Toast.makeText(NotificationConfirmationActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Log.e("hello", "getData: " );

                        } else {
                            Toast.makeText(NotificationConfirmationActivity.this, msg, Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(NotificationConfirmationActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(NotificationConfirmationActivity.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(NotificationConfirmationActivity.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(NotificationConfirmationActivity.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(NotificationConfirmationActivity.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(NotificationConfirmationActivity.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(NotificationConfirmationActivity.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(NotificationConfirmationActivity.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(NotificationConfirmationActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(NotificationConfirmationActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<RejectModel> call, Throwable t) {
                Log.e("conversion issue", t.getMessage());

                if (t instanceof IOException) {
                    Toast.makeText(NotificationConfirmationActivity.this, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(NotificationConfirmationActivity.this, "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }

        });


    }


    private void  AcceptNotificationApi() {
        final ProgressDialog pd = new ProgressDialog(NotificationConfirmationActivity.this);
        pd.setCancelable(false);
        pd.show();
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<AcceptNotificationModel> call = service.ACCEPT_NOTIFICATION_MODEL_CALL(NotificationId,UserId);

        call.enqueue(new Callback<AcceptNotificationModel>() {
            @Override
            public void onResponse(retrofit2.Call<AcceptNotificationModel> call, Response<AcceptNotificationModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        AcceptNotificationModel acceptNotificationModel = response.body();
                        String success = acceptNotificationModel.getSuccess();
                        String msg = acceptNotificationModel.getMessage();
                        Log.e("hello", "success: " +success );

                        if (success.equals("true") || success.equals("True")) {
                            notificationCData = acceptNotificationModel.getData();
                            Toast.makeText(NotificationConfirmationActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Log.e("hello", "getData: " );

                        } else {
                            Toast.makeText(NotificationConfirmationActivity.this, msg, Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(NotificationConfirmationActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(NotificationConfirmationActivity.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(NotificationConfirmationActivity.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(NotificationConfirmationActivity.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(NotificationConfirmationActivity.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(NotificationConfirmationActivity.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(NotificationConfirmationActivity.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(NotificationConfirmationActivity.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(NotificationConfirmationActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(NotificationConfirmationActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<AcceptNotificationModel> call, Throwable t) {
                Log.e("conversion issue", t.getMessage());

                if (t instanceof IOException) {
                    Toast.makeText(NotificationConfirmationActivity.this, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(NotificationConfirmationActivity.this, "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }

        });


    }
}