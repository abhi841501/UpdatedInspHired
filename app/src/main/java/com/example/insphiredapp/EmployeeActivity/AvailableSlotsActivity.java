package com.example.insphiredapp.EmployeeActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.insphiredapp.Adapter.AvailableSlotsAdapter;
import com.example.insphiredapp.Api_Model.GetCreateSlotsModel;
import com.example.insphiredapp.Api_Model.GetCreateSlotsModelData;
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

public class AvailableSlotsActivity extends AppCompatActivity {
AppCompatButton createBtnAvailSlots;
ImageView backArrowAvvslots;
List<GetCreateSlotsModelData> getCreateSlotsModelDataList = new ArrayList<>();
    AvailableSlotsAdapter availableSlotsAdapter;
    RecyclerView recyclerAvailableSlots;
    private  String TimeSlotId;
    private String UserId,TableName,UserIDDD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_slots);

        backArrowAvvslots = findViewById(R.id.backArrowAvvslots);
        createBtnAvailSlots = findViewById(R.id.createBtnAvailSlots);
        recyclerAvailableSlots = findViewById(R.id.recyclerAvailableSlots);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AvailableSlotsActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerAvailableSlots.setLayoutManager(layoutManager);

        backArrowAvvslots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        createBtnAvailSlots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AvailableSlotsActivity.this,CreateSlotActivity.class);
                startActivity(intent);
            }
        });
        SharedPreferences getUserIdData = getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
        UserId= getUserIdData.getString("Id", "");
        Log.e("feedback", "change" + UserId);
        GetSlotsActivityApi();
    }

    private void GetSlotsActivityApi() {

        final ProgressDialog pd = new ProgressDialog(AvailableSlotsActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<GetCreateSlotsModel> call = service.GET_CREATE_SLOTS_MODEL_CALL("show_timeslot?user_id="+UserId);

        call.enqueue(new Callback<GetCreateSlotsModel>() {
            @Override
            public void onResponse(Call<GetCreateSlotsModel> call, Response<GetCreateSlotsModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String success = response.body().getSuccess();
                        String msg = response.body().getMessage();
                        Log.e("hello", "success: " +success );

                        if (success.equals("true")|| (success.equals("True"))) {
                            GetCreateSlotsModel getCreateSlotsModel = response.body();
                            getCreateSlotsModelDataList = getCreateSlotsModel.getData();

                            TableName = getCreateSlotsModel.getTable();
                            Log.e(TableName, "onResponse:"+TableName );

                            for (int i = 0; i < getCreateSlotsModelDataList.size(); i++)
                            {
                              //  UserIDDD = String.valueOf(getCreateSlotsModelDataList.get(i).getUserId());
                                        TimeSlotId = String.valueOf(getCreateSlotsModelDataList.get(i).getId());
                                Log.e(TimeSlotId, "onResponse:"+TimeSlotId );
                                SharedPreferences getUserIdData = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = getUserIdData.edit();
                                editor.putString("UserIDDD", String.valueOf(UserIDDD));
                                editor.apply();
                                Log.e("hello11///", "getData: " +UserIDDD);

                            }
                            availableSlotsAdapter = new AvailableSlotsAdapter(AvailableSlotsActivity.this, getCreateSlotsModelDataList,TimeSlotId,TableName);
                            recyclerAvailableSlots.setAdapter(availableSlotsAdapter);


                            Log.e("hello", "getData: " );
                            // Id  = profileGetData.getId();

                          //  Toast.makeText(AvailableSlotsActivity.this, msg, Toast.LENGTH_SHORT).show();

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
            public void onFailure(Call<GetCreateSlotsModel> call, Throwable t) {
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

    @Override
    protected void onResume() {
        super.onResume();
        GetSlotsActivityApi();
    }
}