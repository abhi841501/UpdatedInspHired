package com.example.insphiredapp.EmployeeFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.insphiredapp.EmployeeActivity.AvailableSlotsActivity;
import com.example.insphiredapp.EmployeeActivity.CVWebViewActivity;
import com.example.insphiredapp.EmployeeActivity.ChatCompanyActivity;
import com.example.insphiredapp.EmployeeActivity.EmployeeHistoryActivity;
import com.example.insphiredapp.EmployeeActivity.MyJobActivity;
import com.example.insphiredapp.EmployeeActivity.NotificationActivity;
import com.example.insphiredapp.R;

public class HomeFragmentEmployee extends Fragment {
    LinearLayout linearSlots,linearMyJobs,linearHistoryEmployee,linearUpdateCV,linearFavourite;
    ImageView notificationIcon,chatImg;



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
}