package com.example.insphiredapp.EmployeeActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.insphiredapp.Api_Model.GiveRatingModel;
import com.example.insphiredapp.EmployerActivity.FeedbackActivity;
import com.example.insphiredapp.FeedbackEmployee.EmployeeRating;
import com.example.insphiredapp.FeedbackEmployee.EmployeeRatingData;
import com.example.insphiredapp.R;
import com.example.insphiredapp.retrofit.Api;
import com.example.insphiredapp.retrofit.Api_Client;

import org.json.JSONObject;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeGiveRatingActivity extends AppCompatActivity {
    private ImageView backArrowFeedbackEmployee;
    CircleImageView spareImageFeedBackEmployee;
    TextView nameFeedbackEmployer,profileNameFeedbackEmployer;
    RatingBar rating_barFeedbackEmployee;
    EditText leaveCommentEditEmployee;
    AppCompatButton leaveFeedbackBtnEmployee;
    private String EmployerIddd,StrCImage,StrCatnameCompany,StrNameCompany,strRatingBarEmployee,strCommentEmployee;
    private String UserId,UserType;
    EmployeeRatingData employeeRatingData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_give_rating);
        inits();

        SharedPreferences getUserIdData = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        UserId = getUserIdData.getString("Id", "");
        UserType= getUserIdData.getString("userType", "");

        backArrowFeedbackEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        EmployerIddd = getIntent().getStringExtra("EmployerIdFeedback");
        StrCImage = getIntent().getStringExtra("imgg");
        StrCatnameCompany = getIntent().getStringExtra("catNamee");
        StrNameCompany = getIntent().getStringExtra("CompanyName");

        SharedPreferences sharedPreferences = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("EmployerIddd", String.valueOf(EmployerIddd));
        editor.apply();


        nameFeedbackEmployer.setText(StrNameCompany);
        Glide.with(getApplicationContext()).load(Api_Client.BASE_URL_IMAGES1+StrCImage).placeholder(R.drawable.employee).into(spareImageFeedBackEmployee);

        leaveFeedbackBtnEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strRatingBarEmployee = String.valueOf(rating_barFeedbackEmployee.getRating());
                strCommentEmployee = leaveCommentEditEmployee.getText().toString();
                if(validation())
                {
                    GiveRatingApi();
                }
                finish();
            }
        });
    }

    private void  GiveRatingApi() {

        final ProgressDialog pd = new ProgressDialog(EmployeeGiveRatingActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<EmployeeRating> call = service.EMPLOYEE_RATING_CALL(UserId,EmployerIddd,strRatingBarEmployee,strCommentEmployee,UserType);

        call.enqueue(new Callback<EmployeeRating>() {
            @Override
            public void onResponse(Call<EmployeeRating> call, Response<EmployeeRating> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        EmployeeRating employeeRating = response.body();
                        String success = employeeRating.getSuccess();
                        String msg = employeeRating.getMessage();
                        Log.e("hello", "success: " +success );

                        if (success.equals("true")|| (success.equals("True"))) {
                            employeeRatingData = response.body().getData();

                            Toast.makeText(EmployeeGiveRatingActivity.this, msg, Toast.LENGTH_SHORT).show();
                            leaveCommentEditEmployee.setText("");
                            Log.e("hello", "getData: " );

                        } else {
                            Toast.makeText(EmployeeGiveRatingActivity.this, msg, Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(EmployeeGiveRatingActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(EmployeeGiveRatingActivity.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(EmployeeGiveRatingActivity.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(EmployeeGiveRatingActivity.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(EmployeeGiveRatingActivity.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(EmployeeGiveRatingActivity.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(EmployeeGiveRatingActivity.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(EmployeeGiveRatingActivity.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(EmployeeGiveRatingActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(EmployeeGiveRatingActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<EmployeeRating> call, Throwable t) {
                Log.e("conversion issue", t.getMessage());

                if (t instanceof IOException) {
                    Toast.makeText(EmployeeGiveRatingActivity.this, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(EmployeeGiveRatingActivity.this, "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }

    private boolean validation() {
        if (leaveCommentEditEmployee.getText().toString().equals(""))

        {
            Toast.makeText(getApplicationContext(), "Please provide your feedback", Toast.LENGTH_SHORT).show();
            return  false;
        }
        return true;
    }


    private void inits() {
        backArrowFeedbackEmployee = findViewById(R.id.backArrowFeedbackEmployee);
        spareImageFeedBackEmployee = findViewById(R.id.spareImageFeedBackEmployee);
        nameFeedbackEmployer = findViewById(R.id.nameFeedbackEmployer);
        rating_barFeedbackEmployee = findViewById(R.id.rating_barFeedbackEmployee);
        leaveCommentEditEmployee = findViewById(R.id.leaveCommentEditEmployee);
        leaveFeedbackBtnEmployee = findViewById(R.id.leaveFeedbackBtnEmployee);
    }
}