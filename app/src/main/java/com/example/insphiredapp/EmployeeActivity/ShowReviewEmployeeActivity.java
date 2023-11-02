package com.example.insphiredapp.EmployeeActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.insphiredapp.Adapter.EmployeeReviewsAdapter;
import com.example.insphiredapp.Adapter.ReviewsModelAdapter;
import com.example.insphiredapp.Api_Model.EmployeeReviewModel;
import com.example.insphiredapp.Api_Model.GetReviewModel;
import com.example.insphiredapp.EmployerActivity.ReviewEmployerActivity;
import com.example.insphiredapp.R;
import com.example.insphiredapp.ShowReviewApiModel.EmpDetailData;
import com.example.insphiredapp.ShowReviewApiModel.EmpReviewDataList;
import com.example.insphiredapp.ShowReviewApiModel.ShowEmployeeReview;
import com.example.insphiredapp.retrofit.Api;
import com.example.insphiredapp.retrofit.Api_Client;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowReviewEmployeeActivity extends AppCompatActivity {
    ImageView backArrowReviewsEmployee;
    CircleImageView spareImageeeeeeEmployee;
    RatingBar rating_barreviewsssEmployee;
    RecyclerView recyclerReviewsEmployee;
    private String UserId,UserType;
    TextView nameRatingEmployee,profileNameRatinggEmployee;
    EmpDetailData empDetailData;
    private String strnameRating,strprofileNameRatingg;
    List<EmpReviewDataList> empReviewDataLists = new ArrayList<>();
    EmployeeReviewsAdapter employeeReviewsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_review_employee);

        backArrowReviewsEmployee = findViewById(R.id.backArrowReviewsEmployee);
        spareImageeeeeeEmployee = findViewById(R.id.spareImageeeeeeEmployee);
        rating_barreviewsssEmployee = findViewById(R.id.rating_barreviewsssEmployee);
        recyclerReviewsEmployee = findViewById(R.id.recyclerReviewsEmployee);
        nameRatingEmployee = findViewById(R.id.nameRatingEmployee);
       // profileNameRatinggEmployee = findViewById(R.id.profileNameRatinggEmployee);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ShowReviewEmployeeActivity.this,LinearLayoutManager.VERTICAL,false);
        recyclerReviewsEmployee.setLayoutManager(layoutManager);

        SharedPreferences getUserIdData = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        UserId = getUserIdData.getString("Id", "");
        UserType= getUserIdData.getString("userType", "");


        backArrowReviewsEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        UserId = sharedPreferences.getString("EmployerIddd", "");

        ShowEmployeeReview();

    }
    private void  ShowEmployeeReview() {

        final ProgressDialog pd = new ProgressDialog(ShowReviewEmployeeActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<ShowEmployeeReview> call = service.SHOW_EMPLOYEE_REVIEW_CALL("show_review?+employer_id="+UserId+"&user_type="+UserType);


        call.enqueue(new Callback<ShowEmployeeReview>() {
            @Override
            public void onResponse(Call<ShowEmployeeReview> call, Response<ShowEmployeeReview> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        ShowEmployeeReview showEmployeeReview = response.body();
                        String success = showEmployeeReview.getSuccess();
                        String msg =showEmployeeReview.getMessage();
                        Log.e("hello", "success: " +success );


                        if (success.equals("true")|| (success.equals("True"))) {

                            empDetailData = showEmployeeReview.getEmpDetail();
                            Glide.with(getApplicationContext()).load(Api_Client.BASE_URL_IMAGES1+ empDetailData.getImage()).placeholder(R.drawable.ic_launcher_foreground).into(spareImageeeeeeEmployee);
                            empDetailData = response.body().getEmpDetail();
                            strnameRating = empDetailData.getFirstName();
                            strprofileNameRatingg = empDetailData.getCompanyName();
                            float myFloat1 = response.body().getEmpRating();
                            //  holder.rating_barFavGet.setRating(Math.round(myFloat1));
                            //strrating_barreviewsss = String.valueOf(response.body().getEmpRating());


                            nameRatingEmployee.setText(strnameRating);
                            profileNameRatinggEmployee.setText(strprofileNameRatingg);
                            rating_barreviewsssEmployee.setRating(Math.round(myFloat1));

                            empReviewDataLists = response.body().getEmpReview();
                            employeeReviewsAdapter = new EmployeeReviewsAdapter(ShowReviewEmployeeActivity.this,empReviewDataLists);
                            recyclerReviewsEmployee.setAdapter(employeeReviewsAdapter);





                            // Toast.makeText(ReviewEmployerActivity.this, msg, Toast.LENGTH_SHORT).show();


                            //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            // Calling another activity

                        } else {
                            Toast.makeText(ShowReviewEmployeeActivity.this, msg, Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(ShowReviewEmployeeActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(ShowReviewEmployeeActivity.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(ShowReviewEmployeeActivity.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(ShowReviewEmployeeActivity.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(ShowReviewEmployeeActivity.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(ShowReviewEmployeeActivity.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(ShowReviewEmployeeActivity.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(ShowReviewEmployeeActivity.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(ShowReviewEmployeeActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(ShowReviewEmployeeActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ShowEmployeeReview> call, Throwable t) {
                Log.e("conversion issue", t.getMessage());

                if (t instanceof IOException) {
                    Toast.makeText(ShowReviewEmployeeActivity.this, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(ShowReviewEmployeeActivity.this, "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }
}