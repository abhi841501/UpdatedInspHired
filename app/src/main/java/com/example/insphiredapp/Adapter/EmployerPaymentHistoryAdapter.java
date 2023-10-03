package com.example.insphiredapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.insphiredapp.Api_Model.PaymentHistoryData;
import com.example.insphiredapp.R;
import com.example.insphiredapp.retrofit.Api_Client;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EmployerPaymentHistoryAdapter extends RecyclerView.Adapter<EmployerPaymentHistoryAdapter.ViewHolder> {

    Context context;
    List<PaymentHistoryData> paymentHistoryDataList;
    private String strFirstName,strLastName,strData;
    boolean isCheck = true ;

    public EmployerPaymentHistoryAdapter(Context context, List<PaymentHistoryData> paymentHistoryDataList) {
        this.context = context;
        this.paymentHistoryDataList = paymentHistoryDataList;
    }

    @NonNull
    @Override
    public EmployerPaymentHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.recyclerwalletfragment,viewGroup,Boolean.parseBoolean("false"));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployerPaymentHistoryAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(Api_Client.BASE_URL_IMAGES + paymentHistoryDataList.get(position).getEmpImage()).into(holder.imagePaymentsHistory);
        Log.e("images", " " + paymentHistoryDataList.get(position).getEmpImage());
        strFirstName = paymentHistoryDataList.get(position).getFirstName();
        holder.namePaymentsHistory.setText(strFirstName);

        holder.designationPayHistory.setText(paymentHistoryDataList.get(position).getCatName());
        holder.startDatePayHistory.setText( paymentHistoryDataList.get(position).getStartDate());
        holder.endDatePayHistory.setText(paymentHistoryDataList.get(position).getEndDate());
        holder.amountPayHistory.setText(paymentHistoryDataList.get(position).getAmount());
        

    }

    @Override
    public int getItemCount() {
        return paymentHistoryDataList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imagePaymentsHistory;
        TextView namePaymentsHistory,designationPayHistory,startDatePayHistory,endDatePayHistory,amountPayHistory;
        AppCompatButton confirmationPayHistory;
        public ViewHolder(View itemView) {
            super(itemView);

            imagePaymentsHistory = itemView.findViewById(R.id.imagePaymentsHistory);
            namePaymentsHistory = itemView.findViewById(R.id.namePaymentsHistory);
            designationPayHistory = itemView.findViewById(R.id.designationPayHistory);
            startDatePayHistory = itemView.findViewById(R.id.startDatePayHistory);
            endDatePayHistory = itemView.findViewById(R.id.endDatePayHistory);
            amountPayHistory = itemView.findViewById(R.id.amountPayHistory);
            confirmationPayHistory = itemView.findViewById(R.id.confirmationPayHistory);





        }
    }
}
