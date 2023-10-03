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
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.insphiredapp.Adapter.MyJobAdapter;
import com.example.insphiredapp.Api_Model.MyJobModel;
import com.example.insphiredapp.Api_Model.MyJobModelData;
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

public class MyJobActivity extends AppCompatActivity {
    RecyclerView recyclerApplyJob;
    List<MyJobModelData> myJobModelDataList = new ArrayList<>();
    MyJobAdapter myJobAdapter;
    ImageView backArrowFindJobs;
    private String user_id,BookingId,UserType;
    private SearchView searchQueryMyJobs;
    private String is_corporator;
    List<MyJobModelData> myJobModelDataListBackUp = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_job);

        recyclerApplyJob = findViewById(R.id.recyclerApplyJob);
        backArrowFindJobs = findViewById(R.id.backArrowFindJobs);
        searchQueryMyJobs = findViewById(R.id.searchQueryMyJobs);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MyJobActivity.this,LinearLayoutManager.VERTICAL,false);
        recyclerApplyJob.setLayoutManager(layoutManager);
        searchQueryMyJobs.setIconified(false);
        searchQueryMyJobs.clearFocus();
        searchQueryMyJobs.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                myJobAdapter.getFilter().filter(newText);
                return false;
            }
        });


        backArrowFindJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SharedPreferences getUserIdData = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        user_id = getUserIdData.getString("Id", "");
        UserType = getUserIdData.getString("userType", "");


        GetMyJobApi();

    }

    private void  GetMyJobApi() {
        final ProgressDialog pd = new ProgressDialog(MyJobActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<MyJobModel> call = service.MY_JOB_MODEL_CALL("my_job_list?user_id="+user_id);

        call.enqueue(new Callback<MyJobModel>() {
            @Override
            public void onResponse(Call<MyJobModel> call, Response<MyJobModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        MyJobModel myJobModel = response.body();
                        String success = myJobModel.getSuccess();
                        String msg =myJobModel.getMessage();
                        Log.e("hello", "success: " +success );

                        if (success.equals("true")|| (success.equals("True"))) {
                            myJobModelDataList = myJobModel.getData();
                            myJobAdapter = new MyJobAdapter(MyJobActivity.this, myJobModelDataList,myJobModelDataListBackUp,UserType);
                            recyclerApplyJob.setAdapter(myJobAdapter);

                            Log.e("hello", "getData: " );

                        } else {
                            Toast.makeText(MyJobActivity.this, msg, Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(MyJobActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(MyJobActivity.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(MyJobActivity.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(MyJobActivity.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(MyJobActivity.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(MyJobActivity.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(MyJobActivity.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(MyJobActivity.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(MyJobActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(MyJobActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<MyJobModel> call, Throwable t) {
                Log.e("conversion issue", t.getMessage());

                if (t instanceof IOException) {
                    Toast.makeText(MyJobActivity.this, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(MyJobActivity.this, "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }
}