package com.example.insphiredapp.EmployeeFragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.app.ProgressDialog;
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
import com.example.insphiredapp.Api_Model.GetEditEmployeeProfileData;
import com.example.insphiredapp.Api_Model.GetEditEmployeeProfileModel;
import com.example.insphiredapp.EmployeeActivity.CVWebViewActivity;
import com.example.insphiredapp.EmployeeActivity.EarningListActivity;
import com.example.insphiredapp.EmployeeActivity.RefreshInterface;
import com.example.insphiredapp.EmployerActivity.ChangePasswordActivity;
import com.example.insphiredapp.EmployerActivity.LoginActivity;
import com.example.insphiredapp.EmployerActivity.ProfileActivity;
import com.example.insphiredapp.R;
import com.example.insphiredapp.retrofit.Api;
import com.example.insphiredapp.retrofit.Api_Client;

import org.json.JSONObject;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragmentEmployee extends Fragment implements RefreshInterface {

    LinearLayout linearEditProfileEmployee,linearTotalEarning,linearChangePasswordEmployee,linearLogoutEmployee;
    private String UserId,StrCName;
    CircleImageView imageProfileeeee;
    TextView nameProfile;
    LinearLayout linearUpdateCVVV;
    private String UserType;
    GetEditEmployeeProfileData getEditEmployeeProfileData;
    RefreshInterface refreshInterface;
    private String strUserId,strUserType;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile_employee, container, false);
        linearEditProfileEmployee = view.findViewById(R.id.linearEditProfileEmployee);
        linearTotalEarning = view.findViewById(R.id.linearTotalEarning);
        linearUpdateCVVV = view.findViewById(R.id.linearUpdateCVVV);
        linearTotalEarning = view.findViewById(R.id.linearTotalEarning);
        linearChangePasswordEmployee = view.findViewById(R.id.linearChangePasswordEmployee);
        linearLogoutEmployee = view.findViewById(R.id.linearLogoutEmployee);
        imageProfileeeee = view.findViewById(R.id.imageProfileeeee);
        nameProfile = view.findViewById(R.id.nameProfile);
        refreshInterface = this;

        linearEditProfileEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        linearTotalEarning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EarningListActivity.class);
                startActivity(intent);
            }
        });


        linearUpdateCVVV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CVWebViewActivity.class);
                startActivity(intent);
            }
        });

        linearChangePasswordEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        linearLogoutEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog logoutDialog  = new Dialog(getActivity());
                logoutDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                logoutDialog.setContentView(R.layout.logout_dialog_employee);
                LinearLayout noDialogEmployee = logoutDialog.findViewById(R.id.noDialogEmployee);
                LinearLayout yesDialogEmployee = logoutDialog.findViewById(R.id.yesDialogEmployee);
                logoutDialog.show();
             /*   WindowManager.LayoutParams params = window.getAttributes();
                params.width = 800;
                params.height = 350;
                window.setAttributes(params);*/
                Window window = logoutDialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);//
               /* logoutDialog.getWindow().setBackgroundDrawableResource(R.drawable.withdrawpopupcard);
*/
                yesDialogEmployee.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear(); // Clear all data in the SharedPreferences file
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


                noDialogEmployee.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logoutDialog.dismiss();
                    }
                });

            }
        });

        SharedPreferences getUserIdData = getActivity().getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
        UserId= getUserIdData.getString("Id", "");
        UserType= getUserIdData.getString("userType", "");
        Log.e("feedback", "change" + UserId);
        getProfileApi();
        return view;
    }

    private void getProfileApi() {

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<GetEditEmployeeProfileModel> call = service.GET_EDIT_EMPLOYEE_PROFILE_MODEL_CALL("user_show_data?user_id="+UserId);

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
                        Log.e("hello", "success: " +success );

                        if (success.equals("True") || success.equals("true")) {
                            GetEditEmployeeProfileModel getProfileDetailsModel = response.body();
                            getEditEmployeeProfileData = getProfileDetailsModel.getData();
                            StrCName = getEditEmployeeProfileData.getFirstName();
                            nameProfile.setText(StrCName);

                            Log.e("hello", "getData: " );
                            // Id  = profileGetData.getId()
                            Glide.with(getActivity()).load(Api_Client.BASE_URL_IMAGES + getEditEmployeeProfileData.getEmpImage()).placeholder(R.drawable.ic_launcher_foreground).into(imageProfileeeee);



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
            public void onFailure(Call<GetEditEmployeeProfileModel> call, Throwable t) {
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
    public void Refresh() {
        getProfileApi();

    }
}
