package com.example.insphiredapp.EmployerActivity;

import android.app.ProgressDialog;
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

import com.example.insphiredapp.Adapter.EmployerHistoryAdapter;
import com.example.insphiredapp.Api_Model.GetEmployeeHistoryModel;
import com.example.insphiredapp.Api_Model.GetEmployeeHistoryModelData;
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

public class EmployerHistoryActivity extends AppCompatActivity {

    ImageView arrowHistory;
    RecyclerView recyclerHistoryEmployer;
    List<GetEmployeeHistoryModelData> getEmployeeHistoryModelData = new ArrayList<>();
    EmployerHistoryAdapter employerHistoryAdapter;
    private String UserId,UserType;
    private SearchView searchQueryEmployerHis;
    List<GetEmployeeHistoryModelData> employerHistory = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_history);

        arrowHistory = findViewById(R.id.arrowHistory);
        recyclerHistoryEmployer = findViewById(R.id.recyclerHistoryEmployer);
        searchQueryEmployerHis = findViewById(R.id.searchQueryEmployerHis);

        arrowHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(EmployerHistoryActivity.this,LinearLayoutManager.VERTICAL,false);
        recyclerHistoryEmployer.setLayoutManager(layoutManager);
        searchQueryEmployerHis.setIconified(false);
        searchQueryEmployerHis.clearFocus();
        searchQueryEmployerHis.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                employerHistoryAdapter.getFilter().filter(newText);
                return false;
            }
        });
        SharedPreferences getUserIdData = getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
        UserId= getUserIdData.getString("Id", "");
       UserType= getUserIdData.getString("userType", "");
        Log.e("feedback", "change" + UserId);

        SharedPreferences.Editor editor = getUserIdData.edit();
        editor.putString("UserType1", String.valueOf(UserType));
        editor.apply();

        GetHistoryEmployee();


    }

    private void GetHistoryEmployee() {
        final ProgressDialog pd = new ProgressDialog(EmployerHistoryActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<GetEmployeeHistoryModel> call = service.GET_EMPLOYEE_HISTORY_MODEL_CALL("my_booked_employer_history?user_id="+UserId+"&user_type="+UserType);

        call.enqueue(new Callback<GetEmployeeHistoryModel>() {
            @Override
            public void onResponse(Call<GetEmployeeHistoryModel> call, Response<GetEmployeeHistoryModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String success = (response.body().getSuccess());
                        String msg = (response.body().getMessage());
                        Log.e("hello", "success: " +success );

                        if (success.equals("true")|| (success.equals("True"))) {
                            GetEmployeeHistoryModel getEmployeeHistoryModel = response.body();
                            getEmployeeHistoryModelData = getEmployeeHistoryModel.getData();
                            employerHistoryAdapter = new EmployerHistoryAdapter(EmployerHistoryActivity.this,getEmployeeHistoryModelData,employerHistory);
                            recyclerHistoryEmployer.setAdapter(employerHistoryAdapter);


                            Log.e("hello", "getData: " );
                            // Id  = profileGetData.getId();


                         Toast.makeText(EmployerHistoryActivity.this, msg, Toast.LENGTH_SHORT).show();


                            //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            // Calling another activity

                        } else {
                            Toast.makeText(EmployerHistoryActivity.this, msg, Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(EmployerHistoryActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(EmployerHistoryActivity.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(EmployerHistoryActivity.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(EmployerHistoryActivity.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(EmployerHistoryActivity.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(EmployerHistoryActivity.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(EmployerHistoryActivity.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(EmployerHistoryActivity.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(EmployerHistoryActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(EmployerHistoryActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GetEmployeeHistoryModel> call, Throwable t) {
                Log.e("conversion issue", t.getMessage());

                if (t instanceof IOException) {
                    Toast.makeText(EmployerHistoryActivity.this, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(EmployerHistoryActivity.this, "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }
}