package com.example.insphiredapp.EmployeeActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.insphiredapp.Adapter.NonNull;
import com.example.insphiredapp.Api_Model.EmployeeProfileModelFirst;
import com.example.insphiredapp.Api_Model.GetCategoryModel;
import com.example.insphiredapp.Api_Model.GetCategoryModelData;
import com.example.insphiredapp.Api_Model.GetProfieEmpApiData;
import com.example.insphiredapp.Api_Model.PostProfieEmpApi;
import com.example.insphiredapp.Api_Model.PostProfieEmpDataApi;
import com.example.insphiredapp.Api_Model.ProfileEmployeeModelFirstData;
import com.example.insphiredapp.EmployerActivity.WebViewEmployerActivity;
import com.example.insphiredapp.R;
import com.example.insphiredapp.retrofit.Api;
import com.example.insphiredapp.retrofit.Api_Client;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeProfileActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int GALLERY_REQUEST_CODE = 200;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 101;
    ImageView backArrowProfileEmployee;
    AppCompatButton submitEmployeeProfile;
    ImageView addImage;
    TextView empName, empEmail, empNo;
    EditText EmpUserIdpp, EmpDailyRatepp, EmpHistorypp, EmpAddresspp;
    CheckBox checkboxpp;
    CircleImageView userProfileEmployeeee;
    Spinner Spinner11;
    String catIds;
    TextView uploadCV, uploadCvEdit, termCondEmployee;
    List<GetCategoryModelData> getCategoryModelDataList;
    List<String> CatList = new ArrayList<>();
    ImageView imageView;
    File profile_fileImage;
    File profile_fileImage1;
    GetProfieEmpApiData getProfieEmpApiData;
    PostProfieEmpDataApi postProfieEmpDataApi;
    private Uri pdfUri;
    private String user_id, strCheck = "0";
    private Button cameraButton, galleryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);
        inits();

        backArrowProfileEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SharedPreferences getUserIdData = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        user_id = getUserIdData.getString("Id", "");
        Log.e("changePassword", "Id" + user_id);
        getEmployeeProfileApi();
        getCategoryApi();

        Spinner11.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //  Toast.makeText(getActivity(), "Country Spinner Working **********", Toast.LENGTH_SHORT).show();

                String item = Spinner11.getSelectedItem().toString();
                if (item.equals(getResources().getString(R.string.category)))
                {

                    // int spinnerPosition = dAdapter.getPosition(compareValue);
                    // spinner_category.setSelection(Integer.parseInt("Select Category"));
                }   else
                {

                    catIds = (String.valueOf(getCategoryModelDataList.get(i).getId()));
                    //Toast.makeText(getActivity(), "leaveId :"+absencelist.get(i).getLeaveName()+leaveId, Toast.LENGTH_SHORT).show();

                  /*  getStateSearch();
                    Log.e("LIST_ID_COUNTRY",country_sp_id+"ID");
                    switch_my.setChecked(false);*/

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });


        termCondEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmployeeProfileActivity.this, WebViewEmployerActivity.class);
                startActivity(intent);
            }
        });

        submitEmployeeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkboxpp.isChecked()) {
                    strCheck = "1";
                } else {
                    strCheck = "0";
                }
               //validateSpinnerSelection();
                if (validation()) {
                    PostProfileApi();
                }
            }
        });


        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog addProfileUpdate = new Dialog(EmployeeProfileActivity.this);
                addProfileUpdate.requestWindowFeature(Window.FEATURE_NO_TITLE);
                addProfileUpdate.setContentView(R.layout.gallary_camera_popup);

                imageView = addProfileUpdate.findViewById(R.id.imageView);
                cameraButton = addProfileUpdate.findViewById(R.id.cameraButton);
                galleryButton = addProfileUpdate.findViewById(R.id.galleryButton);

                addProfileUpdate.show();
                Window window = addProfileUpdate.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                galleryButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //  openGallery(PICK_IMAGE);
                        openGallery();
                        addProfileUpdate.dismiss();
                    }
                });

                cameraButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        requestCameraPermission();
                        addProfileUpdate.dismiss();
                    }
                });
            }
        });

        uploadCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog selectFile = new Dialog(EmployeeProfileActivity.this);
                selectFile.requestWindowFeature(Window.FEATURE_NO_TITLE);
                selectFile.setContentView(R.layout.file_manager);

                TextView select_file_layout = selectFile.findViewById(R.id.select_file_layout);

                selectFile.show();
                Window window = selectFile.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                select_file_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectPdf();
                        selectFile.dismiss();
                    }
                });

            }
        });
    }

 /*   private void validateSpinnerSelection() {
        String selectedOption = Spinner11.getSelectedItem().toString();
       else {
            // Proceed with the action using the selected option.
            // You can use the value of 'selectedOption' here.
           // Toast.makeText(this, "Selected option: " + selectedOption, Toast.LENGTH_SHORT).show();
        }
    }*/

    private boolean validation() {
        String name = null;
        String cvFilePathOrUri = null;
        String selectedOption = Spinner11.getSelectedItem().toString();

        if (userProfileEmployeeee.getDrawable()== null) {
            Toast.makeText(getApplicationContext(), "Please upload your image", Toast.LENGTH_SHORT).show();
            return false;
        } else if (EmpUserIdpp.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter empId", Toast.LENGTH_SHORT).show();
            return false;
        }    else if (selectedOption.equals(getResources().getString(R.string.category))) {
            // Display a Toast message indicating that a selection is required.
            Toast.makeText(this, "Please select category", Toast.LENGTH_SHORT).show();
            return false;
        }else if (EmpDailyRatepp.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter the daily rate", Toast.LENGTH_SHORT).show();
            return false;
        } else if (profile_fileImage1 == null) {
            Toast.makeText(getApplicationContext(), "Please upload your CV", Toast.LENGTH_SHORT).show();
            return false;
        } else if (EmpHistorypp.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter work experience", Toast.LENGTH_SHORT).show();
            return false;
        } else if (EmpAddresspp.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter your address", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!checkboxpp.isChecked()) {
            Toast.makeText(getApplicationContext(), "Please accept the terms & conditions", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    private void selectPdf() {
        Intent pdfIntent = new Intent(Intent.ACTION_GET_CONTENT);
        pdfIntent.setType("application/pdf");
        // pdfIntent.setType("application/doc"); // Uncomment this line if you want to support DOC files
        pdfIntent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(pdfIntent, 12);
    }

    private void getEmployeeProfileApi() {

        final ProgressDialog pd = new ProgressDialog(EmployeeProfileActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<EmployeeProfileModelFirst> call = service.EMPLOYEE_PROFILE_MODEL_FIRST_CALL("user_show_data?user_id=" + user_id);

        call.enqueue(new Callback<EmployeeProfileModelFirst>() {
            @Override
            public void onResponse(Call<EmployeeProfileModelFirst> call, Response<EmployeeProfileModelFirst> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {

                        String success = response.body().getSuccess();
                        String message = response.body().getMessage();
                        Log.e("hello", "success: " + success);

                        if (success.equals("True") || success.equals("true")) {
                            EmployeeProfileModelFirst employeeProfileModelFirst = response.body();
                            ProfileEmployeeModelFirstData profileEmployeeModelFirstData = employeeProfileModelFirst.getData();

                            Log.e("hello", "getData: ");

                            String strFirstname = profileEmployeeModelFirstData.getFirstName();
                            String strEmail = profileEmployeeModelFirstData.getEmail();
                            String strPhone = profileEmployeeModelFirstData.getMobile();

                            empName.setText(strFirstname);
                            empEmail.setText(strEmail);
                            empNo.setText(strPhone);
                           // Glide.with(getApplicationContext()).load(Api_Client.BASE_URL_IMAGES + profileEmployeeModelFirstData.getEmpImage()).placeholder(R.drawable.ic_launcher_foreground).into(userProfileEmployeeee);

                        } else {
                            // Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
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
            public void onFailure(Call<EmployeeProfileModelFirst> call, Throwable t) {
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


    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
    }

    private void requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        } else {
            openCamera();
        }
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
        }
    }

    private void getCategoryApi() {

        final ProgressDialog pd = new ProgressDialog(EmployeeProfileActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<GetCategoryModel> call = service.GET_CATEGORY_MODEL_CALL();

        call.enqueue(new Callback<GetCategoryModel>() {
            @Override
            public void onResponse(Call<GetCategoryModel> call, Response<GetCategoryModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String success = response.body().getSuccess();
                        String message = response.body().getMessage();
                        Log.e("hello", "success: " + success);

                        if (success.equals("True") || success.equals("true")) {
                            GetCategoryModel getCategoryModel = response.body();
                            getCategoryModelDataList = getCategoryModel.getData();

                            for (int i = 0; i<getCategoryModelDataList.size(); i++) {

                                CatList.add(getCategoryModelDataList.get(i).getCatName());
                               // catIds = (String.valueOf(getCategoryModelDataList.get(i).getId()));

                                Log.e("catIds", "onResponse: "+catIds );

                            }

                            spinnerAdapter dAdapter = new spinnerAdapter(EmployeeProfileActivity.this, R.layout.custom_spinner, CatList);
                            dAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            dAdapter.addAll(CatList);
                            dAdapter.add(getResources().getString(R.string.category));
                            Spinner11.setAdapter(dAdapter);
                            Spinner11.setSelection(dAdapter.getCount());

                            Log.e("hello", "getData: ");
                        } else {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
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
            public void onFailure(Call<GetCategoryModel> call, Throwable t) {
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

    private void PostProfileApi() {

        final ProgressDialog pd = new ProgressDialog(EmployeeProfileActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        MultipartBody.Part profileImage = null;
        MultipartBody.Part profileCV = null;

        try {

            if (profile_fileImage == null) {
                profileImage = MultipartBody.Part.createFormData("emp_image", "", RequestBody.create(MediaType.parse("image/*"), ""));
                Log.e("conversionException", "error1");


            } else  {
                profileImage = MultipartBody.Part.createFormData("emp_image", profile_fileImage.getName(), RequestBody.create(MediaType.parse("image/*"), profile_fileImage));
                Log.e("conversionException", "error2");
            }
            if (profile_fileImage1 == null) {
                // If no file is selected (handling the case when profile_fileImage is null)
                profileCV = MultipartBody.Part.createFormData("emp_cv", "", RequestBody.create(MediaType.parse("application/pdf"), ""));
                Log.e("conversionException", "error1");
            } else {
                // If a file is selected
                profileCV = MultipartBody.Part.createFormData("emp_cv", profile_fileImage1.getName(), RequestBody.create(MediaType.parse("application/pdf"), profile_fileImage1));
                Log.e("conversionException", "error2");
            }

        } catch (Exception e) {
            Log.e("conversionException", "error" + e.getMessage());
        }
        RequestBody UserId = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody EmpId = RequestBody.create(MediaType.parse("text/plain"), EmpUserIdpp.getText().toString());
        RequestBody CatId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(catIds));
        RequestBody DailyRate = RequestBody.create(MediaType.parse("text/plain"), EmpDailyRatepp.getText().toString());
        RequestBody EmpHistory = RequestBody.create(MediaType.parse("text/plain"), EmpHistorypp.getText().toString());
        RequestBody EmpAddress = RequestBody.create(MediaType.parse("text/plain"), EmpAddresspp.getText().toString());
        RequestBody TermCondition = RequestBody.create(MediaType.parse("text/plain"), strCheck);


        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<PostProfieEmpApi> call = service.POST_PROFIE_EMP_API_CALL(UserId,
                profileImage,
                EmpId,
                CatId,
                DailyRate,
                profileCV,
                EmpHistory,
                EmpAddress,
                TermCondition);

        Log.e("check//", "UserId" + UserId);
        Log.e("check//", "profileImage" + profileImage);
        Log.e("check//", "CatId" + CatId);
        Log.e("check//", "DailyRate" + DailyRate);
        Log.e("check//", "profileCV" + profileCV);
        Log.e("check//", "EmpHistory" + EmpHistory);
        Log.e("check//", "EmpAddress" + EmpAddress);
        Log.e("check//", "TermCondition" + TermCondition);

        call.enqueue(new Callback<PostProfieEmpApi>() {
            @Override
            public void onResponse(Call<PostProfieEmpApi> call, Response<PostProfieEmpApi> response) {
                pd.dismiss();

                try {
                    if (response.isSuccessful()) {
                        String success = response.body().getSuccess();
                        String msg = response.body().getMessage();

                        if (success.equals("True") || success.equals("true")) {
                            Log.e("myprofile", "success " + success);
                            PostProfieEmpApi postProfieEmpApi = response.body();
                             postProfieEmpDataApi = postProfieEmpApi.getData();

                            Toast.makeText(EmployeeProfileActivity.this, msg, Toast.LENGTH_SHORT).show();



                            Intent intent = new Intent(EmployeeProfileActivity.this, DashboardActivityEmployee.class);
                            startActivity(intent);


                        } else {
                            pd.dismiss();
                            Toast.makeText(EmployeeProfileActivity.this, msg, Toast.LENGTH_LONG).show();

                        }

                    } else {

                        try {

                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(EmployeeProfileActivity.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(EmployeeProfileActivity.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(EmployeeProfileActivity.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(EmployeeProfileActivity.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(EmployeeProfileActivity.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(EmployeeProfileActivity.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(EmployeeProfileActivity.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(EmployeeProfileActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        } catch (Exception e) {
                        }
                    }
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (JsonSyntaxException exception) {
                    exception.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(@NonNull Call<PostProfieEmpApi> call, Throwable t) {

                if (t instanceof IOException) {

                    Toast.makeText(EmployeeProfileActivity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                } else {

                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(EmployeeProfileActivity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void inits() {
        backArrowProfileEmployee = findViewById(R.id.backArrowProfileEmployee);
        submitEmployeeProfile = findViewById(R.id.submitEmployeeProfile);
        addImage = findViewById(R.id.addImage);
        userProfileEmployeeee = findViewById(R.id.userProfileEmployeeee);
        uploadCV = findViewById(R.id.uploadCV);
        empName = findViewById(R.id.empName);
        empEmail = findViewById(R.id.empEmail);
        empNo = findViewById(R.id.empNo);
        Spinner11 = findViewById(R.id.Spinner11);
        uploadCvEdit = findViewById(R.id.uploadCvEdit);
        termCondEmployee = findViewById(R.id.termCondEmployee);
        checkboxpp = findViewById(R.id.checkboxpp);
        EmpUserIdpp = findViewById(R.id.EmpUserIdpp);
        EmpDailyRatepp = findViewById(R.id.EmpDailyRatepp);
        EmpHistorypp = findViewById(R.id.EmpHistorypp);
        EmpAddresspp = findViewById(R.id.EmpAddresspp);
    }

    @SuppressLint("Range")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                // Get the captured image as a Bitmap
                // Uri selectedImage = data.getData();
                // imageview.setImageURI(selectedImage);
                Bitmap capturedImage = (Bitmap) extras.get("data");
                Log.e("cameara", "  cam   " + capturedImage);

                // Display the captured image in the ImageView
                userProfileEmployeeee.setImageBitmap(capturedImage);

                // Save the captured image as a file
                profile_fileImage = saveBitmapToFile(capturedImage);

            }
        } else if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            // Gallery selection successful, get the selected image URI
            Uri selectedImageUri = data.getData();
            Log.e("cameara", "  gall   " + selectedImageUri);
            userProfileEmployeeee.setImageURI(selectedImageUri);

            // Get the file path from the URI
            String selectedImagePath = getPath(selectedImageUri);
            profile_fileImage = new File(selectedImagePath);

            // Load the image using Glide
            Glide.with(EmployeeProfileActivity.this)
                    .load(selectedImageUri) // Load the Uri directly
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(userProfileEmployeeee);
        }



        if (requestCode == 12 && resultCode == RESULT_OK) {
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
                        uploadCvEdit.setText(pdfName);

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

                       profile_fileImage1 = new File(filePath);

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

    private File saveBitmapToFile(Bitmap bitmap) {
        File imagePath = new File(getCacheDir(), "captured_images");
        if (!imagePath.exists()) {
            imagePath.mkdirs();
        }

        File imageFile = new File(imagePath, "captured_image.jpg");

        try {
            FileOutputStream fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            fos.close();
            return imageFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getPath(Uri uri) {
        Cursor cursor = null;
        try {
            String[] projection = {MediaStore.Images.Media.DATA};
            cursor = getContentResolver().query(uri, projection, null, null, null);
            if (cursor == null) return null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();


            return cursor.getString(column_index);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return "";
    }

    public class spinnerAdapter extends ArrayAdapter<String>{
        private spinnerAdapter(Context context, int textViewResourceId, List<String> smonking) {
            super(context, textViewResourceId);
        }
        @Override
        public int getCount() {
            int count = super.getCount();
            return count > 0 ? count - 1 : count;
        }
    }
}