package com.example.insphiredapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.insphiredapp.Api_Model.GetCreditModelData;
import com.example.insphiredapp.R;

import java.util.List;

public class CreditAmontAdapter extends RecyclerView.Adapter<CreditAmontAdapter.ViewHolder> {
    Context context;
    List<GetCreditModelData> getCreditModelDataList;
    private String strFirst;

    public CreditAmontAdapter(Context context, List<GetCreditModelData> getCreditModelDataList) {
        this.context = context;
        this.getCreditModelDataList = getCreditModelDataList;
    }

    @Override
    public CreditAmontAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_credit_trans,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CreditAmontAdapter.ViewHolder holder, int position) {

        strFirst= getCreditModelDataList.get(position).getCompanyName();
        holder.CnameTransCompany.setText(strFirst);
        holder.priceCredit.setText(getCreditModelDataList.get(position).getAmount());
        holder.dateTimeCredit.setText(getCreditModelDataList.get(position).getUpdatedAt());



    }

    @Override
    public int getItemCount() {
        return getCreditModelDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCredit;
        TextView CnameTransCompany,priceCredit,dateTimeCredit;
        public ViewHolder(View itemView) {
            super(itemView);

            imgCredit = itemView.findViewById(R.id.imgCredit);
            CnameTransCompany = itemView.findViewById(R.id.CnameTransCompany);
            priceCredit = itemView.findViewById(R.id.priceCredit);
            dateTimeCredit = itemView.findViewById(R.id.dateTimeCredit);


        }
    }
}
