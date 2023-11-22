package com.example.insphiredapp.EmployerFragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.insphiredapp.Api_Model.GetProfileDetailsData;
import com.example.insphiredapp.Api_Model.GetProfileDetailsModel;
import com.example.insphiredapp.EmployerActivity.ChangePasswordActivity;
import com.example.insphiredapp.EmployerActivity.EditProfileActivity;
import com.example.insphiredapp.EmployerActivity.LoginActivity;
import com.example.insphiredapp.R;
import com.example.insphiredapp.retrofit.Api;
import com.example.insphiredapp.retrofit.Api_Client;

import org.json.JSONObject;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    LinearLayout linearEditProfile,linearChangePassword,linearTotalHC1,linearLogout;
    LinearLayout noDialog,yesDialog;
    GetProfileDetailsData getProfileDetailsData;
    CircleImageView imageProfileFragment;
    TextView companyNameProfFrag;
    private  String strCompanyName,strUserId,UserType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        linearEditProfile = view.findViewById(R.id.linearEditProfile);
        linearChangePassword = view.findViewById(R.id.linearChangePassword);
       //
        //
        // linearTotalHC1 = view.findViewById(R.id.linearTotalHC1);
        linearLogout = view.findViewById(R.id.linearLogout);
        noDialog = view.findViewById(R.id.noDialog);
        yesDialog = view.findViewById(R.id.yesDialog);
        companyNameProfFrag = view.findViewById(R.id.companyNameProfFrag);
        imageProfileFragment = view.findViewById(R.id.imageProfileFragment);


        SharedPreferences getUserIdData = getActivity().getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        strUserId = getUserIdData.getString("Id", "");
        UserType= getUserIdData.getString("userType", "");
        Log.e("changePassword", "change" + strUserId);

        getProfileDetailsApi();

        linearEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
            }
        });

        linearChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

      /*  linearTotalHC1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OnGoingEmployeeActivity.class);
                startActivity(intent);
            }
        });
*/
        linearLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog logoutDialog  = new Dialog(getActivity());
                logoutDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                logoutDialog.setContentView(R.layout.logoutdialog);
                LinearLayout noDialog = logoutDialog.findViewById(R.id.noDialog);
                LinearLayout yesDialog = logoutDialog.findViewById(R.id.yesDialog);
                logoutDialog.show();
                Window window = logoutDialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);//

                yesDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear(); // Clear all data in the SharedPreferences file
                        editor.apply();
                        editor.apply();

                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("finish", true);
                        startActivity(intent);

                        Toast.makeText(getActivity(), "User logout successfully", Toast.LENGTH_SHORT).show();
                        logoutDialog.dismiss();
                    }
                });


                noDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logoutDialog.dismiss();
                    }
                });
            }
        });
        return view;
    }

    private void getProfileDetailsApi() {

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<GetProfileDetailsModel> call = service.GET_PROFILE_DETAILS_MODEL_CALL("employer_details?user_id="+strUserId+"&user_type="+UserType);

        call.enqueue(new Callback<GetProfileDetailsModel>() {
            @Override
            public void onResponse(Call<GetProfileDetailsModel> call, Response<GetProfileDetailsModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        //String message = response.body().getMessage();
                        String success = (response.body().getSuccess());
                        String message = (response.body().getMessage());
                        Log.e("hello", "success: " +success );

                        if (success.equals("True") || success.equals("true")) {
                            GetProfileDetailsModel getProfileDetailsModel = response.body();
                            getProfileDetailsData = getProfileDetailsModel.getData();

                            Log.e("hello", "getData: " );
                            // Id  = profileGetData.getId();
                            strCompanyName = getProfileDetailsData.getCompanyName();
                            companyNameProfFrag.setText(strCompanyName);
                            if(UserType.equals("employer"))
                            Glide.with(getActivity()).load(Api_Client.BASE_URL_IMAGES1 + getProfileDetailsData.getImage()).placeholder(R.drawable.ic_launcher_foreground).into(imageProfileFragment);
                            else
                                {
                                Glide.with(getActivity()).load(Api_Client.BASE_URL_IMAGES2 + getProfileDetailsData.getImage()).placeholder(R.drawable.ic_launcher_foreground).into(imageProfileFragment);
                            }



                        } else {
                            // Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(getActivity(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(getActivity(), "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(getActivity(), "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(getActivity(), "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(getActivity(), "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(getActivity(), "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(getActivity(), "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(getActivity(), "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(getActivity(), "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GetProfileDetailsModel> call, Throwable t) {
                Log.e("conversion issue", t.getMessage());

                if (t instanceof IOException) {
                    Toast.makeText(getActivity(), "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(getActivity(), "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            onResume();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint()) {
            return;
        }
        getProfileDetailsApi();
    }

}
