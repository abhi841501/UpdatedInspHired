package com.example.insphiredapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.insphiredapp.Api_Model.Notification;
import com.example.insphiredapp.Api_Model.NotificationModel;
import com.example.insphiredapp.Api_Model.NotificationModelData;
import com.example.insphiredapp.R;
import com.example.insphiredapp.retrofit.Api_Client;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    Context context;
    List<NotificationModelData> notificationModelDataList;

    public NotificationAdapter(Context context, List<NotificationModelData> notificationModelDataList) {
        this.context = context;
        this.notificationModelDataList = notificationModelDataList;
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_notification,parent,Boolean.parseBoolean("false"));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.NotificationHeading.setText(notificationModelDataList.get(position).getSubject());
        holder.NotificationMessage.setText(notificationModelDataList.get(position).getMessage());


    }



    @Override
    public int getItemCount() {
        return notificationModelDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgCpny;
        TextView NotificationHeading,NotificationMessage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgCpny = itemView.findViewById(R.id.imgCpny);
            NotificationHeading = itemView.findViewById(R.id.NotificationHeading);
            NotificationMessage = itemView.findViewById(R.id.NotificationMessage);
        }
    }
}
