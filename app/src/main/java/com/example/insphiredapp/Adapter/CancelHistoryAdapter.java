package com.example.insphiredapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.insphiredapp.Api_Model.CancelEmployeeHistoryList;
import com.example.insphiredapp.EmployeeActivity.EmployeeGiveRatingActivity;
import com.example.insphiredapp.R;
import com.example.insphiredapp.retrofit.Api_Client;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.annotations.NonNull;

public class CancelHistoryAdapter extends RecyclerView.Adapter<CancelHistoryAdapter.ViewHolder> {
    Context context;
    List<CancelEmployeeHistoryList> cancelEmployeeHistoryList;
    private String UserType;
    private String status;


    public CancelHistoryAdapter(Context context, List<CancelEmployeeHistoryList> cancelEmployeeHistoryList, String userType) {
        this.context = context;
        this.cancelEmployeeHistoryList = cancelEmployeeHistoryList;
        UserType = userType;
    }
    @Override
    public CancelHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_cancel_adapter,parent,Boolean.parseBoolean("false"));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CancelHistoryAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            Glide.with(context).load(Api_Client.BASE_URL_IMAGES1 + cancelEmployeeHistoryList.get(position).getImage()).into(holder.imgCancel);
        holder.catNameCancel.setText(cancelEmployeeHistoryList.get(position).getCatName());
        holder.companyNameCancel.setText(cancelEmployeeHistoryList.get(position).getCompanyName());
        holder.startDateCancel.setText(cancelEmployeeHistoryList.get(position).getStartDate());
        holder.endDateCancel.setText(cancelEmployeeHistoryList.get(position).getEndDate());
        holder.DailyAmountCancel.setText(cancelEmployeeHistoryList.get(position).getAmount());
        holder.AddressCancel.setText(cancelEmployeeHistoryList.get(position).getAddress());
         status = String.valueOf(cancelEmployeeHistoryList.get(position).getStatus());
        if (status.equals("3"))
        {
            holder.StatusCancel.setText("Cancelled by user");

        }
        else if  (status.equals("2"))
        {
            holder.startDateCancel.setText("Cancelled by employer");
        }

        holder.feedbackBtnCancelH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Id = cancelEmployeeHistoryList.get(position).getEmployerId();
                String name = cancelEmployeeHistoryList.get(position).getCompanyName();
                String catName = cancelEmployeeHistoryList.get(position).getCatName();
                String img = cancelEmployeeHistoryList.get(position).getImage();

                Intent intent = new Intent(context, EmployeeGiveRatingActivity.class);
                intent.putExtra("EmployerIdFeedback",Id);
                intent.putExtra("CompanyName",name);
                intent.putExtra("catNamee",catName);
                intent.putExtra("imgg",img);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return cancelEmployeeHistoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgCancel;
        TextView catNameCancel, companyNameCancel,startDateCancel,endDateCancel,DailyAmountCancel,AddressCancel,StatusCancel,paymentCancel;
        AppCompatButton feedbackBtnCancelH;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgCancel = itemView.findViewById(R.id.imgCancel);
            catNameCancel = itemView.findViewById(R.id.catNameCancel);
            companyNameCancel = itemView.findViewById(R.id.companyNameCancel);
            startDateCancel = itemView.findViewById(R.id.startDateCancel);
            endDateCancel = itemView.findViewById(R.id.endDateCancel);
            DailyAmountCancel = itemView.findViewById(R.id.DailyAmountCancel);
            AddressCancel = itemView.findViewById(R.id.AddressCancel);
            StatusCancel = itemView.findViewById(R.id.StatusCancel);
            paymentCancel = itemView.findViewById(R.id.paymentCancel);
            feedbackBtnCancelH = itemView.findViewById(R.id.feedbackBtnCancelH);
        }
    }

}
