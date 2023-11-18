package com.example.insphiredapp.EmployeeFragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.insphiredapp.Adapter.NotificationAdapter;
import com.example.insphiredapp.Api_Model.NotificationModel;
import com.example.insphiredapp.Api_Model.NotificationModelData;
import com.example.insphiredapp.EmployeeActivity.AvailableSlotsActivity;
import com.example.insphiredapp.EmployeeActivity.CVWebViewActivity;
import com.example.insphiredapp.EmployeeActivity.ChatCompanyActivity;
import com.example.insphiredapp.EmployeeActivity.EmployeeHistoryActivity;
import com.example.insphiredapp.EmployeeActivity.MyJobActivity;
import com.example.insphiredapp.EmployeeActivity.NotificationActivity;
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

public class HomeFragmentEmployee extends Fragment {
    LinearLayout linearSlots,linearMyJobs,linearHistoryEmployee,linearUpdateCV,linearFavourite;
    ImageView notificationIcon,chatImg;
    private String UserId,UserType;
    TextView notificationCount;
    List<NotificationModelData> notificationModelDataList = new ArrayList<>();




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  =  inflater.inflate(R.layout.fragment_home_employee, container, false);

        linearSlots = view.findViewById(R.id.linearSlots);
        linearMyJobs = view.findViewById(R.id.linearMyJobs);
        linearHistoryEmployee = view.findViewById(R.id.linearHistoryEmployee);
        linearUpdateCV = view.findViewById(R.id.linearUpdateCV);
        notificationIcon = view.findViewById(R.id.notificationIcon);
        chatImg = view.findViewById(R.id.chatImg);
        notificationCount = view.findViewById(R.id.notificationCount);


        SharedPreferences getUserIdData = getActivity().getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
        UserId= getUserIdData.getString("Id", "");
        UserType= getUserIdData.getString("userType", "");
        Log.e("feedback", "change" + UserId);
        getNotificationApi();

        linearSlots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AvailableSlotsActivity.class);
                startActivity(intent);
            }
        });

        notificationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NotificationActivity.class);
                startActivity(intent);
            }
        });

        chatImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChatCompanyActivity.class);
                startActivity(intent);
            }
        });

        linearMyJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyJobActivity.class);
                startActivity(intent);
            }
        });


        linearUpdateCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CVWebViewActivity.class);
                startActivity(intent);
            }
        });


        linearHistoryEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EmployeeHistoryActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void  getNotificationApi() {
        Api service = Api_Client.getClient().create(Api.class);
        retrofit2.Call<NotificationModel> call = service.NOTIFICATION_MODEL_CALL("notification?user_id="+UserId+"&user_type="+UserType);

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
                            String count = String.valueOf(notificationModel.getCount());
                            notificationCount.setText(count);

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