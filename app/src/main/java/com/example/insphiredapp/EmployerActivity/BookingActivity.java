package com.example.insphiredapp.EmployerActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.insphiredapp.Api_Model.BookingModel;
import com.example.insphiredapp.Api_Model.BookingModelData;
import com.example.insphiredapp.Api_Model.GetBookingDetailModel;
import com.example.insphiredapp.Api_Model.GetBookingDetailModelData;
import com.example.insphiredapp.R;
import com.example.insphiredapp.retrofit.Api;
import com.example.insphiredapp.retrofit.Api_Client;

import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingActivity extends AppCompatActivity {
    ImageView backArrowBooking;
    TextView nameBooking,designationBooking,startDateBooking,endDateBooking,amountAllEmployeeBooking,locationBooking;
    TextView AmountBookingTxt;
    AppCompatButton BookNowBtnBooking;
    GetBookingDetailModelData getBookingDetailModelData;
    private String EmployeeId,StrName,strTotalAmount,strStartDate,strEndDate,strLocation,UserId,UserType
            ,strCatName,
            strDailyAmountFav,strAddressFav;
    String TimeSlotId;
    private String strAmount,EmployeeIdDetails;
    BookingModelData bookingModelData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        inits();
        EmployeeId = getIntent().getStringExtra("EmpId");
        TimeSlotId = getIntent().getStringExtra("TimeSlot");
        Log.e("TimeSlotId", "change" + TimeSlotId);
        EmployeeIdDetails = getIntent().getStringExtra("EmpDetailsID");
        backArrowBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SharedPreferences getUserIdData = getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
        UserId= getUserIdData.getString("Id", "");
        UserType= getUserIdData.getString("userType", "");
        Log.e("feedbackkk", "change" + UserId);
        Log.e("feedbackkk", "change" + UserType);
        getEmployeeDetailsApi();

        BookNowBtnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    employeeBookApi();

            }
        });
    }

    private void  getEmployeeDetailsApi() {
        final ProgressDialog pd = new ProgressDialog(BookingActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<GetBookingDetailModel> call = service.GET_BOOKING_DETAIL_MODEL_CALL("user_detail?user_id="+UserId+"&user_type="+UserType+"&time_slot_id="+TimeSlotId+"&employee_id="+EmployeeId);

        call.enqueue(new Callback<GetBookingDetailModel>() {
            @Override
            public void onResponse(Call<GetBookingDetailModel> call, Response<GetBookingDetailModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String success =response.body().getSuccess();
                        String msg =response.body().getMessage();

                        Log.e("hello", "success: " +success );

                        if (success.equals("true")|| (success.equals("True"))) {
                            GetBookingDetailModel getBookingDetailModel = response.body();
                            getBookingDetailModelData = getBookingDetailModel.getData();

                            StrName = getBookingDetailModelData.getEmployeeName();
                            strCatName = getBookingDetailModelData.getCtaName();
                            strStartDate = getBookingDetailModelData.getStartDate();
                            strEndDate = getBookingDetailModelData.getEndDate();
                            strDailyAmountFav = getBookingDetailModelData.getDailyRate();
                            strLocation = getBookingDetailModelData.getAddress();
                            strTotalAmount = String.valueOf(getBookingDetailModelData.getTotalAmomunt());
                            Log.e("hell", "onResponse: "+strTotalAmount );

                            nameBooking.setText(StrName);
                            designationBooking.setText(strCatName);
                            startDateBooking.setText(strStartDate);
                            endDateBooking.setText(strEndDate);
                            amountAllEmployeeBooking.setText(strDailyAmountFav);
                            locationBooking.setText(strLocation);
                            AmountBookingTxt.setText(strTotalAmount);

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
    private void  employeeBookApi() {
        final ProgressDialog pd = new ProgressDialog(BookingActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<BookingModel> call = service.BOOKING_MODEL_CALL(EmployeeId,UserId, strTotalAmount,TimeSlotId,UserType);

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
                        Log.e("hello", "success: " +success );

                        if (success.equals("true")|| (success.equals("True"))) {
                            bookingModelData = bookingModel.getData();

                            Intent intent = new Intent(BookingActivity.this, WebViewActivity.class);
                            intent.putExtra("strAmount",strTotalAmount);
                            intent.putExtra("strEmpid",EmployeeId);
                            intent.putExtra("UserId",UserId);
                            intent.putExtra("SlotId",TimeSlotId);
                            intent.putExtra("Type",UserType);
                            startActivity(intent);

                            /*AmountBookingTxt.setText("");*/
                            Log.e("hello", "getData: " );

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
    }
    }
