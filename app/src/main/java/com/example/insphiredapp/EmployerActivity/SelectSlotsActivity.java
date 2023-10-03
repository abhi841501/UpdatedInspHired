package com.example.insphiredapp.EmployerActivity;

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

import com.example.insphiredapp.Adapter.SelectSlotsAdapter;
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

public class SelectSlotsActivity extends AppCompatActivity implements ItemClickListener {
    ImageView backArrowAS;
    List<GetCreateSlotsModelData> getCreateSlotsModelDataList = new ArrayList<>();
    SelectSlotsAdapter selectSlotsAdapter;
    RecyclerView recyclerSlots;
    AppCompatButton BookBtnSlotsSelect;
    private String UserId, EmployeeId, status,statusCheck="",statusCheck1="";
    boolean bool = false;
     private String slId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_slots);

        backArrowAS = findViewById(R.id.backArrowAS);
        recyclerSlots = findViewById(R.id.recyclerSlots);
        BookBtnSlotsSelect = findViewById(R.id.BookBtnSlotsSelect);

        backArrowAS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        EmployeeId = getIntent().getStringExtra("EmpId");
        status = getIntent().getStringExtra("status");
        SharedPreferences getUserIdData = getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
        UserId = getUserIdData.getString("UserIDDD", "");
        Log.e("feedback", "EmployeeId" + EmployeeId);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SelectSlotsActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerSlots.setLayoutManager(layoutManager);

        GetSlotsActivityApi();
        BookBtnSlotsSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  statusCheck = getUserIdData.getString("statusCheck", "");
                statusCheck1 = getUserIdData.getString("statusCheck1", "");
                status = getIntent().getStringExtra("status");
                Log.e("trhrw","provides....." +   statusCheck );
                Log.e("trhrw","provides....." +   statusCheck1 );

                if (statusCheck.equals("2")||statusCheck1.equals("4")) {
                    statusCheck="1";
                    statusCheck1="3";

                    Intent i = new Intent(SelectSlotsActivity.this, BookingActivity.class);
                    i.putExtra("EmpId", EmployeeId);
                    i.putExtra("SlotId", SlotId);
                    startActivity(i);
                }else if(statusCheck.equals("1")|| statusCheck1.equals("3")){

                    Toast.makeText(SelectSlotsActivity.this, "Please Select Time Slot!", Toast.LENGTH_SHORT).show();
                }*/
               // someAction();
                if (slId==null)
                {



                    Toast.makeText(SelectSlotsActivity.this, "Please Select Time Slot!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent i = new Intent(SelectSlotsActivity.this, BookingActivity.class);
                    i.putExtra("EmpId", EmployeeId);
                    i.putExtra("TimeSlot", slId);
                    Log.e("PAG", "onClick: "+slId );
                    startActivity(i);
                }
            }
        });


    }

    private void GetSlotsActivityApi() {

        final ProgressDialog pd = new ProgressDialog(SelectSlotsActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<GetCreateSlotsModel> call = service.GET_CREATE_SLOTS_MODEL_CALL("show_timeslot?user_id="+EmployeeId);

        call.enqueue(new Callback<GetCreateSlotsModel>() {
            @Override
            public void onResponse(Call<GetCreateSlotsModel> call, Response<GetCreateSlotsModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String success = response.body().getSuccess();
                        String msg = response.body().getMessage();
                        Log.e("hello", "success: " + success);

                        if (success.equals("true") || (success.equals("True"))) {
                            getCreateSlotsModelDataList = response.body().getData();

                            for (int i = 0; i < getCreateSlotsModelDataList.size(); i++) {
                            }
                            selectSlotsAdapter = new SelectSlotsAdapter(SelectSlotsActivity.this, SelectSlotsActivity.this,getCreateSlotsModelDataList);
                            recyclerSlots.setAdapter(selectSlotsAdapter);
                            selectSlotsAdapter.notifyDataSetChanged();

                            SharedPreferences getUserIdData = getApplicationContext().getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = getUserIdData.edit();
                            editor.putString("ischecked", "1");
                            editor.apply();


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
    public void onClick(int position, String id) {
        recyclerSlots.post(new Runnable() {
            @Override public void run()
            {
                selectSlotsAdapter.notifyDataSetChanged();
            }
        });
        slId = id;
        Log.e("idSlot","id...." +id);

    }
}