package com.example.insphiredapp.EmployerActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.insphiredapp.ApiModelEmployer.ForgotPassword;
import com.example.insphiredapp.Api_Model.PostEmailOtpData;
import com.example.insphiredapp.Api_Model.PostEmailOtpModel;
import com.example.insphiredapp.Api_Model.VerifyOtpData;
import com.example.insphiredapp.Api_Model.VerifyOtpModel;
import com.example.insphiredapp.R;
import com.example.insphiredapp.retrofit.Api;
import com.example.insphiredapp.retrofit.Api_Client;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import in.aabhasjindal.otptextview.OtpTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordActivity extends AppCompatActivity {
    AppCompatButton GetOtpBtn;
    EditText phoneNumber,emailForget, OTPDialog;
    ImageView forgotArrowImg;
    RadioGroup radioGroupBtnForget;
    RadioButton employerMobileBtnBtn,employerEmailBtn;
    RelativeLayout relativeForgetN,relativeForgetEmail;

    public String phoneVerificationId;
    public FirebaseAuth mAuth;
    private Context context;
    Dialog dialog2;
    String data;
    boolean statuss=false;
    AuthCredential credential;
     private String accessToken,UserType,StrPhone;
    VerifyOtpData verifyOtpData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        inits();

        forgotArrowImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FirebaseApp.initializeApp(ForgetPasswordActivity.this);
        context = ForgetPasswordActivity.this;
        FirebaseApp.initializeApp(ForgetPasswordActivity.this);
        FirebaseApp.getApps(context);
        mAuth = FirebaseAuth.getInstance();

        employerEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    relativeForgetN.setVisibility(View.GONE);
                    relativeForgetEmail.setVisibility(View.VISIBLE);
                    statuss=true;



            }
        });

        employerMobileBtnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    relativeForgetN.setVisibility(View.VISIBLE);
                    relativeForgetEmail.setVisibility(View.GONE);

            }
        });

        GetOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statuss==true) {

                    getEmailOtpApi();
                  //
                }else{
                    StrPhone = phoneNumber.getText().toString();

                    if (phoneNumber.getText().toString().equals("")) {
                        Toast.makeText(ForgetPasswordActivity.this, "Please Enter Your Phone Number", Toast.LENGTH_SHORT).show();
                    } else {
                        forgetApi();


                    }
                }
            }
        });

        SharedPreferences getUserIdData = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        UserType = getUserIdData.getString("userType", "");
        Log.e("ForgetPassword", "ForgetPassword" + UserType);


    }


    private void getEmailOtpApi() {
        final ProgressDialog pd = new ProgressDialog(ForgetPasswordActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<PostEmailOtpModel> call = service.POST_EMAIL_OTP_MODEL_CALL(emailForget.getText().toString());

        call.enqueue(new Callback<PostEmailOtpModel>() {
            @Override
            public void onResponse(retrofit2.Call<PostEmailOtpModel> call, retrofit2.Response<PostEmailOtpModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        PostEmailOtpModel postEmailOtpModel = response.body();
                        String success = postEmailOtpModel.getSuccess();
                        String msg = postEmailOtpModel.getMessage();

                        Log.e("paramObject1", "strMessage.." +success);
                        if (success.equals("true")|| success.equals("True")) {
                            PostEmailOtpData postEmailOtpData = postEmailOtpModel.getData();
                            String otp = String.valueOf(postEmailOtpData.getOtp());
                            String email = postEmailOtpData.getEmail();

                           // sendVerificationCode("+91" + phoneNumber.getText().toString());
                            emailVerifyDialog(otp,email);
/*

                            Intent intent = new Intent(ForgetPasswordActivity.this, ResetPasswordActivity.class);
                            startActivity(intent);
*/

                        } else {
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                            pd.dismiss();

                            Log.e("user_id", "    False");
                        }

                    } else {
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
            public void onFailure(Call<PostEmailOtpModel> call, Throwable t) {
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

    private void emailVerifyDialog(String otps,String emailss) {
        Dialog emailVerifyDialog = new Dialog(ForgetPasswordActivity.this);
        emailVerifyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        emailVerifyDialog.setContentView(R.layout.email_verify_layout);
        AppCompatButton SubmitOTPBtn = emailVerifyDialog.findViewById(R.id.SubmitOTPBtn);
         OTPDialog = emailVerifyDialog.findViewById(R.id.OTPDialog);
        emailVerifyDialog.show();
        Window window = emailVerifyDialog.getWindow();
        if (window == null) return;
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = 900;
        params.height = 640;
        window.setAttributes(params);
        //    dialog.getWindow().setLayout(100, 100);
        emailVerifyDialog.getWindow().setBackgroundDrawableResource(R.drawable.popup_background);

        SubmitOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (OTPDialog.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Please enter otp", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String OTPS=otps;
                    String Emails=emailss;
                    OTPDialog.setText(otps);
                    VerifyOtpApi(OTPS,Emails);
                    emailVerifyDialog.dismiss();

                }
            }
        });



    }

    private void  VerifyOtpApi(String OTP,String Email) {

        final ProgressDialog pd = new ProgressDialog(ForgetPasswordActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<VerifyOtpModel> call = service.VERIFY_OTP_MODEL_CALL("otp_verify?email="+Email+"&otp="+OTP);

        call.enqueue(new Callback<VerifyOtpModel>() {
            @Override
            public void onResponse(Call<VerifyOtpModel> call, Response<VerifyOtpModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String success = (response.body().getSuccess());
                        String msg = (response.body().getMessage());
                        Log.e("hello", "success: " +success );

                        if (success.equals("true")|| (success.equals("True"))) {
                            VerifyOtpModel verifyOtpModel = response.body();
                             verifyOtpData = verifyOtpModel.getData();
                            String UserId = String.valueOf(verifyOtpData.getUserId());
                           String UserType = String.valueOf(verifyOtpData.getUserType());

                            Log.e("hello", "getData:///// " );
                            Intent intent=new Intent(getApplicationContext(), ResetPasswordActivity.class);
                            intent.putExtra("UserId",UserId);
                            intent.putExtra("UserType",UserType);
                            startActivity(intent);

                            // Id  = profileGetData.getId();

                            Toast.makeText(getApplicationContext(),"OTP Verification Successful", Toast.LENGTH_LONG).show();
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
            public void onFailure(Call<VerifyOtpModel> call, Throwable t) {
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


    PhoneAuthProvider.OnVerificationStateChangedCallbacks
                mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                phoneVerificationId = s;
                alert_dialog();

            }

            // this method is called when user
            // receive OTP from Firebase.
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                // below line is used for getting OTP code
                // which is sent in phone auth credentials.
                final String code = phoneAuthCredential.getSmsCode();
                signInWithPhoneAuthCredential(phoneAuthCredential);
                // checking if the code
                // is null or not.

                if (code != null) {

                }
            }
            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(ForgetPasswordActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                Toast.makeText(ForgetPasswordActivity.this, "Please try again", Toast.LENGTH_LONG).show();
            }
        };


    private void sendVerificationCode(String phoneNumber) {

        // this method is used for getting
        // OTP on user phone number.
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber, // first parameter is user's mobile number
                60, // second parameter is time limit for OTP
                // verification which is 60 seconds in our case.
                TimeUnit.SECONDS, // third parameter is for initializing units
                // for time period which is in seconds in our case.
                this, // this task will be excuted on Main thread.
                mCallBack // we are calling callback method when we recieve OTP for
                // auto verification of user.
        );

    }

    private void alert_dialog() {
        Dialog getOtpDialog = new Dialog(ForgetPasswordActivity.this);
        getOtpDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getOtpDialog.setContentView(R.layout.getotp_password_dialog);
        AppCompatButton verifyOtp = getOtpDialog.findViewById(R.id.verifyOtp);
        OtpTextView otp = getOtpDialog.findViewById(R.id.otp);

        getOtpDialog.show();
        Window window = getOtpDialog.getWindow();
        if (window == null) return;
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = 900;
        params.height = 640;
        window.setAttributes(params);
        //    dialog.getWindow().setLayout(100, 100);
        getOtpDialog.getWindow().setBackgroundDrawableResource(R.drawable.popup_background);

        verifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = otp.getOTP().toString();
                Log.e("test_sam_otp",code);
                verifyCode(code);
                getOtpDialog.dismiss();
            }
        });


    }

    private void verifyCode(String data) {
        String code = data;
        if ((!code.equals("")) && (code.length() == 6)) {

            PhoneAuthCredential credential =
                    PhoneAuthProvider.getCredential(phoneVerificationId, code);
            signInWithPhoneAuthCredential(credential);
        } else if (code.length() != 6) {
            Toast.makeText(this, "Please enter six digit valid otp", Toast.LENGTH_SHORT).show();

        }
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(ForgetPasswordActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete( Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgetPasswordActivity.this, "OTP Verification Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ForgetPasswordActivity.this, ResetPasswordActivity.class);
                            startActivity(intent);


                        } else {
                            Toast.makeText(ForgetPasswordActivity.this, "Please enter valid OTP", Toast.LENGTH_SHORT).show();
                            if (task.getException() instanceof
                                    FirebaseAuthInvalidCredentialsException) {
                            }
                        }
                    }
                });

    }


   private void forgetApi() {

        // show till load api data

        // Log.e("user_id", "  " + device_token + " " + userEmail + " " + userPassword);

        final ProgressDialog pd = new ProgressDialog(ForgetPasswordActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<ForgotPassword> call = service.FORGOT_PASSWORD_CALL(StrPhone,UserType);

        call.enqueue(new Callback<ForgotPassword>() {
            @Override
            public void onResponse(retrofit2.Call<ForgotPassword> call, retrofit2.Response<ForgotPassword> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {

                        String success = response.body().getSuccess();
                        String msg = response.body().getMessage();

                        Log.e("paramObject1", "strMessage.." +success);
                        if (success.equals("true")|| success.equals("True")) {
                           // sendVerificationCode("+91" + phoneNumber.getText().toString());
                            sendVerificationCode("+91" + phoneNumber.getText().toString());


                        } else {
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                            pd.dismiss();

                            Log.e("user_id", "    False");
                        }

                    } else {
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
            public void onFailure(Call<ForgotPassword> call, Throwable t) {
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
        GetOtpBtn = findViewById(R.id.GetOtpBtn);
        phoneNumber = findViewById(R.id.phoneNumber);
        forgotArrowImg = findViewById(R.id.forgotArrowImg);
        radioGroupBtnForget = findViewById(R.id.radioGroupBtnForget);
        employerMobileBtnBtn = findViewById(R.id.employerMobileBtnBtn);
        employerEmailBtn = findViewById(R.id.employerEmailBtn);
        relativeForgetN = findViewById(R.id.relativeForgetN);
        relativeForgetEmail = findViewById(R.id.relativeForgetEmail);
        emailForget = findViewById(R.id.emailForget);
    }
}