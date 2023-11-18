package com.example.insphiredapp.EmployeeActivity;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.customtoast.chen.customtoast.CustomToast;
import com.example.insphiredapp.Api_Model.CreateSlotModel;
import com.example.insphiredapp.Api_Model.CreateSlotModelData;
import com.example.insphiredapp.R;
import com.example.insphiredapp.retrofit.Api;
import com.example.insphiredapp.retrofit.Api_Client;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateSlotActivity extends AppCompatActivity {

    CreateSlotModelData createSlotModelData;
    private ImageView backArrowCSlots, imgMapC;
    private LinearLayout linearStartDate, linearEndDate, linearStartTime, linearEndTime;
    private TextView startDateTxt, endDateTxt, startTimetxt, endTimeTxt, selectLocationC;
    private int year, month, day;
    private AppCompatButton createSlotsBtn;
    private String StrStartDate, StrStartTime, StrEndDate, StrEndTime, UserId;
    private String Id;
    private CustomToast customToast;
    private RelativeLayout relativeMapssss;
    private String strAddresss,StrselectLocationC;
//    Spinner locationSpinner;
//    List<LocationData> locationDataList;
//    List<String> LocList = new ArrayList<>();
//    private String LocId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_slot);
        inits();

        backArrowCSlots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        relativeMapssss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateSlotActivity.this, LocationActivity.class);
                startActivity(intent);
            }
        });

        strAddresss = getIntent().getStringExtra("searchValue");
        selectLocationC.setText(strAddresss);


        SharedPreferences getUserIdData = getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
        UserId = getUserIdData.getString("Id", "");
        Log.e("feedback", "change" + UserId);

        //   locationApi();
       /* locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //  Toast.makeText(getActivity(), "Country Spinner Working **********", Toast.LENGTH_SHORT).show();

                String item = locationSpinner.getSelectedItem().toString();
                if (item.equals(getResources().getString(R.string.location)))
                {
                    // int spinnerPosition = dAdapter.getPosition(compareValue);
                    // spinner_category.setSelection(Integer.parseInt("Select Category"));
                }   else
                {

                    LocId = (String.valueOf(locationDataList.get(i).getId()));
                    //Toast.makeText(getActivity(), "leaveId :"+absencelist.get(i).getLeaveName()+leaveId, Toast.LENGTH_SHORT).show();

                  *//*  getStateSearch();
                    Log.e("LIST_ID_COUNTRY",country_sp_id+"ID");
                    switch_my.setChecked(false);*//*
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });*/


        createSlotsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StrStartDate = startDateTxt.getText().toString();
                StrStartTime = startTimetxt.getText().toString();
                StrEndDate = endDateTxt.getText().toString();
                StrEndTime = endTimeTxt.getText().toString();
                StrselectLocationC = selectLocationC.getText().toString();
                if (validation()) {
                    CreateSlotsApi();
                }

            }
        });

        linearStartDate.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                final Calendar calendar = Calendar.getInstance();

                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                Log.e("month", "kkk.." + month);

                //calendar.add(Calendar.MONTH, 1);

                linearStartDate.setOnClickListener(view -> {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(CreateSlotActivity.this, R.style.MyDatePicker, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            month = month + 1;

                            String formattedMonth = String.format("%02d", month);
                            String formatDay = String.format("%02d", day);
                            String date = year + "-" + formattedMonth + "-" + formatDay;

                            startDateTxt.setText(date);
                        }
                    }, year, month, day);
                    datePickerDialog.show();
                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                    datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
                    datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
                });

            }
        });

        linearEndDate.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                final Calendar calendar = Calendar.getInstance();

                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                Log.e("month", "kkk.." + month);

                //calendar.add(Calendar.MONTH, 1);

                linearEndDate.setOnClickListener(view -> {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(CreateSlotActivity.this, R.style.MyDatePicker, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            month = month + 1;

                            String formattedMonth = String.format("%02d", month);
                            String formatDay = String.format("%02d", day);
                            String date = year + "-" + formattedMonth + "-" + formatDay;

                            endDateTxt.setText(date);
                        }
                    }, year, month, day);
                    datePickerDialog.show();
                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                    datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
                    datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
                });

            }
        });

        linearStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    mcurrentTime = Calendar.getInstance();
                }
                int hour = 0;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                }
                int minute = 0;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    minute = mcurrentTime.get(Calendar.MINUTE);
                }
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CreateSlotActivity.this, R.style.MyTimePicker, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String formattedhour = String.format("%02d", selectedHour);
                        String formatMinutes = String.format("%02d", selectedMinute);

                        startTimetxt.setText(formattedhour + ":" + formatMinutes);
                    }
                }, hour, minute, true);//Yes 24 hour time
                // mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        linearEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    mcurrentTime = Calendar.getInstance();
                }
                int hour = 0;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                }
                int minute = 0;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    minute = mcurrentTime.get(Calendar.MINUTE);
                }
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CreateSlotActivity.this, R.style.MyTimePicker, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String formattedhour = String.format("%02d", selectedHour);
                        String formatMinutes = String.format("%02d", selectedMinute);
                        endTimeTxt.setText(formattedhour + ":" + formatMinutes);
                    }
                }, hour, minute, true);//Yes 24 hour time
                //   mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });


    }

    private void inits() {
        backArrowCSlots = findViewById(R.id.backArrowCSlots);
        linearStartDate = findViewById(R.id.linearStartDate);
        linearEndDate = findViewById(R.id.linearEndDate);
        startDateTxt = findViewById(R.id.startDateTxt);
        endDateTxt = findViewById(R.id.endDateTxt);
        linearStartTime = findViewById(R.id.linearStartTime);
        linearEndTime = findViewById(R.id.linearEndTime);
        startTimetxt = findViewById(R.id.startTimetxt);
        endTimeTxt = findViewById(R.id.endTimeTxt);
        createSlotsBtn = findViewById(R.id.createSlotsBtn);
        imgMapC = findViewById(R.id.imgMapC);
        selectLocationC = findViewById(R.id.selectLocationC);
        relativeMapssss = findViewById(R.id.relativeMapssss);
        // locationSpinner = findViewById(R.id.locationSpinner);

    }

