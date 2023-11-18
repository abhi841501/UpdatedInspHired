package com.example.insphiredapp.EmployerActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
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
import androidx.appcompat.widget.AppCompatImageView;

import com.example.insphiredapp.Api_Model.RegisterModel;
import com.example.insphiredapp.Api_Model.RegisterModelData;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import in.aabhasjindal.otptextview.OtpTextView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
   private TextView SignInEmployeeReg,companyNametext,editProfileUploadVideo,ProfilePreviousDetailstxt;
    RadioButton employeeRegBtn,employerRegBtn;
    RelativeLayout relativeCompanyNamereg,relativeUploadd,RelativeUploadVideo;
    RadioGroup radioGroupBtn;
    AppCompatButton regSignUpButton,sendOTPReg,uploadReg,uploadPdetailsBtn;
   private EditText firstNameRegEmpee,lastNameRegEmpee,emailRegEmpee, editPasswordRegEmpee,editConfirmPasswordRegEmpee,phoneNumber;
    ImageView regPasswordHidden,regPasswordShow,regConfirmPHidden,regConfirmPShow;
    LinearLayout linearRegisterEmployee;
    String strAdmin = "employee",userTypeReg;
   private String strFirstName,strLastName,strEmail, strPassword, strPhone,StrTEmployer,StrJobseeker;
    RegisterModelData registerModelData;
    ImageView regArrowBackImg;
    public List<String> email;
    private static final int REQUEST_VIDEO_PICK = 1001;
    private static final int REQUEST_FILE_PICK = 12;
    private Uri selectedVideoUri;
    File profile_Identity;
    File profile_fileVideo;
    private Uri pdfUri;
    public String phoneVerificationId;
    public FirebaseAuth mAuth;
    private Context context;
    Dialog dialog2;
    String data;
    AuthCredential credential;
    boolean clickedOtp=false;
    boolean clickedVideo=false;
    Dialog getOtpDialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        inits();

       StrTEmployer = getIntent().getStringExtra("TempEmployer");
        Log.e("hejfda", ""+StrTEmployer );
        StrJobseeker = getIntent().getStringExtra("temjc");
        employeeRegBtn.setChecked(true);


                if (StrTEmployer.equals("TemJPost")) {
                    employerRegBtn.setChecked(true);

                    employerRegBtn.isChecked();

                    strAdmin = "employer";
                    Log.e("emple", "onCheckedChanged:" + strAdmin);
                    relativeUploadd.setVisibility(View.GONE);
                    RelativeUploadVideo.setVisibility(View.GONE);
                }

                else
                    {
                        employeeRegBtn.setChecked(true);
                        employeeRegBtn.isChecked();
                        strAdmin = "employee";
                        Log.e("emple", "onCheckedChanged:" + strAdmin  );
                        relativeUploadd.setVisibility(View.VISIBLE);
                        RelativeUploadVideo.setVisibility(View.VISIBLE);
                    }





        FirebaseApp.initializeApp(RegisterActivity.this);
        context = RegisterActivity.this;
        FirebaseApp.initializeApp(RegisterActivity.this);
        FirebaseApp.getApps(context);
        mAuth = FirebaseAuth.getInstance();

        regArrowBackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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


        sendOTPReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedOtp=true;

                if (phoneNumber.getText().toString().equals("")) {
                    Toast.makeText(context, "Please enter mobile number", Toast.LENGTH_SHORT).show();

                }
                else
                {
                   // sendVerificationCode("+91" + phoneNumber.getText().toString());
                    sendVerificationCode("+27" + phoneNumber.getText().toString());


                }
            }
        });

        uploadPdetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedVideo=true;

                Dialog selectPreviousFile = new Dialog(RegisterActivity.this);
                selectPreviousFile.requestWindowFeature(Window.FEATURE_NO_TITLE);
                selectPreviousFile.setContentView(R.layout.upload_previous_details);

                TextView select_Pfile_layout = selectPreviousFile.findViewById(R.id.select_Pfile_layout);

                selectPreviousFile.show();
                Window window = selectPreviousFile.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                select_Pfile_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectPdf();
                        selectPreviousFile.dismiss();
                    }
                });

            }
        });

        uploadReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog selectVideo = new Dialog(RegisterActivity.this);
                selectVideo.requestWindowFeature(Window.FEATURE_NO_TITLE);
                selectVideo.setContentView(R.layout.upload_video_layout);

                TextView choose_video_layout = selectVideo.findViewById(R.id.choose_video_layout);

                selectVideo.show();
                Window window = selectVideo.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                choose_video_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openGalleryForVideo();
                        selectVideo.dismiss();
                    }
                });

            }
        });


        radioGroupBtn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (employerRegBtn.isChecked()) {

                    strAdmin = "employer";
                    Log.e("emplr", "onCheckedChanged:" + strAdmin  );
                    relativeUploadd.setVisibility(View.GONE);
                    RelativeUploadVideo.setVisibility(View.GONE);

                }
                else if (employeeRegBtn.isChecked())
                {
                    strAdmin = "employee";
                    Log.e("emple", "onCheckedChanged:" + strAdmin  );
                    relativeUploadd.setVisibility(View.VISIBLE);
                    RelativeUploadVideo.setVisibility(View.VISIBLE);
                }
            }
        });
        regSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strFirstName = firstNameRegEmpee.getText().toString();
                strEmail = emailRegEmpee.getText().toString();
                strPassword = editPasswordRegEmpee.getText().toString();
                strPhone = phoneNumber.getText().toString();
                if (employerRegBtn.isChecked()) {

                    strAdmin = "employer";
                    Log.e("emplr", "onCheckedChanged:" + strAdmin  );
                    relativeUploadd.setVisibility(View.GONE);
                    RelativeUploadVideo.setVisibility(View.GONE);

                    if (validation1())
                    {
                        registerEmployee_Api();

                    }
                }
                else if (employeeRegBtn.isChecked())
                {
                    strAdmin = "employee";
                    Log.e("emple", "onCheckedChanged:" + strAdmin  );
                    relativeUploadd.setVisibility(View.VISIBLE);
                    RelativeUploadVideo.setVisibility(View.VISIBLE);
                    if (validation())
                    {
                        registerEmployee_Api();

                    }
                }


            }
        });

    }

    private boolean validation1() {
        String MobilePattern = "[0-9]{10}";
        if (firstNameRegEmpee.getText().toString().equals("")) {
            Toast.makeText(RegisterActivity.this, "Please enter name", Toast.LENGTH_SHORT).show();
            return false; }
        else if (emailRegEmpee.getText().toString().equals(""))
        {
            Toast.makeText(RegisterActivity.this, "Please enter email Address", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!phoneNumber.getText().toString().matches(MobilePattern)) {
            Toast.makeText(RegisterActivity.this, "Please enter valid 10 digit phone number", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (clickedOtp==false)
        {
            Toast.makeText(RegisterActivity.this, "Please verify your number with otp", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (editPasswordRegEmpee.getText().toString().equals("")) {
            Toast.makeText(RegisterActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
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

    PhoneAuthProvider.OnVerificationStateChangedCallbacks
            // initializing our callbacks for on
            // verification callback method.
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


        // below method is used when
        // OTP is sent from Firebase
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            // when we receive the OTP it
            // contains a unique id which
            // we are storing in our string
            // which we have already created.
/*            Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            */
            phoneVerificationId = s;
            //viewFlipper.setDisplayedChild(1);

            openDialog();


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
                //   Forget_Login();
                // if the code is not null then
                // we are setting that code to
                // our OTP edittext field.
//                edtOTP.setText(code);

                // after setting this code
                // to OTP edittext field we
                // are calling our verifycode method.
//                verifyCode(code);
            }
        }

        // this method is called when firebase doesn't
        // sends our OTP code due to any error or issue.
        @Override
        public void onVerificationFailed(FirebaseException e) {
            // displaying error message with firebase exception.

              /*  Intent intent = new Intent(MainActivity.this, NameEmailActivity.class);
                startActivity(intent);*/
            Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            Toast.makeText(RegisterActivity.this, "Please try again", Toast.LENGTH_LONG).show();
        }
    };



    private void selectPdf() {
        Intent pdfIntent = new Intent(Intent.ACTION_GET_CONTENT);
        pdfIntent.setType("application/pdf");
        // pdfIntent.setType("application/doc"); // Uncomment this line if you want to support DOC files
        pdfIntent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(pdfIntent, REQUEST_FILE_PICK);
    }

    private void openGalleryForVideo() {
        Intent videoPickerIntent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        videoPickerIntent.setType("video/*");
        startActivityForResult(videoPickerIntent, REQUEST_VIDEO_PICK);
    }

    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_VIDEO_PICK && resultCode == RESULT_OK) {
            Uri videoUri = data.getData();
            if (videoUri != null) {
                selectedVideoUri = data.getData();
                if (selectedVideoUri != null) {
                  //  editProfileUploadVideo.setText("Uploaded successfully");
                    String uriString = videoUri.toString();

                    Log.e("videoUpload", "video..." + uriString);

                    String videoName = null;
                    String filePath = null; // Th
                    if (uriString.startsWith("content://")) {
                        Cursor cursor = null;
                        try {
                            cursor = getContentResolver().query(videoUri, null, null, null, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                videoName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                editProfileUploadVideo.setText(videoName);

                                // Get the actual file path from the content URI (Display name might not be the actual path)
                                // This part needs to be modified to handle video files
                                InputStream inputStream = getContentResolver().openInputStream(videoUri);
                                File cacheDir = getCacheDir();

                                // You can change the file extension to ".mp4" for video files
                                File tempFile = File.createTempFile("temp", ".mp4", cacheDir);
                                FileOutputStream outputStream = new FileOutputStream(tempFile);

                                byte[] buffer = new byte[1024];
                                int bytesRead;

                                while ((bytesRead = inputStream.read(buffer)) != -1) {
                                    outputStream.write(buffer, 0, bytesRead);
                                }

                                filePath = tempFile.getAbsolutePath();
                                profile_fileVideo = new File(filePath);
                                Log.e("TAGG", "onActivityResult: "+profile_fileVideo);

                                cursor.close();

                                // Now you have the file path in 'filePath'.
                                // You can proceed with uploading the video using 'filePath'.

                                // Example: You can upload the video to a server using a library like Retrofit
                                // RetrofitUploadService.uploadVideo(filePath);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (cursor != null) {
                                cursor.close();
                            }
                        }
                    }

            } else {
                    Toast.makeText(getApplicationContext(), "Failed to select video", Toast.LENGTH_LONG).show();
                }
            }}

                if (requestCode == REQUEST_FILE_PICK && resultCode == RESULT_OK) {
                    Uri pdfUri = data.getData();
                    String uriString = pdfUri.toString();

                    Log.e("pdfUpload", "pdf..." + uriString);

                    String pdfName = null;
                    String filePath = null; // This will store the actual file path
                    if (uriString.startsWith("content://")) {
                        Cursor myCursor = null;
                        try {
                            myCursor = getContentResolver().query(pdfUri, null, null, null, null);
                            if (myCursor != null && myCursor.moveToFirst()) {
                                pdfName = myCursor.getString(myCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                ProfilePreviousDetailstxt.setText("Uploaded successfully");

                                // Get the actual file path from the content URI (Display name might not be the actual path)
                                filePath = myCursor.getString(myCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));

                                // Correcting the path creation - content URIs don't directly represent file paths
                                InputStream inputStream = getContentResolver().openInputStream(pdfUri);
                                File cacheDir = getCacheDir();
                                File tempFile = File.createTempFile("temp", ".pdf", cacheDir);
                                FileOutputStream outputStream = new FileOutputStream(tempFile);
                                byte[] buffer = new byte[1024];
                                int bytesRead;
                                while ((bytesRead = inputStream.read(buffer)) != -1) {
                                    outputStream.write(buffer, 0, bytesRead);
                                }
                                filePath = tempFile.getAbsolutePath();

                                profile_Identity = new File(filePath);

                                myCursor.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (myCursor != null) {
                                myCursor.close();
                            }
                        }

                }
            }
        }



    private void openDialog() {
         getOtpDialog1 = new Dialog(RegisterActivity.this);
        getOtpDialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getOtpDialog1.setContentView(R.layout.reg_enterotp_popup);
        AppCompatImageView crossOtpreg = getOtpDialog1.findViewById(R.id.crossOtpreg);
        AppCompatButton verifyOtpReg = getOtpDialog1.findViewById(R.id.verifyOtpReg);
        OtpTextView enterOtpReg = getOtpDialog1.findViewById(R.id.enterOtpReg);

        verifyOtpReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = enterOtpReg.getOTP().toString();
                Log.e("test_sam_otp",code);
                verifyCode(code);

            }
        });


        getOtpDialog1.show();
        Window window = getOtpDialog1.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        crossOtpreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOtpDialog1.dismiss();
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
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete( Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();
                            Toast.makeText(RegisterActivity.this, "Authentication successful.", Toast.LENGTH_SHORT).show();
                            getOtpDialog1.dismiss();

                         /*   Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);

                            startActivity(intent);*/


                        } else {
                            Toast.makeText(RegisterActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                            if (task.getException() instanceof
                                    FirebaseAuthInvalidCredentialsException) {
                            }
                        }
                    }
                });
    }

    private boolean validation() {
        //String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String MobilePattern = "[0-9]{10}";
        if (firstNameRegEmpee.getText().toString().equals("")) {
            Toast.makeText(RegisterActivity.this, "Please enter name", Toast.LENGTH_SHORT).show();
            return false; }
        else if (emailRegEmpee.getText().toString().equals(""))
        {
            Toast.makeText(RegisterActivity.this, "Please enter email Address", Toast.LENGTH_SHORT).show();
            return false;
        }
      /*  else if (!emailRegEmpee.getText().toString().matches(emailPattern)) {
            Toast.makeText(RegisterActivity.this, "Please Enter Valid Email Address", Toast.LENGTH_SHORT).show();
            return false;
        }*/
        else if (!phoneNumber.getText().toString().matches(MobilePattern)) {
            Toast.makeText(RegisterActivity.this, "Please enter valid 10 digit phone number", Toast.LENGTH_SHORT).show();
            return false;
        }

        else if (clickedOtp==false)
        {
            Toast.makeText(RegisterActivity.this, "Please verify your number with otp", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (clickedVideo==false)
        {
            Toast.makeText(RegisterActivity.this, "Please upload your previous company details", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (editPasswordRegEmpee.getText().toString().equals("")) {
            Toast.makeText(RegisterActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
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

        MultipartBody.Part profVideo = null;
        MultipartBody.Part profIdent = null;

        try {

            if (profile_fileVideo == null) {
                profVideo = MultipartBody.Part.createFormData("intro_ideo", "", RequestBody.create(MediaType.parse("video/mp4"), ""));
                Log.e("conversionException", "error1");
            } else {
                profVideo = MultipartBody.Part.createFormData("intro_ideo", profile_fileVideo.getName(), RequestBody.create(MediaType.parse("video/mp4"),profile_fileVideo));
                Log.e("conversionException", "intro_video"+profile_fileVideo);
            }
            if (profile_Identity == null) {
                // If no file is selected (handling the case when profile_fileImage is null)
                profIdent = MultipartBody.Part.createFormData("id_pdf", "", RequestBody.create(MediaType.parse("application/pdf"), ""));
                Log.e("conversionException", "error1");
            } else {
                // If a file is selected
                profIdent = MultipartBody.Part.createFormData("id_pdf", profile_Identity.getName(), RequestBody.create(MediaType.parse("application/pdf"), profile_Identity));
                Log.e("conversionException", "error2");
            }

        } catch (Exception e) {
            Log.e("conversionException", "error" + e.getMessage());
        }
        RequestBody RName = RequestBody.create(MediaType.parse("text/plain"), strFirstName);
        RequestBody REmail = RequestBody.create(MediaType.parse("text/plain"),strEmail);
        RequestBody RPassword = RequestBody.create(MediaType.parse("text/plain"), strPassword);
        RequestBody RPhone = RequestBody.create(MediaType.parse("text/plain"), strPhone);
        RequestBody RAdmin = RequestBody.create(MediaType.parse("text/plain"), strAdmin);


        final ProgressDialog pd = new ProgressDialog(RegisterActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<RegisterModel> call = service.REGISTER_MODEL_CALL(RName,REmail,RPassword,RPhone,profVideo,profIdent,RAdmin);

        call.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
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
        phoneNumber = findViewById(R.id.phoneNumber);
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
        sendOTPReg = findViewById(R.id.sendOTPReg);
        uploadReg = findViewById(R.id.uploadReg);
        regArrowBackImg = findViewById(R.id.regArrowBackImg);
        ProfilePreviousDetailstxt = findViewById(R.id.ProfilePreviousDetailstxt);
        editProfileUploadVideo = findViewById(R.id.editProfileUploadVideo);
        uploadPdetailsBtn = findViewById(R.id.uploadPdetailsBtn);
        relativeUploadd = findViewById(R.id.relativeUploadd);
        RelativeUploadVideo = findViewById(R.id.RelativeUploadVideo);

    }
}