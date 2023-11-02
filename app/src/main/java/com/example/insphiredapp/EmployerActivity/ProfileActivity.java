package com.example.insphiredapp.EmployerActivity;

import static com.example.insphiredapp.R.color.skyBlue;
import static com.example.insphiredapp.R.color.white;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.insphiredapp.Adapter.NonNull;
import com.example.insphiredapp.Api_Model.EmployeeEditProfileModel;
import com.example.insphiredapp.Api_Model.GetEditEmployeeProfileData;
import com.example.insphiredapp.Api_Model.GetEditEmployeeProfileModel;
import com.example.insphiredapp.EmployerFragment.HomeFragment;
import com.example.insphiredapp.R;
import com.example.insphiredapp.retrofit.Api;
import com.example.insphiredapp.retrofit.Api_Client;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int GALLERY_REQUEST_CODE = 200;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 101;
    private ImageView imageView;
    private ImageView backArrowProfileee, addImageEmployerrr;
    private CircleImageView userProfileEmployerrrr;
    private AppCompatButton UpdateProfileeedit;
    private File profile_fileImage;
    private Button cameraButton, galleryButton;
    private EditText PNameEEdit, PEmailEEdit, PPhoneEEdit, PCompanyAddEdit, PDailyRateAddress;
    private ContentValues values6;
    private Uri imageUri6;
    private String user_id, userType;
    private GetEditEmployeeProfileData getEditEmployeeProfileData;
    private String strFirstname, strEmail, strPhone, strCompanyAddress, strDailyRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        UpdateProfileeedit = findViewById(R.id.UpdateProfileeedit);
        addImageEmployerrr = findViewById(R.id.addImageEmployerrr);
        backArrowProfileee = findViewById(R.id.backArrowProfileee);
        userProfileEmployerrrr = findViewById(R.id.userProfileEmployerrrr);
        PNameEEdit = findViewById(R.id.PNameEEdit);
        PEmailEEdit = findViewById(R.id.PEmailEEdit);
        PPhoneEEdit = findViewById(R.id.PPhoneEEdit);
        PCompanyAddEdit = findViewById(R.id.PCompanyAddEdit);
        PDailyRateAddress = findViewById(R.id.PDailyRateAddress);

        backArrowProfileee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        SharedPreferences getUserIdData = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        user_id = getUserIdData.getString("Id", "");
        userType = getUserIdData.getString("userType", "");

        UpdateProfileeedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validation()) {
                    EditEmployeeProfileApi();
                }

            }
        });

        addImageEmployerrr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog addProfileUpdate = new Dialog(ProfileActivity.this);
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

        getProfileDetailsApi();


    }

    private boolean validation() {
        String MobilePattern = "[0-9]{10}";
        if (PNameEEdit.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "please enter name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (PEmailEEdit.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter email address", Toast.LENGTH_SHORT).show();
            return false;
        }  else if (PPhoneEEdit.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter  phone number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!PPhoneEEdit.getText().toString().matches(MobilePattern)) {
            Toast.makeText(getApplicationContext(), "Please enter valid 10 digit phone number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (PCompanyAddEdit.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "please enter address", Toast.LENGTH_SHORT).show();
            return false;
        } else if (PDailyRateAddress.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "please enter daily rate", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

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

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
    }

    private void getProfileDetailsApi() {

        final ProgressDialog pd = new ProgressDialog(ProfileActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<GetEditEmployeeProfileModel> call = service.GET_EDIT_EMPLOYEE_PROFILE_MODEL_CALL("user_show_data?user_id=" + user_id);

        call.enqueue(new Callback<GetEditEmployeeProfileModel>() {
            @Override
            public void onResponse(Call<GetEditEmployeeProfileModel> call, Response<GetEditEmployeeProfileModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        //String message = response.body().getMessage();
                        String success = response.body().getSuccess();
                        String message = response.body().getMessage();
                        Log.e("hello", "success: " + success);

                        if (success.equals("True") || success.equals("true")) {
                            GetEditEmployeeProfileModel getProfileDetailsModel = response.body();
                            getEditEmployeeProfileData = getProfileDetailsModel.getData();

                            Log.e("hello", "getData: ");
                            // Id  = profileGetData.getId();
                            strFirstname = getEditEmployeeProfileData.getFirstName();
                            strEmail = getEditEmployeeProfileData.getEmail();
                            strPhone = getEditEmployeeProfileData.getMobile();
                            strCompanyAddress = getEditEmployeeProfileData.getAddress();
                            strDailyRate = getEditEmployeeProfileData.getDailyRate();


                            PNameEEdit.setText(strFirstname);
                            PEmailEEdit.setText(strEmail);
                            PPhoneEEdit.setText(strPhone);
                            PCompanyAddEdit.setText(strCompanyAddress);
                            PDailyRateAddress.setText(strDailyRate);
                            Glide.with(getApplicationContext()).load(Api_Client.BASE_URL_IMAGES + getEditEmployeeProfileData.getEmpImage()).placeholder(R.drawable.ic_launcher_foreground).into(userProfileEmployerrrr);


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
            public void onFailure(Call<GetEditEmployeeProfileModel> call, Throwable t) {
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

    private void EditEmployeeProfileApi() {

        final ProgressDialog pd = new ProgressDialog(ProfileActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        MultipartBody.Part profileImage = null;

        try {
            if (profile_fileImage == null) {
                profileImage = MultipartBody.Part.createFormData("image", "", RequestBody.create(MediaType.parse("image/*"), ""));
                Log.e("conversionException", "error1");

            } else {
                profileImage = MultipartBody.Part.createFormData("image", profile_fileImage.getName(), RequestBody.create(MediaType.parse("image/*"), profile_fileImage));
                Log.e("conversionException", "error2");
            }

        } catch (Exception e) {
            Log.e("conversionException", "error" + e.getMessage());
        }
        RequestBody UserId = (RequestBody.create(MediaType.parse("string/plain"), user_id));
        RequestBody ProfileName = RequestBody.create(MediaType.parse("text/plain"), PNameEEdit.getText().toString());
        RequestBody ProfileEmail = RequestBody.create(MediaType.parse("text/plain"), PEmailEEdit.getText().toString());
        RequestBody ProfilePhone = RequestBody.create(MediaType.parse("text/plain"), PPhoneEEdit.getText().toString());
        RequestBody CompanyAdd = RequestBody.create(MediaType.parse("text/plain"), PCompanyAddEdit.getText().toString());
        RequestBody DailyRate = RequestBody.create(MediaType.parse("string/plain"), PDailyRateAddress.getText().toString());
        RequestBody UserType = RequestBody.create(MediaType.parse("string/plain"), userType);


        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<EmployeeEditProfileModel> call = service.EMPLOYEE_EDIT_PROFILE_MODEL_CALL(UserId, profileImage,
                ProfileName,
                ProfileEmail,
                ProfilePhone,
                DailyRate,
                CompanyAdd,
                UserType);
        call.enqueue(new Callback<EmployeeEditProfileModel>() {
            @Override
            public void onResponse(Call<EmployeeEditProfileModel> call, Response<EmployeeEditProfileModel> response) {
                pd.dismiss();

                try {
                    if (response.isSuccessful()) {
                        String success = response.body().getSuccess();
                        String msg = response.body().getMessage();

                        if (success.equals("True") || success.equals("true")) {
                            Log.e("myprofile", "success " + success);
                            Toast.makeText(ProfileActivity.this, msg, Toast.LENGTH_SHORT).show();
                            getProfileDetailsApi();

                        } else {
                            pd.dismiss();
                            Toast.makeText(ProfileActivity.this, msg, Toast.LENGTH_LONG).show();

                        }

                    } else {

                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(ProfileActivity.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(ProfileActivity.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(ProfileActivity.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(ProfileActivity.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(ProfileActivity.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(ProfileActivity.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(ProfileActivity.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(ProfileActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
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
            public void onFailure(@NonNull Call<EmployeeEditProfileModel> call, Throwable t) {

                if (t instanceof IOException) {

                    Toast.makeText(ProfileActivity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                } else {

                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(ProfileActivity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
                userProfileEmployerrrr.setImageBitmap(capturedImage);

                // Save the captured image as a file
                profile_fileImage = saveBitmapToFile(capturedImage);

            }
        } else if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            // Gallery selection successful, get the selected image URI
            Uri selectedImageUri = data.getData();
            Log.e("cameara", "  gall   " + selectedImageUri);
            userProfileEmployerrrr.setImageURI(selectedImageUri);

            // Get the file path from the URI
            String selectedImagePath = getPath(selectedImageUri);
            profile_fileImage = new File(selectedImagePath);

            // Load the image using Glide
            Glide.with(ProfileActivity.this)
                    .load(selectedImageUri) // Load the Uri directly
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(userProfileEmployerrrr);
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



}