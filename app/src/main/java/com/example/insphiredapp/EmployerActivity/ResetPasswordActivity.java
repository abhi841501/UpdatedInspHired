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

import com.example.insphiredapp.Api_Model.ResetPasswordModel;
import com.example.insphiredapp.R;
import com.example.insphiredapp.retrofit.Api;
import com.example.insphiredapp.retrofit.Api_Client;

import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivity extends AppCompatActivity {
    ImageView resetArrow,hiddenPasswordReset,showPasswordReset,hiddenPasswordResetCp,showPasswordResetCp;
    AppCompatButton resetPasswordBtn;
    EditText CNewPasswordReset,newPasswordReset;
    String strNewPass,strConfirmPass;
    private String UserId ,UserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        inits();

        resetArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        hiddenPasswordReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPasswordReset.setVisibility(View.VISIBLE);
                hiddenPasswordReset.setVisibility(View.GONE);
                newPasswordReset.setTransformationMethod(null);
                newPasswordReset.setSelection(newPasswordReset.getText().length());


            }
        });

        showPasswordReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPasswordReset.setVisibility(View.GONE);
                hiddenPasswordReset.setVisibility(View.VISIBLE);
                newPasswordReset.setTransformationMethod(new PasswordTransformationMethod());
                newPasswordReset.setSelection(newPasswordReset.getText().length());

            }
        });

        hiddenPasswordResetCp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiddenPasswordResetCp.setVisibility(View.GONE);
                showPasswordResetCp.setVisibility(View.VISIBLE);
                CNewPasswordReset.setTransformationMethod(null);
                CNewPasswordReset.setSelection(CNewPasswordReset.getText().length());

            }
        });

        showPasswordResetCp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPasswordResetCp.setVisibility(View.GONE);
                hiddenPasswordResetCp.setVisibility(View.VISIBLE);
                CNewPasswordReset.setTransformationMethod(new PasswordTransformationMethod());
                CNewPasswordReset.setSelection(CNewPasswordReset.getText().length());
            }
        });


        resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strNewPass = newPasswordReset.getText().toString();
                strConfirmPass = CNewPasswordReset.getText().toString();
                if (validation())
                {
                  Reset_Api();
                }
            }
        });


        SharedPreferences getUserIdData = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        UserId = getUserIdData.getString("Id", "");
        UserType = getUserIdData.getString("userType", "");
        Log.e("changePassword", "change" + UserType);


    }

    private void Reset_Api() {
        ProgressDialog progressDialog = new ProgressDialog(ResetPasswordActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("loading");
        progressDialog.show();

        Api service = Api_Client.getClient().create(Api.class);
        Call<ResetPasswordModel> call = service.RESET_PASSWORD_MODEL_CALL(strNewPass,strConfirmPass,UserId,UserType);
        call.enqueue(new Callback<ResetPasswordModel>() {
            @Override
            public void onResponse(Call<ResetPasswordModel> call, Response<ResetPasswordModel> response) {
                progressDialog.dismiss();

                try {
                    if (response.isSuccessful())
                    {
                        ResetPasswordModel resetPasswordModel = response.body();
                        String success = resetPasswordModel.getSuccess();
                        String msg   = resetPasswordModel.getMessage();

                        if (success.equals("true")|| success.equals("true"))
                        {
                            Toast.makeText(getApplicationContext(), "Password reset Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(intent);
                        }

                        else
                        {
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            Log.e("user_id", "    False");
                        }
                    }
                    else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Log.e("user_id", "    Message");
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

                            Log.e("user_id", "    Exception");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                    Log.e("user_id", "    Exception  " + e.getMessage() + "  " + e.toString());
                }

            }

            @Override
            public void onFailure(Call<ResetPasswordModel> call, Throwable t) {
                Log.e("conversion issue", t.getMessage());

                if (t instanceof IOException) {
                    Toast.makeText(getApplicationContext(), "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(getApplicationContext(), "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }
        });





    }

    private boolean validation() {
       if (newPasswordReset.getText().toString().equals(""))
       {
           Toast.makeText(getApplicationContext(), "Please enter new password", Toast.LENGTH_SHORT).show();
           return false;
       }

       else if (CNewPasswordReset.getText().toString().equals(""))
       {
           Toast.makeText(getApplicationContext(), "Please enter confirm password", Toast.LENGTH_SHORT).show();
           return false;
       }
        return true;
    }

    private void inits() {
        resetArrow = findViewById(R.id.resetArrow);
        hiddenPasswordReset = findViewById(R.id.hiddenPasswordReset);
        showPasswordReset = findViewById(R.id.showPasswordReset);
        hiddenPasswordResetCp = findViewById(R.id.hiddenPasswordResetCp);
        showPasswordResetCp = findViewById(R.id.showPasswordResetCp);
        resetPasswordBtn = findViewById(R.id.resetPasswordBtn);
        CNewPasswordReset = findViewById(R.id.CNewPasswordReset);
        newPasswordReset = findViewById(R.id.newPasswordReset);
    }
}