package com.example.insphiredapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.insphiredapp.Api_Model.EarningModelData;
import com.example.insphiredapp.R;
import com.example.insphiredapp.retrofit.Api_Client;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EarningModelAdapter extends RecyclerView.Adapter<EarningModelAdapter.ViewHolder> {
    Context context;
    List<EarningModelData> earningModelDataList;

    public EarningModelAdapter(Context context, List<EarningModelData> earningModelDataList) {
        this.context = context;
        this.earningModelDataList = earningModelDataList;
    }

    @NonNull
    @Override
    public EarningModelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerearninglist,parent,Boolean.parseBoolean("false"));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EarningModelAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(Api_Client.BASE_URL_IMAGES1 + earningModelDataList.get(position).getImage()).into(holder.earningCompanyImage);
        holder.earningCompanyName.setText(earningModelDataList.get(position).getCompanyName());
        holder.earningCompanyprice.setText(earningModelDataList.get(position).getAmount());


    }

    @Override
    public int getItemCount() {
        return earningModelDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView earningCompanyImage;
        TextView earningCompanyName,earningCompanyprice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            earningCompanyImage = itemView.findViewById(R.id.earningCompanyImage);
            earningCompanyName = itemView.findViewById(R.id.earningCompanyName);
            earningCompanyprice = itemView.findViewById(R.id.earningCompanyprice);
        }
    }

}