/*    private void locationApi() {

        final ProgressDialog pd = new ProgressDialog(CreateSlotActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
     //   pd.show();
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<LocationModel> call = service.LOCATION_MODEL_CALL();

        call.enqueue(new Callback<LocationModel>() {
            @Override
            public void onResponse(Call<LocationModel> call, Response<LocationModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String success = response.body().getSuccess();
                        String message = response.body().getMessage();
                        Log.e("hello", "success: " + success);

                        if (success.equals("True") || success.equals("true")) {
                            LocationModel locationModel = response.body();
                            locationDataList = locationModel.getData();

                            for (int i = 0; i<locationDataList.size(); i++) {

                                LocList.add(locationDataList.get(i).getLocationName());
                            }

                            CreateSlotActivity.spinnerAdapter dAdapter = new CreateSlotActivity.spinnerAdapter(CreateSlotActivity.this, R.layout.custom_spinner, LocList);
                            dAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            dAdapter.addAll(LocList);
                            dAdapter.add(getResources().getString(R.string.location));
                            locationSpinner.setAdapter(dAdapter);
                            locationSpinner.setSelection(dAdapter.getCount());

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
            public void onFailure(Call<LocationModel> call, Throwable t) {
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


    }*/

    private void CreateSlotsApi() {

        final ProgressDialog pd = new ProgressDialog(CreateSlotActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        /*JSONObject paramObject = new JSONObject();
        try {

            paramObject.put("UserId",UserId );
            paramObject.put("startDateTxt",StrStartDate );
            paramObject.put("endDateTxt", StrEndDate);
            paramObject.put("startTimetxt", StrStartTime);
            paramObject.put("endTimeTxt", StrEndTime);

            Log.e("paramObject", "strMessage.." + paramObject);

        } catch (JSONException e) {
        }*/

        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<CreateSlotModel> call = service.CREATE_SLOT_MODEL_CALL(UserId,StrStartDate,StrEndDate,StrStartTime,StrEndTime,strAddresss);
        //   RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), paramObject);
        //retrofit2.Call<CreateSlotModel> call = service.CREATE_SLOT_MODEL_CALL(paramObject.toString());

        call.enqueue(new Callback<CreateSlotModel>() {
            @Override
            public void onResponse(Call<CreateSlotModel> call, Response<CreateSlotModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        CreateSlotModel createSlotModel = response.body();
                        String success = createSlotModel.getSuccess();
                        String msg = createSlotModel.getMessage();
                        Log.e("hello", "success: " + success);

                        if (success.equals("true") || (success.equals("True"))) {
                            createSlotModelData = response.body().getData();
                            Id = String.valueOf(createSlotModelData.getId());
                            
                          /*  startDateTxt.setText("");
                             startTimetxt.setText("");
                            endDateTxt.setText("");
                             endTimeTxt.setText("");*/

                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                            Log.e("hello", "getData: ");
                            // Id  = profileGetData.getId();
                        } else {
                         /*   customToast = new CustomToast(CreateSlotActivity.this); //pass context as parameter
                            customToast.setTextColor(CreateSlotActivity.this.getResources().getColor(R.color.black));
                            customToast.setBackground(CreateSlotActivity.this.getResources().getColor(R.color.red));
                            customToast.showErrorToast(msg);*/
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<CreateSlotModel> call, Throwable t) {
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

    private boolean validation() {
        if (startDateTxt.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter start date", Toast.LENGTH_SHORT).show();
            return false;
        } else if (startTimetxt.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter start time", Toast.LENGTH_SHORT).show();
            return false;
        } else if (endDateTxt.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter end date", Toast.LENGTH_SHORT).show();
            return false;
        } else if (endTimeTxt.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter end time", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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