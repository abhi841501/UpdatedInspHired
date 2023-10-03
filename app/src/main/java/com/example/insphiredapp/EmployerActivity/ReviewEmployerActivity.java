package com.example.insphiredapp.EmployerActivity;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.insphiredapp.Adapter.ReviewsModelAdapter;
import com.example.insphiredapp.Api_Model.EmployeeReviewDetail;
import com.example.insphiredapp.Api_Model.EmployeeReviewModel;
import com.example.insphiredapp.Api_Model.GetReviewModel;
import com.example.insphiredapp.R;
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

public class ReviewEmployerActivity extends AppCompatActivity {

    RecyclerView recyclerReviews;
    ImageView backArrowReviews;
    List<EmployeeReviewModel> employeeReviewModelList = new ArrayList<>();
    TextView nameRating,profileNameRatingg;
    CircleImageView spareImageeeeee;
    RatingBar rating_barreviewsss;
    EmployeeReviewDetail employeeReviewDetail;
    ReviewsModelAdapter reviewsModelAdapter;
    private String strnameRating,strprofileNameRatingg,strrating_barreviewsss;
    private String EmpId,UserId,UserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_employer);

        recyclerReviews = findViewById(R.id.recyclerReviews);
        backArrowReviews = findViewById(R.id.backArrowReviews);
        nameRating = findViewById(R.id.nameRating);
        spareImageeeeee = findViewById(R.id.spareImageeeeee);
        profileNameRatingg = findViewById(R.id.profileNameRatingg);
        rating_barreviewsss = findViewById(R.id.rating_barreviewsss);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ReviewEmployerActivity.this,LinearLayoutManager.VERTICAL,false);
        recyclerReviews.setLayoutManager(layoutManager);

        SharedPreferences getUserIdData = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        UserId = getUserIdData.getString("Id", "");
        UserType= getUserIdData.getString("userType", "");


        backArrowReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        EmpId= getIntent().getStringExtra("strId");
         GetReviewsEmployee();
    }


    private void  GetReviewsEmployee() {

        final ProgressDialog pd = new ProgressDialog(ReviewEmployerActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<GetReviewModel> call = service.GET_REVIEW_MODEL_CALL("show_review?+employee_id="+EmpId+"&user_type="+UserType);


        call.enqueue(new Callback<GetReviewModel>() {
            @Override
            public void onResponse(Call<GetReviewModel> call, Response<GetReviewModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        GetReviewModel getReviewModel = response.body();
                        String success = getReviewModel.getSuccess();
                        String msg =getReviewModel.getMessage();
                        Log.e("hello", "success: " +success );


                        if (success.equals("true")|| (success.equals("True"))) {

                            employeeReviewDetail = getReviewModel.getEmpDetail();
                            Glide.with(getApplicationContext()).load(Api_Client.BASE_URL_IMAGES+ employeeReviewDetail.getEmpImage()).placeholder(R.drawable.ic_launcher_foreground).into(spareImageeeeee);
                            employeeReviewDetail = response.body().getEmpDetail();
                            strnameRating = employeeReviewDetail.getFirstName();
                            strprofileNameRatingg = employeeReviewDetail.getCatName();
                            float myFloat1 = response.body().getEmpRating();
                          //  holder.rating_barFavGet.setRating(Math.round(myFloat1));
                            //strrating_barreviewsss = String.valueOf(response.body().getEmpRating());


                            nameRating.setText(strnameRating);
                            profileNameRatingg.setText(strprofileNameRatingg);
                            rating_barreviewsss.setRating(Math.round(myFloat1));

                            employeeReviewModelList = response.body().getEmpReview();
                            reviewsModelAdapter = new ReviewsModelAdapter(ReviewEmployerActivity.this,employeeReviewModelList);
                            recyclerReviews.setAdapter(reviewsModelAdapter);





                           // Toast.makeText(ReviewEmployerActivity.this, msg, Toast.LENGTH_SHORT).show();


                            //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            // Calling another activity

                        } else {
                            Toast.makeText(ReviewEmployerActivity.this, msg, Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(ReviewEmployerActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(ReviewEmployerActivity.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(ReviewEmployerActivity.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(ReviewEmployerActivity.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(ReviewEmployerActivity.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(ReviewEmployerActivity.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(ReviewEmployerActivity.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(ReviewEmployerActivity.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(ReviewEmployerActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(ReviewEmployerActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GetReviewModel> call, Throwable t) {
                Log.e("conversion issue", t.getMessage());

                if (t instanceof IOException) {
                    Toast.makeText(ReviewEmployerActivity.this, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(ReviewEmployerActivity.this, "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }

}