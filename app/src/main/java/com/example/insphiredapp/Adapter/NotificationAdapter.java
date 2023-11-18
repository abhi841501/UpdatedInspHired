package com.example.insphiredapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.insphiredapp.Api_Model.NotificationModelData;
import com.example.insphiredapp.R;

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
        String id = String.valueOf(notificationModelDataList.get(position).getNotificationId());;
      /*  holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NotificationConfirmationActivity.class);
                context.startActivity(intent);
            }
        });*/

    }



    @Override
    public int getItemCount() {
        return notificationModelDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgCpnyy;
        TextView NotificationHeading,NotificationMessage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgCpnyy = itemView.findViewById(R.id.imgCpnyy);
            NotificationHeading = itemView.findViewById(R.id.NotificationHeading);
            NotificationMessage = itemView.findViewById(R.id.NotificationMessage);
        }
    }
}
