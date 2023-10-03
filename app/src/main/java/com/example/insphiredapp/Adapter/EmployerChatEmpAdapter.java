package com.example.insphiredapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.insphiredapp.Api_Model.EmployerChatEmpData;
import com.example.insphiredapp.EmployeeActivity.EmployeeMessageActivity;
import com.example.insphiredapp.R;
import com.example.insphiredapp.retrofit.Api_Client;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EmployerChatEmpAdapter extends RecyclerView.Adapter<EmployerChatEmpAdapter.ViewHolder> {
    List<EmployerChatEmpData> employerChatEmpDataList;
    private Context context;
    private String UserType;
     private String EmployerImg;

    public


    EmployerChatEmpAdapter(List<EmployerChatEmpData> employerChatEmpDataList, Context context, String userType) {
        this.employerChatEmpDataList = employerChatEmpDataList;
        this.context = context;
        UserType = userType;
    }


    @Override
    public EmployerChatEmpAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.employer_chat_to_employee_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EmployerChatEmpAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        EmployerImg = employerChatEmpDataList.get(position).getEmpImage();
        Glide.with(context).load(Api_Client.BASE_URL_IMAGES + employerChatEmpDataList.get(position).getEmpImage()).into(holder.companyImgccEmployer);
        holder.cnameeeEmployeerr.setText(employerChatEmpDataList.get(position).getFirstName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Id = String.valueOf(employerChatEmpDataList.get(position).getUserId());
                String UserTypeEmp = employerChatEmpDataList.get(position).getUserType();
                Intent i = new Intent(context, EmployeeMessageActivity.class);
                i.putExtra("employeeIdddd", Id);
                i.putExtra("UserTypeEmpppp", UserTypeEmp);
                context.startActivity(i);
                Log.e("onClick","GetID"+Id);
                Log.e("onClick","UserTypeEmployee"+UserTypeEmp);


            }
        });
        SharedPreferences getUserIdData = context.getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = getUserIdData.edit();
        editor.putString("getEmpImage",String.valueOf(EmployerImg));
        editor.apply();
    }


    @Override
    public int getItemCount() {
        return employerChatEmpDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView companyImgccEmployer;
        TextView cnameeeEmployeerr;

        public ViewHolder(View itemView) {
            super(itemView);
            companyImgccEmployer = itemView.findViewById(R.id.companyImgccEmployer);
            cnameeeEmployeerr = itemView.findViewById(R.id.cnameeeEmployeerr);

        }
    }
}
