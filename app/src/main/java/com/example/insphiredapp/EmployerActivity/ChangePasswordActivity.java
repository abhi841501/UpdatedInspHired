package com.example.insphiredapp.EmployerActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.insphiredapp.Api_Model.ChangePasswordModel;
import com.example.insphiredapp.EmployeeActivity.DashboardActivityEmployee;
import com.example.insphiredapp.R;
import com.example.insphiredapp.retrofit.Api;
import com.example.insphiredapp.retrofit.Api_Client;

import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    ImageView backArrowChangePassword,hpChangeP,sPChangeP,hPChangeCiFm,sPChangeCiFmt;
    AppCompatButton updatePasswordBtn;
    EditText oldPasswordChange,newPasswordChange,cnfmPasswordChange;

 String strOldPassword,strNewPassword;
 private String UserId,UserType;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        inits();

        backArrowChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        updatePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strOldPassword = oldPasswordChange.getText().toString();
                strNewPassword = newPasswordChange.getText().toString();

                if (Validation())
                {
                   changePasswordApi();
                }

            }
        });

        hpChangeP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sPChangeP.setVisibility(View.VISIBLE);
                hpChangeP.setVisibility(View.GONE);
                newPasswordChange.setTransformationMethod(null);
                newPasswordChange.setSelection(newPasswordChange.getText().length());

            }
        });

        sPChangeP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hpChangeP.setVisibility(View.VISIBLE);
                sPChangeP.setVisibility(View.GONE);
                newPasswordChange.setTransformationMethod(new PasswordTransformationMethod());
                newPasswordChange.setSelection(newPasswordChange.getText().length());
            }
        });


        hPChangeCiFm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hPChangeCiFm.setVisibility(View.GONE);
                sPChangeCiFmt.setVisibility(View.VISIBLE);
                cnfmPasswordChange.setTransformationMethod(null);
                cnfmPasswordChange.setSelection(cnfmPasswordChange.getText().length());
            }
        });

        sPChangeCiFmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hPChangeCiFm.setVisibility(View.VISIBLE);
                sPChangeCiFmt.setVisibility(View.GONE);
                cnfmPasswordChange.setTransformationMethod(new PasswordTransformationMethod());
                cnfmPasswordChange.setSelection(cnfmPasswordChange.getText().length());
            }
        });

        SharedPreferences getUserIdData = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        UserId = getUserIdData.getString("Id", "");
        UserType = getUserIdData.getString("userType", "");
        Log.e("changePassword", "change" + UserType);


    }

    private boolean Validation() {

        if (oldPasswordChange.getText().toString().equals(""))
        {
            Toast.makeText(ChangePasswordActivity.this, "Please enter old password", Toast.LENGTH_SHORT).show();
            return false;
        }

        else if(newPasswordChange.getText().toString().equals(""))
        {
            Toast.makeText(ChangePasswordActivity.this, "Please enter new password", Toast.LENGTH_SHORT).show();
            return false;
        }

        else if (cnfmPasswordChange.getText().toString().equals(""))
        {
            Toast.makeText(ChangePasswordActivity.this, "Please enter confirm password", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!newPasswordChange.getText().toString().equals(cnfmPasswordChange.getText().toString())) {
            Toast.makeText(ChangePasswordActivity.this, "Password did not match", Toast.LENGTH_SHORT).show();
            return false;
        }
return true;

    }


    private void changePasswordApi() {

            final ProgressDialog pd = new ProgressDialog(ChangePasswordActivity.this);
            pd.setCancelable(false);
            pd.setMessage("loading...");
            pd.show();

            Log.e("changePassword", "changePasswordApi: ");


            Api service = Api_Client.getClient().create(Api.class);
            retrofit2.Call<ChangePasswordModel> call = service.CHANGE_PASSWORD_MODEL_CALL(UserId,strOldPassword,strNewPassword,UserType);

            call.enqueue(new Callback<ChangePasswordModel>() {
                @Override
                public void onResponse(Call<ChangePasswordModel> call, Response<ChangePasswordModel> response) {
                    pd.dismiss();
                    Log.e(
                            "changePassword", "loading");
                    try {
                        //if api response is successful ,taking message and success
                        if (response.isSuccessful()) {

                            String success = response.body().getSuccess();
                            String message = response.body().getMessage();
                            String userType = response.body().getUserType();

                            Log.e("changePassword", "Success" + success);

                            if (success.equals("true")|| success.equals("True")) {

                                Log.e("changePassword", "response" + success);
                                if (userType.equals("Employee"))
                                {
                                    Intent intent = new Intent(getApplicationContext(), DashboardActivityEmployee.class);
                                    startActivity(intent);
                                }

                                else
                                {
                                    Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                                    // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }


                                Toast.makeText(ChangePasswordActivity.this, message, Toast.LENGTH_LONG).show();

                            } else {

                                Toast.makeText(ChangePasswordActivity.this, message, Toast.LENGTH_LONG).show();

                                Log.e("Checking", "Success");

                            }

                        } else {
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                Toast.makeText(ChangePasswordActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                                switch (response.code()) {
                                    case 400:
                                        Toast.makeText(ChangePasswordActivity.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 401:
                                        Toast.makeText(ChangePasswordActivity.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 404:
                                        Toast.makeText(ChangePasswordActivity.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 500:
                                        Toast.makeText(ChangePasswordActivity.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 503:
                                        Toast.makeText(ChangePasswordActivity.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 504:
                                        Toast.makeText(ChangePasswordActivity.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 511:
                                        Toast.makeText(ChangePasswordActivity.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                        break;
                                    default:
                                        Toast.makeText(ChangePasswordActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                                        break;
                                }

                            } catch (Exception e) {
                                Toast.makeText(ChangePasswordActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    } catch (
                            Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ChangePasswordModel> call, Throwable t) {
                    Log.e("conversion issue", t.getMessage());

                    if (t instanceof IOException) {
                        Toast.makeText(ChangePasswordActivity.this, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    } else {
                        Log.e("conversion issue", t.getMessage());
                        Toast.makeText(ChangePasswordActivity.this, "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            });
        }


    private void inits() {

        backArrowChangePassword = findViewById(R.id.backArrowChangePassword);
        oldPasswordChange = findViewById(R.id.oldPasswordChange);
        newPasswordChange = findViewById(R.id.newPasswordChange);
        cnfmPasswordChange = findViewById(R.id.cnfmPasswordChange);
        updatePasswordBtn = findViewById(R.id.updatePasswordBtn);
        hpChangeP = findViewById(R.id.hpChangeP);
        sPChangeP = findViewById(R.id.sPChangeP);
        hPChangeCiFm = findViewById(R.id.hPChangeCiFm);
        sPChangeCiFmt = findViewById(R.id.sPChangeCiFmt);
    }
}