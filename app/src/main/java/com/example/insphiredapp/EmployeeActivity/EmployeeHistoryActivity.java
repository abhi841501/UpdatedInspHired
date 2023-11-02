package com.example.insphiredapp.EmployeeActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.insphiredapp.Adapter.CancelHistoryAdapter;
import com.example.insphiredapp.Adapter.EmployeeHistoryAdapter;
import com.example.insphiredapp.Api_Model.CancelEmployeeHistoryList;
import com.example.insphiredapp.Api_Model.CancelEmployeeHistoryModel;
import com.example.insphiredapp.Api_Model.EmployeeHistoryCompleteList;
import com.example.insphiredapp.Api_Model.EmployeeHistoryModel;
import com.example.insphiredapp.R;
import com.example.insphiredapp.retrofit.Api;
import com.example.insphiredapp.retrofit.Api_Client;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeHistoryActivity extends AppCompatActivity {
    ImageView backArrowAppliedJobs;
    List<EmployeeHistoryCompleteList> employeeHistoryCompleteLists = new ArrayList<>();
    EmployeeHistoryAdapter employeeHistoryAdapter;
    List<CancelEmployeeHistoryList> cancelEmployeeHistoryList;
    CancelHistoryAdapter cancelHistoryAdapter;
    RecyclerView recyclerAppliedJobs;
    TextView complete,Cancelled;
    private String UserId,UserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_history);

        backArrowAppliedJobs = findViewById(R.id.backArrowAppliedJobs);
        recyclerAppliedJobs = findViewById(R.id.recyclerAppliedJobs);
        complete = findViewById(R.id.complete);
        Cancelled = findViewById(R.id.Cancelled);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(EmployeeHistoryActivity.this,LinearLayoutManager.VERTICAL,false);
        recyclerAppliedJobs.setLayoutManager(layoutManager);

        SharedPreferences getUserIdData = getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
        UserId= getUserIdData.getString("Id", "");
        UserType = getUserIdData.getString("UserType1", "");

        Log.e("feedback", "change" + UserId);


        backArrowAppliedJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        CompletedEmployeeHistoryApi();


        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                complete.setBackground(EmployeeHistoryActivity.this.getResources().getDrawable(R.drawable.app));
                Cancelled.setBackground(EmployeeHistoryActivity.this.getResources().getDrawable(R.drawable.cpmplete));
                complete.setTextColor(Color.parseColor("#FFFFFF"));
                Cancelled.setTextColor(Color.parseColor("#000000"));

                CompletedEmployeeHistoryApi();

            }
        });

        Cancelled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                complete.setBackground(EmployeeHistoryActivity.this.getResources().getDrawable(R.drawable.cpmplete));
                Cancelled.setBackground(EmployeeHistoryActivity.this.getResources().getDrawable(R.drawable.app));
                Cancelled.setTextColor(Color.parseColor("#FFFFFF"));
                complete.setTextColor(Color.parseColor("#000000"));
                CancelledHistoryApi();

            }
        });
    }

    private void  CancelledHistoryApi() {
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<CancelEmployeeHistoryModel> call = service.CANCEL_EMPLOYEE_HISTORY_MODEL_CALL("employee_job_history?user_id="+UserId+"&switch_id="+1+"&user_type="+UserType);


        call.enqueue(new Callback<CancelEmployeeHistoryModel>() {
            @Override
            public void onResponse(Call<CancelEmployeeHistoryModel> call, Response<CancelEmployeeHistoryModel> response) {
                //pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        CancelEmployeeHistoryModel cancelEmployeeHistoryModel = response.body();
                        String success = cancelEmployeeHistoryModel.getSuccess();
                        String msg =cancelEmployeeHistoryModel.getMessage();
                        Log.e("hello", "success: " +success );


                        if (success.equals("true")|| (success.equals("True"))) {
                            cancelEmployeeHistoryList = cancelEmployeeHistoryModel.getCancel();
                            cancelHistoryAdapter = new CancelHistoryAdapter(EmployeeHistoryActivity.this,cancelEmployeeHistoryList,UserType);
                            recyclerAppliedJobs.setAdapter(cancelHistoryAdapter);

                            //  Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();


                            //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            // Calling another activity

                        } else {
                            Toast.makeText(EmployeeHistoryActivity.this, msg, Toast.LENGTH_LONG).show();
                           // pd.dismiss();
                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(EmployeeHistoryActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(EmployeeHistoryActivity.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(EmployeeHistoryActivity.this ,"Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(EmployeeHistoryActivity.this,"The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(EmployeeHistoryActivity.this,"Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(EmployeeHistoryActivity.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(EmployeeHistoryActivity.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(EmployeeHistoryActivity.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(EmployeeHistoryActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(EmployeeHistoryActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CancelEmployeeHistoryModel> call, Throwable t) {
                Log.e("conversion issue", t.getMessage());

                if (t instanceof IOException) {
                    Toast.makeText(EmployeeHistoryActivity.this, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                  //  pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(EmployeeHistoryActivity.this, "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                  //  pd.dismiss();
                }
            }
        });


    }

    private void  CompletedEmployeeHistoryApi() {
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<EmployeeHistoryModel> call = service.EMPLOYEE_HISTORY_MODEL_CALL("employee_job_history?user_id="+UserId+"&switch_id="+0+"&user_type="+UserType);

        call.enqueue(new Callback<EmployeeHistoryModel>() {
            @Override
            public void onResponse(Call<EmployeeHistoryModel> call, Response<EmployeeHistoryModel> response) {
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        EmployeeHistoryModel employeeHistoryModel = response.body();
                        String success = employeeHistoryModel.getSuccess();
                        String msg =employeeHistoryModel.getMessage();
                        Log.e("hello", "success: " +success );


                        if (success.equals("true")|| (success.equals("True"))) {
                            employeeHistoryCompleteLists = employeeHistoryModel.getComplete();
                            employeeHistoryAdapter = new EmployeeHistoryAdapter(EmployeeHistoryActivity.this,employeeHistoryCompleteLists,UserType);
                            recyclerAppliedJobs.setAdapter(employeeHistoryAdapter);

                            //  Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();


                            //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            // Calling another activity

                        } else {
                            Toast.makeText(EmployeeHistoryActivity.this, msg, Toast.LENGTH_LONG).show();
                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(EmployeeHistoryActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(EmployeeHistoryActivity.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(EmployeeHistoryActivity.this ,"Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(EmployeeHistoryActivity.this,"The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(EmployeeHistoryActivity.this,"Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(EmployeeHistoryActivity.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(EmployeeHistoryActivity.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(EmployeeHistoryActivity.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(EmployeeHistoryActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(EmployeeHistoryActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<EmployeeHistoryModel> call, Throwable t) {
                Log.e("conversion issue", t.getMessage());

                if (t instanceof IOException) {
                    Toast.makeText(EmployeeHistoryActivity.this, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(EmployeeHistoryActivity.this, "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}