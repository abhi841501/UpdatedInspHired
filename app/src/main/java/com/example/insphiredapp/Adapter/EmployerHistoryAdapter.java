package com.example.insphiredapp.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.insphiredapp.Api_Model.GetEmployeeHistoryModelData;
import com.example.insphiredapp.EmployeeActivity.ChatCompanyActivity;
import com.example.insphiredapp.EmployerActivity.FeedbackActivity;
import com.example.insphiredapp.R;
import com.example.insphiredapp.retrofit.Api_Client;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EmployerHistoryAdapter extends RecyclerView.Adapter<EmployerHistoryAdapter.ViewHolder> implements Filterable {


    Context context;
    List<GetEmployeeHistoryModelData> getEmployeeHistoryModelData;
    List<GetEmployeeHistoryModelData> employerHistory;
    private  String strFirstName;

    public EmployerHistoryAdapter(Context context, List<GetEmployeeHistoryModelData> getEmployeeHistoryModelData, List<GetEmployeeHistoryModelData> employerHistory) {
        this.context = context;
        this.getEmployeeHistoryModelData = getEmployeeHistoryModelData;
        this.employerHistory = new ArrayList<>(getEmployeeHistoryModelData);
    }


    @Override
    public EmployerHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycleremployerhistory,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EmployerHistoryAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {


        Glide.with(context).load(Api_Client.BASE_URL_IMAGES + getEmployeeHistoryModelData.get(position).getEmpImage()).into(holder.EmployerHistoryImg);
        Log.e("images", " " + getEmployeeHistoryModelData.get(position).getEmpImage());
        strFirstName = getEmployeeHistoryModelData.get(position).getFirstName();
        holder.EmployerNameHistory.setText(strFirstName);

        holder.EmployerDgNameHistory.setText(getEmployeeHistoryModelData.get(position).getCatName());
        holder.startDateHistory.setText(getEmployeeHistoryModelData.get(position).getStartDate());
        holder.endDateHistory.setText(getEmployeeHistoryModelData.get(position).getEndDate());
        holder.totalAmountHistory.setText(getEmployeeHistoryModelData.get(position).getDailyRate());
        holder.locationHistory.setText(getEmployeeHistoryModelData.get(position).getAddress());

        holder.feedbackBtnHistoryyy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Id = getEmployeeHistoryModelData.get(position).getEmployeeId();
                String name = getEmployeeHistoryModelData.get(position).getFirstName();
                String catName = getEmployeeHistoryModelData.get(position).getCatName();
                String img = getEmployeeHistoryModelData.get(position).getEmpImage();

                Intent intent = new Intent(context, FeedbackActivity.class);
                intent.putExtra("EmployeeIdFeedback",Id);
                intent.putExtra("FirstName",name);
                intent.putExtra("CTName",catName);
                intent.putExtra("img",img);

                context.startActivity(intent);
            }
        });


        holder.ImgCallingHisss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   callPhoneNumber();
                try
                {
                    if(Build.VERSION.SDK_INT > 22)
                    {
                        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling

                            ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.CALL_PHONE}, 101);
                            return;
                        }
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        String Phone = getEmployeeHistoryModelData.get(position).getMobile();
                        callIntent.setData(Uri.parse("tel:"+Phone));
                        context.startActivity(callIntent);
                    }
                    else {
                        // String number=calltext.getText().toString();
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        String Phone = getEmployeeHistoryModelData.get(position).getMobile();
                        callIntent.setData(Uri.parse("tel:"+Phone));
                        context.startActivity(callIntent);
                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        });


        holder.ImgMessageEmpHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatCompanyActivity.class);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return getEmployeeHistoryModelData.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence keyword) {
            List<GetEmployeeHistoryModelData> filtereddata = new ArrayList<>();
            if (keyword.toString().isEmpty())
            {
                filtereddata.addAll(employerHistory);
            }
            else
            {
                for (GetEmployeeHistoryModelData obj1 : employerHistory)
                {
                    if (obj1.getFirstName().toString().toLowerCase().contains(keyword.toString().toLowerCase()))
                        filtereddata.add(obj1);
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values=filtereddata;
            return  filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            getEmployeeHistoryModelData.clear();
            getEmployeeHistoryModelData.addAll((List<GetEmployeeHistoryModelData>)filterResults.values);

            notifyDataSetChanged();

        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatButton feedbackBtnHistoryyy;
        ImageView ImgMessageEmpHistory,ImgCallingHisss;
        CircleImageView EmployerHistoryImg;
        TextView EmployerNameHistory,EmployerDgNameHistory,startDateHistory,endDateHistory,totalAmountHistory,locationHistory;
        LinearLayout linearHistoryy;
        public ViewHolder(View itemView) {
            super(itemView);

            feedbackBtnHistoryyy = itemView.findViewById(R.id.feedbackBtnHistoryyy);
            ImgMessageEmpHistory = itemView.findViewById(R.id.ImgMessageEmpHistory);
            linearHistoryy = itemView.findViewById(R.id.linearHistoryy);
            EmployerHistoryImg = itemView.findViewById(R.id.EmployerHistoryImg);
            EmployerNameHistory = itemView.findViewById(R.id.EmployerNameHistory);
            EmployerDgNameHistory = itemView.findViewById(R.id.EmployerDgNameHistory);
            startDateHistory = itemView.findViewById(R.id.startDateHistory);
            endDateHistory = itemView.findViewById(R.id.endDateHistory);
            totalAmountHistory = itemView.findViewById(R.id.totalAmountHistory);
            locationHistory = itemView.findViewById(R.id.locationHistory);
            ImgCallingHisss = itemView.findViewById(R.id.ImgCallingHisss);


        }
    }
}
