package com.example.insphiredapp.EmployerFragment;

import static android.content.Context.MODE_PRIVATE;

import static com.example.insphiredapp.R.color.skyBlue;
import static com.example.insphiredapp.R.color.white;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.insphiredapp.Api_Model.NotificationModel;
import com.example.insphiredapp.Api_Model.NotificationModelData;
import com.example.insphiredapp.EmployeeActivity.ChatCompanyActivity;
import com.example.insphiredapp.EmployeeActivity.NotificationActivity;
import com.example.insphiredapp.EmployerActivity.AllEmployeeListActivity;
import com.example.insphiredapp.EmployerActivity.DashboardActivity;
import com.example.insphiredapp.EmployerActivity.EmployerHistoryActivity;
import com.example.insphiredapp.EmployerActivity.ActiveEmployeeActivity;
import com.example.insphiredapp.EmployerActivity.SettingsActivity;
import com.example.insphiredapp.R;
import com.example.insphiredapp.retrofit.Api;
import com.example.insphiredapp.retrofit.Api_Client;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    ImageView notificationEmployer,chatImgEmployer;
    LinearLayout linearOnGoingEmployee,linearPaymentsHome,linearSettings,linearEmployeeHistory;
    RelativeLayout relativeSearchEmployer;
    private  String user_id,userType,employer;
    ImageView homeIcon,favouriteIcon,walletIcon,profileIcon;
    TextView notificationCountEmployer;
    List<NotificationModelData> notificationModelDataList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);

        linearOnGoingEmployee = view.findViewById(R.id.linearOnGoingEmployee);
        linearPaymentsHome = view.findViewById(R.id.linearPaymentsHome);
        linearSettings = view.findViewById(R.id.linearSettings);
        notificationEmployer = view.findViewById(R.id.notificationEmployer);
        linearEmployeeHistory = view.findViewById(R.id.linearEmployeeHistory);
        notificationCountEmployer = view.findViewById(R.id.notificationCountEmployer);
        chatImgEmployer = view.findViewById(R.id.chatImgEmployer);
        relativeSearchEmployer = view.findViewById(R.id.relativeSearchEmployer);
        walletIcon = ((DashboardActivity)getContext()).findViewById(R.id.walletIcon);
        homeIcon = ((DashboardActivity)getContext()).findViewById(R.id.homeIcon);
        favouriteIcon =  ((DashboardActivity)getContext()).findViewById(R.id.favouriteIcon);
        profileIcon = ((DashboardActivity)getContext()).findViewById(R.id.profileIcon);



        SharedPreferences getUserIdData = getActivity().getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
        user_id= getUserIdData.getString("Id", "");
        userType= getUserIdData.getString("userType", "");
        Log.e("feedback", "change" + user_id);
        getNotificationApi();


        relativeSearchEmployer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AllEmployeeListActivity.class);
                startActivity(intent);
            }
        });

        notificationEmployer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NotificationActivity.class);
                startActivity(intent);
            }
        });

        chatImgEmployer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChatCompanyActivity.class);
                startActivity(intent);
            }
        });


        linearOnGoingEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActiveEmployeeActivity.class);
                startActivity(intent);
            }
        });
        linearEmployeeHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EmployerHistoryActivity.class);
                startActivity(intent);
            }
        });
        linearPaymentsHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentsFragment paymentsFragment =new PaymentsFragment();
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.container, paymentsFragment);
                walletIcon.setBackground(getActivity().getResources().getDrawable(R.drawable.wallet));
                walletIcon.setColorFilter(getActivity().getResources().getColor(skyBlue));;
                homeIcon.setBackground(getActivity().getResources().getDrawable(R.drawable.house1));
                homeIcon.setColorFilter(getActivity().getResources().getColor(white));
                favouriteIcon.setBackground(getActivity().getResources().getDrawable(R.drawable.appoinment1));
                favouriteIcon.setColorFilter(getActivity().getResources().getColor(white));
                profileIcon.setBackground(getActivity().getResources().getDrawable(R.drawable.profile1));
                profileIcon.setColorFilter(getActivity().getResources().getColor(white));
                fr.commit();
            }
        });
        linearSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);

            }
        });
        return view;
    }

    private void  getNotificationApi() {
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<NotificationModel> call = service.NOTIFICATION_MODEL_CALL("notification?user_id="+user_id+"&user_type="+userType);

        call.enqueue(new Callback<NotificationModel>() {
            @Override
            public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        NotificationModel notificationModel = response.body();
                        String success = notificationModel.getSuccess();
                        String msg =notificationModel.getMessage();
                        Log.e("hello", "success: " +success );


                        if (success.equals("true")|| (success.equals("True"))) {
                            notificationModelDataList = notificationModel.getData();
                            String countt = String.valueOf(notificationModel.getCount());
                            notificationCountEmployer.setText(countt);




                            // Toast.makeText(NotificationActivity.this, msg, Toast.LENGTH_SHORT).show();


                            //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            // Calling another activity

                        } else {
                            Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
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
                                    Toast.makeText(getActivity() ,"Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(getActivity(),"The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(getActivity(),"Internal Server Error..", Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<NotificationModel> call, Throwable t) {
                Log.e("conversion issue", t.getMessage());

                if (t instanceof IOException) {
                    Toast.makeText(getActivity(), "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(getActivity(), "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


}