package com.example.insphiredapp.EmployerActivity;

import static com.example.insphiredapp.R.color.Red;
import static com.example.insphiredapp.R.color.black;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.insphiredapp.Api_Model.FavouriteDataList;
import com.example.insphiredapp.Api_Model.Favourite_employee_model;
import com.example.insphiredapp.Api_Model.ShowCvModel;
import com.example.insphiredapp.EmployerFragment.FavouriteFragment;
import com.example.insphiredapp.R;
import com.example.insphiredapp.retrofit.Api;
import com.example.insphiredapp.retrofit.Api_Client;

import org.json.JSONObject;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmpDetailsActivity extends AppCompatActivity {
    ImageView empDArrow,empFavouriteIcon;
    TextView ViewAvailability;
    TextView viewReview,EmpdName,empdDesig,empdRating,ViewCv11;
    RatingBar rating_barId;
    String Id,strId,fav,RatingInt;
    int empdeRating,strRatingfv;
    FavouriteDataList favouriteDataList;
    private String UserId,strempdDesig,strData,strImg,UserType,statusCheck="",statusCheck1="";
    CircleImageView employeeDetailsImage,empIcon;

   // boolean isCheck = true ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_details);
        inits();
        strId = getIntent().getStringExtra("EmpId");
        strImg = getIntent().getStringExtra("imgg");
        strData = getIntent().getStringExtra("FullName");
        strempdDesig = getIntent().getStringExtra("Designation");
        empdeRating = getIntent().getIntExtra("Rating",0);
        RatingInt = getIntent().getStringExtra("Rating1");


            Glide.with(getApplicationContext()).load(Api_Client.BASE_URL_IMAGES+strImg).placeholder(R.drawable.employee).into(employeeDetailsImage);
            EmpdName.setText(strData);
            empdDesig.setText(strempdDesig);
            rating_barId.setRating(empdeRating);
             empdRating.setText(RatingInt);


        SharedPreferences getUserIdData = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        UserId = getUserIdData.getString("Id", "");
        UserType= getUserIdData.getString("userType", "");
        Log.e("Employeedetails", "change" + UserId);
        empDArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ViewAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmpDetailsActivity.this, SelectSlotsActivity.class);
                intent.putExtra("EmpId",strId);
                startActivity(intent);
            }
        });

        ViewCv11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewCVApi();
            }
        });

        empIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addFavApi();

            }

        });

        viewReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmpDetailsActivity.this,ReviewEmployerActivity.class);
                intent.putExtra("strId",strId);
                startActivity(intent);
            }
        });
    }

    private void  ViewCVApi() {

        final ProgressDialog pd = new ProgressDialog(EmpDetailsActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<ShowCvModel> call = service.SHOW_CV_MODEL_CALL("show_cv?employee_id="+strId+"&user_type="+UserType);

        call.enqueue(new Callback<ShowCvModel>() {
            @Override
            public void onResponse(Call<ShowCvModel> call, Response<ShowCvModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String success = (response.body().getSuccess());
                        String msg = (response.body().getMessage());

                        if (success.equals("true")|| (success.equals("True"))) {
                            String data = response.body().getData();
                            Intent intent = new Intent(getApplicationContext(),PdfViewerActivity.class);
                            intent.putExtra("data",data);
                            startActivity(intent);



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
            public void onFailure(Call<ShowCvModel> call, Throwable t) {
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

    private void inits() {
        empDArrow = findViewById(R.id.empDArrow);
        ViewAvailability = findViewById(R.id.ViewAvailability);
        empFavouriteIcon = findViewById(R.id.empFavouriteIcon);
        viewReview = findViewById(R.id.viewReview);
        EmpdName = findViewById(R.id.EmpdName);
        empdDesig = findViewById(R.id.empdDesig);
        employeeDetailsImage = findViewById(R.id.employeeDetailsImage);
        empdRating = findViewById(R.id.empdRating);
        rating_barId = findViewById(R.id.rating_barId);
        empIcon = findViewById(R.id.empIcon);
        ViewCv11 = findViewById(R.id.ViewCv11);
    }

    private void  addFavApi() {

        final ProgressDialog pd = new ProgressDialog(EmpDetailsActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<Favourite_employee_model> call = service.FAVOURITE_EMPLOYEE_MODEL_CALL(strId,UserId,UserType);

        call.enqueue(new Callback<Favourite_employee_model>() {
            @Override
            public void onResponse(Call<Favourite_employee_model> call, Response<Favourite_employee_model> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        Favourite_employee_model favourite_employee_model = response.body();
                        String success = favourite_employee_model.getSuccess();
                        String msg = favourite_employee_model.getMessage();
                        Log.e("hello", "success: " +success );

                        if (success.equals("true")|| (success.equals("True"))) {
                            Toast.makeText(EmpDetailsActivity.this, msg, Toast.LENGTH_SHORT).show();
                            favouriteDataList = favourite_employee_model.getData();
                            Id = String.valueOf(favouriteDataList.getId());
                            fav = String.valueOf(favouriteDataList.getIsFav());
                            Log.e("hi", "success: " +fav );

                            if (fav.equals("1"))
                            {
                               // addFavApi();
                                empFavouriteIcon.setBackground(getApplication().getResources().getDrawable(R.drawable.favoritered));
                                empFavouriteIcon.setColorFilter(getApplication().getResources().getColor(Red));
                            }
                            else
                            {
                                empFavouriteIcon.setBackground(getApplication().getResources().getDrawable(R.drawable.favourite));
                                empFavouriteIcon.setColorFilter(getApplication().getResources().getColor(black));


                            }
                            Log.e("hello", "getData: " +fav);
                            Intent intent = new Intent(EmpDetailsActivity.this, FavouriteFragment.class);
                            intent.putExtra("Id","Id");
                            startActivity(intent);


                            Log.e("hello", "getData: " );
                            // Id  = profileGetData.getId();


                        } else {
                            Toast.makeText(EmpDetailsActivity.this, msg, Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(EmpDetailsActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(EmpDetailsActivity.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(EmpDetailsActivity.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(EmpDetailsActivity.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(EmpDetailsActivity.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(EmpDetailsActivity.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(EmpDetailsActivity.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(EmpDetailsActivity.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(EmpDetailsActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(EmpDetailsActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Favourite_employee_model> call, Throwable t) {
                Log.e("conversion issue", t.getMessage());

                if (t instanceof IOException) {
                    Toast.makeText(EmpDetailsActivity.this, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(EmpDetailsActivity.this, "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }
}