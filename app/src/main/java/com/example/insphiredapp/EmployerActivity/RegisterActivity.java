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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.insphiredapp.Api_Model.LoginModel;
import com.example.insphiredapp.Api_Model.RegisterModel;
import com.example.insphiredapp.Api_Model.RegisterModelData;
import com.example.insphiredapp.EmployeeActivity.DashboardActivityEmployee;
import com.example.insphiredapp.EmployeeActivity.EmployeeProfileActivity;
import com.example.insphiredapp.R;
import com.example.insphiredapp.retrofit.Api;
import com.example.insphiredapp.retrofit.Api_Client;
import com.google.android.gms.dynamic.IFragmentWrapper;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    TextView SignInEmployeeReg,companyNametext;
    RadioButton employeeRegBtn,employerRegBtn;
    RelativeLayout relativeCompanyNamereg;
    RadioGroup radioGroupBtn;
    AppCompatButton regSignUpButton;
    EditText firstNameRegEmpee,lastNameRegEmpee,emailRegEmpee, editPasswordRegEmpee,editConfirmPasswordRegEmpee,phoneEditEmpee;
    ImageView regPasswordHidden,regPasswordShow,regConfirmPHidden,regConfirmPShow;
    LinearLayout linearRegisterEmployee;
    String strAdmin = "employer",userTypeReg;
    String strFirstName,strLastName,strEmail, strPassword, strPhone;
    RegisterModelData registerModelData;
    public List<String> email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        inits();
        SignInEmployeeReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        regPasswordHidden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regPasswordShow.setVisibility(View.VISIBLE);
                regPasswordHidden.setVisibility(View.GONE);
                editPasswordRegEmpee.setTransformationMethod(null);
                editPasswordRegEmpee.setSelection(editPasswordRegEmpee.getText().length());
            }
        });

        regPasswordShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regPasswordHidden.setVisibility(View.VISIBLE);
                regPasswordShow.setVisibility(View.GONE);
                editPasswordRegEmpee.setTransformationMethod(new PasswordTransformationMethod());
                editPasswordRegEmpee.setSelection(editPasswordRegEmpee.getText().length());
            }
        });
        regConfirmPShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regConfirmPHidden.setVisibility(View.VISIBLE);
                regConfirmPShow.setVisibility(View.GONE);
                editConfirmPasswordRegEmpee.setTransformationMethod(new PasswordTransformationMethod());
                editConfirmPasswordRegEmpee.setSelection(editConfirmPasswordRegEmpee.getText().length());
            }
        });

        regConfirmPHidden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regConfirmPHidden.setVisibility(View.GONE);
                regConfirmPShow.setVisibility(View.VISIBLE);
                editConfirmPasswordRegEmpee.setTransformationMethod(null);
                editConfirmPasswordRegEmpee.setSelection(editConfirmPasswordRegEmpee.getText().length());
            }
        });
        radioGroupBtn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (employerRegBtn.isChecked()) {

                    strAdmin = "employer";
                    Log.e("emplr", "onCheckedChanged:" + strAdmin  );
                }
                else if (employeeRegBtn.isChecked())
                {
                    strAdmin = "employee";
                    Log.e("emple", "onCheckedChanged:" + strAdmin  );
                }
            }
        });
        regSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strFirstName = firstNameRegEmpee.getText().toString();
                strEmail = emailRegEmpee.getText().toString();
                strPassword = editPasswordRegEmpee.getText().toString();
                strPhone = phoneEditEmpee.getText().toString();
                if (validation())
                {
                 registerEmployee_Api();

                }
            }
        });

    }

    private boolean validation() {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String MobilePattern = "[0-9]{10}";
        if (firstNameRegEmpee.getText().toString().equals("")) {
            Toast.makeText(RegisterActivity.this, "Please enter  name", Toast.LENGTH_SHORT).show();
            return false; }
        else if (emailRegEmpee.getText().toString().equals(""))
        {
            Toast.makeText(RegisterActivity.this, "Please Enter  Email Address", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!emailRegEmpee.getText().toString().matches(emailPattern)) {
            Toast.makeText(RegisterActivity.this, "Please Enter Valid Email Address", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!phoneEditEmpee.getText().toString().matches(MobilePattern)) {
            Toast.makeText(RegisterActivity.this, "Please enter valid 10 digit phone number", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (editPasswordRegEmpee.getText().toString().equals("")) {
            Toast.makeText(RegisterActivity.this, "Please enter  password", Toast.LENGTH_SHORT).show();
            return false;
        } else if (editConfirmPasswordRegEmpee.getText().toString().equals("")) {
            Toast.makeText(RegisterActivity.this, "Please enter confirm password", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!editPasswordRegEmpee.getText().toString().equals(editConfirmPasswordRegEmpee.getText().toString())) {
            Toast.makeText(this, "Confirm password is not correct", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }



    private void registerEmployee_Api() {

        final ProgressDialog pd = new ProgressDialog(RegisterActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<RegisterModel> call = service.REGISTER_MODEL_CALL(strFirstName,strEmail,strPassword,strPhone,strAdmin);

        call.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(retrofit2.Call<RegisterModel> call, retrofit2.Response<RegisterModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        RegisterModel registerModel = response.body();
                        String success = registerModel.getSuccess();
                        String message = registerModel.getMessage();
                        Log.e("Register", "Success" + success);

                        if (success.equals("true") || success.equals("True")) {
                            if (strAdmin.equals("employer"))
                            {
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                            else if(strAdmin.equals("employee")){
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }

                        }
                            // Toast.makeText(getApplicationContext(), "userType"  +" "+userType, Toast.LENGTH_SHORT).show();
                        else {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
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
            public void onFailure(Call<RegisterModel> call, Throwable t) {
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
        firstNameRegEmpee = findViewById(R.id.firstNameRegEmpee);
      //  lastNameRegEmpee = findViewById(R.id.lastNameRegEmpee);
        emailRegEmpee = findViewById(R.id.emailRegEmpee);
        phoneEditEmpee = findViewById(R.id.editProfilePhone);
        SignInEmployeeReg = findViewById(R.id.SignInEmployeeReg);
        radioGroupBtn = findViewById(R.id.radioGroupBtn);
        regSignUpButton = findViewById(R.id.regSignUpButton);
        employerRegBtn = findViewById(R.id.employerRegBtn);
       // companyNametext = findViewById(R.id.companyNametext);
        employeeRegBtn = findViewById(R.id.employeeRegBtn);
    //    relativeCompanyNamereg = findViewById(R.id.relativeCompanyNamereg);
        editPasswordRegEmpee = findViewById(R.id.editPasswordRegEmpee);
        editConfirmPasswordRegEmpee = findViewById(R.id.editConfirmPasswordRegEmpee);
        regPasswordHidden = findViewById(R.id.regPasswordHidden);
        regPasswordShow = findViewById(R.id.regPasswordShow);
        regConfirmPHidden = findViewById(R.id.regConfirmPHidden);
        regConfirmPShow = findViewById(R.id.regConfirmPShow);
        linearRegisterEmployee = findViewById(R.id.linearRegisterEmployee);

    }
}