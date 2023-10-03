package com.example.insphiredapp.EmployeeActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.insphiredapp.Adapter.ChatCompanyAdapter;
import com.example.insphiredapp.Adapter.EmployerChatEmpAdapter;
import com.example.insphiredapp.Api_Model.ChatCompanyModel;
import com.example.insphiredapp.Api_Model.ChatCompanyModelData;
import com.example.insphiredapp.Api_Model.EmployerChatEmpData;
import com.example.insphiredapp.Api_Model.EmployerChatEmpModel;
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

public class ChatCompanyActivity extends AppCompatActivity {

    ImageView backArrowChatCompany;
    RecyclerView recyclerChatCompany;
    List<ChatCompanyModelData> chatCompanyModelDataList = new ArrayList<>();
    List<EmployerChatEmpData> employerChatEmpDataList = new ArrayList<>();
    EmployerChatEmpAdapter employerChatEmpAdapter;
    ChatCompanyAdapter chatCompanyAdapter;
    private String user_id,UserType,UserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_company);

        recyclerChatCompany = findViewById(R.id.recyclerChatCompany);
        backArrowChatCompany = findViewById(R.id.backArrowChatCompany);

        backArrowChatCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        SharedPreferences getUserIdData = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        user_id = getUserIdData.getString("Id", "");
        UserType= getUserIdData.getString("userType", "");
        Log.e( "helloooo", UserType+user_id);

        if (UserType.equals("employer")||UserType.equals("corporator"))
        {
            employerchatEmpApi();
        }
        else if (UserType.equals("employee"))
        {
            employeechatEmpApi();
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ChatCompanyActivity.this,LinearLayoutManager.VERTICAL,false);
        recyclerChatCompany.setLayoutManager(layoutManager);


    }

    private void  employerchatEmpApi() {
        final ProgressDialog pd = new ProgressDialog(ChatCompanyActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<EmployerChatEmpModel> call = service.EMPLOYER_CHAT_EMP_MODEL_CALL("my_booked_employer?user_id="+user_id+"&user_type="+UserType);

        call.enqueue(new Callback<EmployerChatEmpModel>() {
            @Override
            public void onResponse(Call<EmployerChatEmpModel> call, Response<EmployerChatEmpModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String success = (response.body().getSuccess());
                        String msg = (response.body().getMessage());
                        Log.e("hello", "success: " +success );

                        if (success.equals("true")|| (success.equals("True"))) {
                            employerChatEmpDataList = response.body().getData();
                            employerChatEmpAdapter = new EmployerChatEmpAdapter(employerChatEmpDataList,ChatCompanyActivity.this,UserType);
                            recyclerChatCompany.setAdapter(employerChatEmpAdapter);


                            Log.e("hello", "getData: " );
                            // Id  = profileGetData.getId();


                           // Toast.makeText(ChatCompanyActivity.this, msg, Toast.LENGTH_SHORT).show();


                            //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            // Calling another activity

                        } else {
                           // Toast.makeText(ChatCompanyActivity.this, msg, Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(ChatCompanyActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(ChatCompanyActivity.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(ChatCompanyActivity.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(ChatCompanyActivity.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(ChatCompanyActivity.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(ChatCompanyActivity.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(ChatCompanyActivity.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(ChatCompanyActivity.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(ChatCompanyActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(ChatCompanyActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<EmployerChatEmpModel> call, Throwable t) {
                Log.e("conversion issue", t.getMessage());

                if (t instanceof IOException) {
                    Toast.makeText(ChatCompanyActivity.this, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(ChatCompanyActivity.this, "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }

    private void  employeechatEmpApi() {

        final ProgressDialog pd = new ProgressDialog(ChatCompanyActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<ChatCompanyModel> call = service.CHAT_COMPANY_MODEL_CALL("all_employer?user_id="+user_id);

        call.enqueue(new Callback<ChatCompanyModel>() {
            @Override
            public void onResponse(Call<ChatCompanyModel> call, Response<ChatCompanyModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String success = (response.body().getSuccess());
                        String msg = (response.body().getMessage());
                        Log.e("hello", "success: " +success );

                        if (success.equals("true")|| (success.equals("True"))) {
                            chatCompanyModelDataList = response.body().getData();
                            chatCompanyAdapter = new ChatCompanyAdapter(ChatCompanyActivity.this,chatCompanyModelDataList,UserType);
                            recyclerChatCompany.setAdapter(chatCompanyAdapter);

                            Log.e("hello", "getData: " );
                            // Id  = profileGetData.getId();


                            //Toast.makeText(ChatCompanyActivity.this, msg, Toast.LENGTH_SHORT).show();


                            //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            // Calling another activity

                        } else {
                       //     Toast.makeText(ChatCompanyActivity.this, msg, Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(ChatCompanyActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(ChatCompanyActivity.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(ChatCompanyActivity.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(ChatCompanyActivity.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(ChatCompanyActivity.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(ChatCompanyActivity.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(ChatCompanyActivity.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(ChatCompanyActivity.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(ChatCompanyActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(ChatCompanyActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ChatCompanyModel> call, Throwable t) {
                Log.e("conversion issue", t.getMessage());

                if (t instanceof IOException) {
                    Toast.makeText(ChatCompanyActivity.this, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(ChatCompanyActivity.this, "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }
}