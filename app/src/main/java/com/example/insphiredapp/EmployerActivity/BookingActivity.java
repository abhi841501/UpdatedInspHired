package com.example.insphiredapp.EmployerActivity;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.insphiredapp.Api_Model.BookingModel;
import com.example.insphiredapp.Api_Model.BookingModelData;
import com.example.insphiredapp.Api_Model.GetBookingDetailModel;
import com.example.insphiredapp.Api_Model.GetBookingDetailModelData;
import com.example.insphiredapp.Api_Model.RequestTimeSlotModel;
import com.example.insphiredapp.Api_Model.RequestTimeSlotModelData;
import com.example.insphiredapp.R;
import com.example.insphiredapp.retrofit.Api;
import com.example.insphiredapp.retrofit.Api_Client;

import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingActivity extends AppCompatActivity {
    private String TimeSlotId,StrrrStartDate,StrrrEndDate;
    BookingModelData bookingModelData;
    private ImageView backArrowBooking, requestForReschceduled;
    private TextView nameBooking, designationBooking, startDateBooking, endDateBooking, amountAllEmployeeBooking, locationBooking;
    private TextView AmountBookingTxt;
    private AppCompatButton BookNowBtnBooking, seeVideoBtn,GeneratePOBooking,SubmitPONUmber;
    private GetBookingDetailModelData getBookingDetailModelData;
    private String EmployeeId, StrName, strTotalAmount, strStartDate, strEndDate, strLocation, UserId, UserType, strCatName,
            strDailyAmountFav, strAddressFav,StrGeneratePOTxt;
    private String strAmount, EmployeeIdDetails, Id;
    private int year,month,day;
    RequestTimeSlotModelData requestTimeSlotModelData;
    LinearLayout linearGeneratePoNo;
    EditText GeneratePOTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        inits();
        EmployeeId = getIntent().getStringExtra("EmpId");
        TimeSlotId = getIntent().getStringExtra("TimeSlot");
        Log.e("feedbackkk", "TimeSlotID" + TimeSlotId);
        Log.e("feedbackkk", "EmployeeId" + EmployeeId);
        EmployeeIdDetails = getIntent().getStringExtra("EmpDetailsID");
        backArrowBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SharedPreferences getUserIdData = getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
        UserId = getUserIdData.getString("Id", "");
        UserType = getUserIdData.getString("userType", "");
        Log.e("feedbackkk", "UserId" + UserId);
        Log.e("feedbackkk", "UserType" + UserType);


        getEmployeeDetailsApi();

        seeVideoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookingActivity.this, IntroductionVideoActivity.class);
                intent.putExtra("Id", Id);
                startActivity(intent);
            }
        });

        if (UserType.equals("corporator"))
        {
            GeneratePOBooking.setVisibility(View.VISIBLE);
            BookNowBtnBooking.setVisibility(View.GONE);
        }

        GeneratePOBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearGeneratePoNo.setVisibility(View.VISIBLE);
                SubmitPONUmber.setVisibility(View.VISIBLE);

            }
        });

        SubmitPONUmber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrGeneratePOTxt = GeneratePOTxt.getText().toString();
                Log.e("feedbackkk", "GeneratePOTxt" + StrGeneratePOTxt);
                employeeBookApi();
            }
        });

        BookNowBtnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneratePOBooking.setVisibility(View.GONE);
                BookNowBtnBooking.setVisibility(View.VISIBLE);
                employeeBookApi();

            }
        });

        requestForReschceduled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRequestPopUp();
            }
        });

    }

    private void openRequestPopUp() {
        Dialog openDialog = new Dialog(BookingActivity.this);
        openDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        openDialog.setContentView(R.layout.rescheduled_pop_up);
        AppCompatButton RequestBtnBooking = openDialog.findViewById(R.id.RequestBtnBooking);
        ImageView crossRequestPOpUp = openDialog.findViewById(R.id.crossRequestPOpUp);
        TextView selectSDateReqTxt = openDialog.findViewById(R.id.selectSDateReqTxt);
        TextView selectEndTxtReq = openDialog.findViewById(R.id.selectEndTxtReq);
        LinearLayout linearselectSDateReqTxt = openDialog.findViewById(R.id.linearselectSDateReqTxt);
        LinearLayout linearEndTxtReq = openDialog.findViewById(R.id.linearEndTxtReq);
        openDialog.show();
        Window window = openDialog.getWindow();
        if (window == null) return;
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT; // Match the width to the parent
        params.height = WindowManager.LayoutParams.WRAP_CONTENT; // Match the height to the parent
        window.setAttributes(params);
       /* params.width = 900;
        params.height = 640;
        window.setAttributes(params);
        //    dialog.getWindow().setLayout(100, 100);
        emailVerifyDialog.getWindow().setBackgroundDrawableResource(R.drawable.popup_background);*/

        crossRequestPOpUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog.dismiss();
            }
        });

        linearselectSDateReqTxt.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                final Calendar calendar = Calendar.getInstance();

                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                Log.e("month","kkk.." +month);

                //calendar.add(Calendar.MONTH, 1);

                linearselectSDateReqTxt.setOnClickListener(view -> {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(BookingActivity.this,R.style.MyDatePicker, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            month = month+ 1;

                            String formattedMonth = String.format("%02d", month);
                            String formatDay = String.format("%02d",day);
                            String date = year + "-" + formattedMonth + "-" + formatDay;

                            selectSDateReqTxt.setText(date);
                        }
                    }, year, month, day);
                    datePickerDialog.show();
                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                    datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
                    datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
                });

            }
        });
        linearEndTxtReq.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                final Calendar calendar = Calendar.getInstance();

                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                Log.e("month","kkk.." +month);

                //calendar.add(Calendar.MONTH, 1);

                linearEndTxtReq.setOnClickListener(view -> {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(BookingActivity.this,R.style.MyDatePicker, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            month = month+ 1;

                            String formattedMonth = String.format("%02d", month);
                            String formatDay = String.format("%02d",day);
                            String date = year + "-" + formattedMonth + "-" + formatDay;

                            selectEndTxtReq.setText(date);
                        }
                    }, year, month, day);
                    datePickerDialog.show();
                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                    datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
                    datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
                });

            }
        });

        RequestBtnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrrrStartDate = selectSDateReqTxt.getText().toString();
                StrrrEndDate = selectEndTxtReq.getText().toString();
                RequestTimeApi();
                openDialog.dismiss();
            }
        });

    }


    private void  RequestTimeApi() {
        final ProgressDialog pd = new ProgressDialog(BookingActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<RequestTimeSlotModel> call = service.REQUEST_TIME_SLOT_MODEL_CALL(TimeSlotId,UserId,EmployeeId,StrrrStartDate,StrrrEndDate,UserType);
        call.enqueue(new Callback<RequestTimeSlotModel>() {
            @Override
            public void onResponse(Call<RequestTimeSlotModel> call, Response<RequestTimeSlotModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        RequestTimeSlotModel requestTimeSlotModel = response.body();
                        String success = requestTimeSlotModel.getSuccess();
                        String msg = requestTimeSlotModel.getMessage();
                        Log.e("hello", "success: " +success );

                        if (success.equals("true")|| (success.equals("True"))) {
                            Log.e("hello", "getData: " );
                            requestTimeSlotModelData = requestTimeSlotModel.getData();

                            // Id  = profileGetData.getId();

                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                            // Calling another activity

                        } else {
                            Toast.makeText(BookingActivity.this, msg, Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(BookingActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(BookingActivity.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(BookingActivity.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(BookingActivity.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(BookingActivity.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(BookingActivity.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(BookingActivity.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(BookingActivity.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(BookingActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(BookingActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<RequestTimeSlotModel> call, Throwable t) {
                Log.e("conversion issue", t.getMessage());

                if (t instanceof IOException) {
                    Toast.makeText(BookingActivity.this, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(BookingActivity.this, "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }

    private void getEmployeeDetailsApi() {
        final ProgressDialog pd = new ProgressDialog(BookingActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<GetBookingDetailModel> call = service.GET_BOOKING_DETAIL_MODEL_CALL("user_detail?user_id=" + UserId + "&user_type=" + UserType + "&time_slot_id=" + TimeSlotId + "&employee_id=" + EmployeeId);

        call.enqueue(new Callback<GetBookingDetailModel>() {
            @Override
            public void onResponse(Call<GetBookingDetailModel> call, Response<GetBookingDetailModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String success = response.body().getSuccess();
                        String msg = response.body().getMessage();

                        Log.e("hello", "success: " + success);

                        if (success.equals("true") || (success.equals("True"))) {
                            GetBookingDetailModel getBookingDetailModel = response.body();
                            getBookingDetailModelData = getBookingDetailModel.getData();
                            Id = getBookingDetailModelData.getEmployeeId();
                            StrName = getBookingDetailModelData.getEmployeeName();
                            strCatName = getBookingDetailModelData.getCtaName();
                            strStartDate = getBookingDetailModelData.getStartDate();
                            strEndDate = getBookingDetailModelData.getEndDate();
                            strDailyAmountFav = getBookingDetailModelData.getDailyRate();
                            strLocation = getBookingDetailModelData.getAddress();
                            strTotalAmount = String.valueOf(getBookingDetailModelData.getTotalAmomunt());
                            Log.e("feedback", "totalamount: " + strTotalAmount);

                            nameBooking.setText(StrName);
                            designationBooking.setText(strCatName);
                            startDateBooking.setText(strStartDate);
                            endDateBooking.setText(strEndDate);
                            amountAllEmployeeBooking.setText(strDailyAmountFav);
                            locationBooking.setText(strLocation);
                            AmountBookingTxt.setText(strTotalAmount);

                            Log.e("hello", "getData: ");
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
            public void onFailure(Call<GetBookingDetailModel> call, Throwable t) {
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

    private void employeeBookApi() {
        final ProgressDialog pd = new ProgressDialog(BookingActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<BookingModel> call = service.BOOKING_MODEL_CALL(EmployeeId, UserId, strTotalAmount, TimeSlotId, UserType);

        call.enqueue(new Callback<BookingModel>() {
            @Override
            public void onResponse(Call<BookingModel> call, Response<BookingModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        BookingModel bookingModel = response.body();
                        String success = bookingModel.getSuccess();
                        String msg = bookingModel.getMessage();
                        Log.e("hello", "success: " + success);

                        if (success.equals("true") || (success.equals("True"))) {
                            bookingModelData = bookingModel.getData();

                            Intent intent = new Intent(BookingActivity.this, WebViewActivity.class);
                            intent.putExtra("strAmount", strTotalAmount);
                            intent.putExtra("strEmpid", EmployeeId);
                            intent.putExtra("UserId", UserId);
                            intent.putExtra("SlotId", TimeSlotId);
                            intent.putExtra("Type", UserType);
                            intent.putExtra("StrGeneratePOTxt", StrGeneratePOTxt);
                            startActivity(intent);

                            /*AmountBookingTxt.setText("");*/
                            Log.e("hello", "getData: ");

                        } else {
                            Toast.makeText(BookingActivity.this, msg, Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(BookingActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(BookingActivity.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(BookingActivity.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(BookingActivity.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(BookingActivity.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(BookingActivity.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(BookingActivity.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(BookingActivity.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(BookingActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(BookingActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BookingModel> call, Throwable t) {
                Log.e("conversion issue", t.getMessage());

                if (t instanceof IOException) {
                    Toast.makeText(BookingActivity.this, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(BookingActivity.this, "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }

    private void inits() {
        backArrowBooking = findViewById(R.id.backArrowBooking);
        nameBooking = findViewById(R.id.nameBooking);
        designationBooking = findViewById(R.id.designationBooking);
        startDateBooking = findViewById(R.id.startDateBooking);
        endDateBooking = findViewById(R.id.endDateBooking);
        amountAllEmployeeBooking = findViewById(R.id.amountAllEmployeeBooking);
        locationBooking = findViewById(R.id.locationBooking);
        AmountBookingTxt = findViewById(R.id.AmountBookingTxt);
        BookNowBtnBooking = findViewById(R.id.BookNowBtnBooking);
        seeVideoBtn = findViewById(R.id.seeVideoBtn);
        requestForReschceduled = findViewById(R.id.requestForReschceduled);
        GeneratePOBooking = findViewById(R.id.GeneratePOBooking);
        linearGeneratePoNo = findViewById(R.id.linearGeneratePoNo);
        GeneratePOTxt = findViewById(R.id.GeneratePOTxt);
        SubmitPONUmber = findViewById(R.id.SubmitPONUmber);
    }
}
