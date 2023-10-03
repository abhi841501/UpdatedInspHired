package com.example.insphiredapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.insphiredapp.Api_Model.GetWithDrawDebitData;
import com.example.insphiredapp.R;

import java.util.List;

public class DevitTransactionAdapter extends RecyclerView.Adapter<DevitTransactionAdapter.DevitTransaction> {

    Context context;
    List<GetWithDrawDebitData>getWithDrawDebitDataList;
    private  String strFirst,strLast,strFullName;

    public DevitTransactionAdapter(Context context, List<GetWithDrawDebitData> getWithDrawDebitDataList) {
        this.context = context;
        this.getWithDrawDebitDataList = getWithDrawDebitDataList;
    }

    @Override
    public DevitTransaction onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_debit_trans,parent,Boolean.parseBoolean("false"));
        return new DevitTransaction(view);
    }

    @Override
    public void onBindViewHolder(DevitTransaction holder, int position) {
        strFirst= getWithDrawDebitDataList.get(position).getFirstName();
        holder.CnameTrans.setText(strFirst);
        holder.priceDebit.setText(getWithDrawDebitDataList.get(position).getWithdrawAmount());
        holder.dateTime.setText(getWithDrawDebitDataList.get(position).getCreatedAt());

    }

    @Override
    public int getItemCount() {
        return getWithDrawDebitDataList.size();
    }

    public class  DevitTransaction extends RecyclerView.ViewHolder {
        TextView CnameTrans,priceDebit,dateTime;
        ImageView imgDebit;
        public DevitTransaction(View itemView) {
            super(itemView);
            CnameTrans = itemView.findViewById(R.id.CnameTrans);
            priceDebit = itemView.findViewById(R.id.priceDebit);
            dateTime = itemView.findViewById(R.id.dateTime);
        }
    }
}
