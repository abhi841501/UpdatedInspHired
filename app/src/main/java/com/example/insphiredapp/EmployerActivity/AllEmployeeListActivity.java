package com.example.insphiredapp.EmployerActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.insphiredapp.Adapter.EmployeeListAdapter;
import com.example.insphiredapp.Api_Model.AllEmployeeDataList;
import com.example.insphiredapp.Api_Model.AllEmployeeListModel;
import com.example.insphiredapp.Api_Model.FilterModel;
import com.example.insphiredapp.Api_Model.FilterModelListData;
import com.example.insphiredapp.Api_Model.GetCategoryModel;
import com.example.insphiredapp.Api_Model.GetCategoryModelData;
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

public class AllEmployeeListActivity extends AppCompatActivity {
    ImageView arrowEmployeeList,filterImage,crossBtn;
    EditText locFilter;
    Spinner SpinnerFilter;
    RecyclerView recyclerEmployeeList;
    SwitchCompat switchbtnPerDayFilter,switchbtnPerHourFilter;
    List<AllEmployeeDataList>allEmployeeDataLists = new ArrayList<>();
    EmployeeListAdapter employeeListAdapter;
    AppCompatButton applyBtnPopUp;
    SeekBar seekBar;
    TextView seekbarValue;
    String progressString;
    private String StringBtnDay="0",user_id;
    List<String> CatList1 = new ArrayList<>();
    List<GetCategoryModelData> getCategoryModelDataList;
    private  String catIds;
    private SearchView searchEmployee;
    List<AllEmployeeDataList> allEmployeeDataListsSearch = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_emploee_list);

        arrowEmployeeList = findViewById(R.id.arrowEmployeeList);
        recyclerEmployeeList = findViewById(R.id.recyclerEmployeeList);
        filterImage = findViewById(R.id.filterImage);
        searchEmployee = findViewById(R.id.searchEmployee);

        arrowEmployeeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AllEmployeeListActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerEmployeeList.setLayoutManager(layoutManager);

        searchEmployee.setIconified(false);
        searchEmployee.clearFocus();
        searchEmployee.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                employeeListAdapter.getFilter().filter(newText);
                return false;
            }
        });

        filterImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog addDetailsFilter = new Dialog(AllEmployeeListActivity.this);
                addDetailsFilter.requestWindowFeature(Window.FEATURE_NO_TITLE);
                addDetailsFilter.setContentView(R.layout.filterpopup);
                 crossBtn = addDetailsFilter.findViewById(R.id.crossBtn);
                SpinnerFilter = addDetailsFilter.findViewById(R.id.SpinnerFilter);
                 locFilter = addDetailsFilter.findViewById(R.id.locFilter);
                switchbtnPerDayFilter = addDetailsFilter.findViewById(R.id.switchbtnPerDayFilter);
                applyBtnPopUp = addDetailsFilter.findViewById(R.id.applyBtnPopUp);
                SpinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        //  Toast.makeText(getActivity(), "Country Spinner Working **********", Toast.LENGTH_SHORT).show();

                        String item = SpinnerFilter.getSelectedItem().toString();
                        if (item.equals(getResources().getString(R.string.category))) {
                            //CatList1.clear();

                        } else {

                            catIds = String.valueOf(getCategoryModelDataList.get(i).getId());
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {


                    }
                });
                addDetailsFilter.show();
                Window window = addDetailsFilter.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                getCategoryApi();
                CatList1.clear();
              //  window.setAttributes(params);
                //    dialog.getWindow().setLayout(100, 100);
              //  addDetailsFilter.getWindow().setBackgroundDrawableResource(R.drawable.popup_background);
                if(switchbtnPerDayFilter.isChecked())
                {
                    StringBtnDay = "1";
                }
                else
                {
                    StringBtnDay = "0";
                }

               /* if (switchbtnPerHourFilter.isChecked())
                {
                    StringBtnHour = "1";
                }

                else {
                    StringBtnHour = "0";
                }*/
                crossBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addDetailsFilter.dismiss();
                    }
                });
                seekBar = addDetailsFilter.findViewById(R.id.seekBar);
                seekBar.setProgress(0);
                seekBar.incrementProgressBy(100);
                seekBar.setMax(5000);
                seekbarValue = addDetailsFilter.findViewById(R.id.seekbarValue);
                seekbarValue.setText(seekbarValue.getText().toString().trim());

                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progress = progress / 100;
                        progress = progress * 100;
                        progressString = String.valueOf(progress);
                        seekbarValue.setText(progressString);
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

                applyBtnPopUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      /*  if (validation())
                        {

                        }*/
                        FilterApi();
                        addDetailsFilter.dismiss();
                    }
                });


            }
        });

        SharedPreferences getUserIdData = getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
        user_id= getUserIdData.getString("Id", "");
        GetAllEmployeeList();
    }

    private void getCategoryApi() {

        final ProgressDialog pd = new ProgressDialog(AllEmployeeListActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<GetCategoryModel> call = service.GET_CATEGORY_MODEL_CALL();

        call.enqueue(new Callback<GetCategoryModel>() {
            @Override
            public void onResponse(Call<GetCategoryModel> call, Response<GetCategoryModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String success = response.body().getSuccess();
                        String message = response.body().getMessage();
                        Log.e("hello", "success: " + success);

                        if (success.equals("True") || success.equals("true")) {
                            GetCategoryModel getCategoryModel = response.body();
                            getCategoryModelDataList = getCategoryModel.getData();

                            for (int i = 0; i < getCategoryModelDataList.size(); i++) {

                                CatList1.add(getCategoryModelDataList.get(i).getCatName());
                                //CatList1.clear();

                                Log.e("catIds", "onResponse: "+catIds );

                            }
                            spinnerAdapter dAdapter = new spinnerAdapter(AllEmployeeListActivity.this, R.layout.filter_custom_spinner, CatList1);
                            dAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            dAdapter.addAll(CatList1);

                            dAdapter.add(getResources().getString(R.string.category));
                            SpinnerFilter.setAdapter(dAdapter);
                            SpinnerFilter.setSelection(dAdapter.getCount());

                            Log.e("hello", "getData: ");
                        } else {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
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
            public void onFailure(Call<GetCategoryModel> call, Throwable t) {
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

    private void  FilterApi() {

        final ProgressDialog pd = new ProgressDialog(AllEmployeeListActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<FilterModel> call = service.FILTER_MODEL_CALL(catIds,locFilter.getText().toString(),StringBtnDay,seekbarValue.getText().toString());
        call.enqueue(new Callback<FilterModel>() {
            @Override
            public void onResponse(Call<FilterModel> call, Response<FilterModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        FilterModel filterModel = response.body();
                        String success = filterModel.getSuccess();
                        String msg = filterModel.getMessage();
                        Log.e("hello", "success: " +success );

                        if (success.equals("true")|| (success.equals("True"))) {
                            Log.e("hello", "getData: " );
                            allEmployeeDataLists = filterModel.getData();
                            employeeListAdapter = new EmployeeListAdapter(AllEmployeeListActivity.this, allEmployeeDataLists,allEmployeeDataListsSearch);
                            recyclerEmployeeList.setAdapter(employeeListAdapter);

                            // Id  = profileGetData.getId();

                            //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            // Calling another activity

                        } else {
                            Toast.makeText(AllEmployeeListActivity.this, msg, Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(AllEmployeeListActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(AllEmployeeListActivity.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(AllEmployeeListActivity.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(AllEmployeeListActivity.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(AllEmployeeListActivity.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(AllEmployeeListActivity.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(AllEmployeeListActivity.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(AllEmployeeListActivity.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(AllEmployeeListActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(AllEmployeeListActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<FilterModel> call, Throwable t) {
                Log.e("conversion issue", t.getMessage());

                if (t instanceof IOException) {
                    Toast.makeText(AllEmployeeListActivity.this, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(AllEmployeeListActivity.this, "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }

    private void  GetAllEmployeeList() {

        final ProgressDialog pd = new ProgressDialog(AllEmployeeListActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<AllEmployeeListModel> call = service.ALL_EMPLOYEE_LIST_MODEL_CALL("all_user_data?user_id="+user_id);

        call.enqueue(new Callback<AllEmployeeListModel>() {
            @Override
            public void onResponse(Call<AllEmployeeListModel> call, Response<AllEmployeeListModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String success = (response.body().getSuccess());
                        String msg = (response.body().getMessage());
                        Log.e("hello", "success: " +success );

                        if (success.equals("true")|| (success.equals("True"))) {
                            AllEmployeeListModel allEmployeeListModel = response.body();
                            allEmployeeDataLists = allEmployeeListModel.getData();
                            employeeListAdapter = new EmployeeListAdapter(AllEmployeeListActivity.this, allEmployeeDataLists,allEmployeeDataListsSearch);
                            recyclerEmployeeList.setAdapter(employeeListAdapter);

                            Log.e("hello", "getData: " );
                            // Id  = profileGetData.getId();

                            //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            // Calling another activity

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
            public void onFailure(Call<AllEmployeeListModel> call, Throwable t) {
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

    public class spinnerAdapter extends ArrayAdapter<String> {
        private spinnerAdapter(Context context, int textViewResourceId, List<String> smonking) {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount() {
            int count = super.getCount();
            return count > 0 ? count - 1 : count;
        }
    }
}